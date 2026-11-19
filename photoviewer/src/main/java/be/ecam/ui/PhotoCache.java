package be.ecam.ui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import static be.ecam.ui.UiUtils.estimatedSize;

/**
 * LRU cache of thumbnails, keyed by file path, bounded by the memory used by
 * the thumbnails.
 * <p>
 * Failed loads (unreadable image or I/O error) are cached too, as an empty
 * {@link Optional}, so a broken file is not read again on every repaint.
 * <p>
 * Not thread-safe: an access-ordered {@link LinkedHashMap} is modified even by
 * {@code get()}. It must only be used from the Event Dispatch Thread.
 */
public class PhotoCache {

    private static final int THREAD_POOL_SIZE = 4;

    public enum Status {
        READY, LOADING, FAILED
    }

    public record Result(Status status, BufferedImage image) {
        static Result ready(BufferedImage image) {
            return new Result(Status.READY, image);
        }

        static Result loading() {
            return new Result(Status.LOADING, null);
        }

        static Result failed() {
            return new Result(Status.FAILED, null);
        }
    }

    public interface Listener {
        void onUpdate(int usedBytes, int hits, int misses, int evictions);
    }

    /**
     * Produces the thumbnail of a file. Called by the cache on a miss only.
     */
    public interface Loader {
        /**
         * @return the thumbnail, or null if the file is not a readable image
         */
        BufferedImage load(Path path) throws IOException;
    }

    // Only one listener can be registered. No-op until then, so get() never has to
    // check for null.
    private Listener listener = (usedBytes, hits, misses, evictions) -> {
    };

    private final int maxBytes;
    private final Loader loader;
    private final Map<Path, Optional<BufferedImage>> cache;
    private final Set<Path> loading = new HashSet<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    private int usedBytes;
    private int hits;
    private int misses;
    private int evictions;

    /**
     * Creates a cache that reads the images from disk and scales them down.
     *
     * @param maxBytes      maximum memory used by the cached thumbnails, in bytes
     * @param thumbnailSize maximum width and height of a thumbnail, in pixels
     */
    public PhotoCache(int maxBytes, int thumbnailSize) {
        this(maxBytes, scalingLoader(thumbnailSize));
    }

    /**
     * @param maxBytes maximum memory used by the cached thumbnails, in bytes
     * @param loader   produces the thumbnails on a cache miss
     */
    public PhotoCache(int maxBytes, Loader loader) {
        if (maxBytes <= 0) {
            throw new IllegalArgumentException("Cache size must be positive");
        }

        this.maxBytes = maxBytes;
        this.loader = loader;
        this.cache = new LinkedHashMap<>(16, 0.75f, true);
    }

    private static Loader scalingLoader(int thumbnailSize) {
        if (thumbnailSize <= 0) {
            throw new IllegalArgumentException("Thumbnail size must be positive");
        }
        return path -> {
            BufferedImage original = ImageIO.read(path.toFile());
            if (original == null) {
                return null;
            }
            // Keep the aspect ratio of the original image.
            return UiUtils.scale(original, thumbnailSize, thumbnailSize);
        };
    }

    /**
     * Non-blocking lookup: returns immediately on the EDT.
     * On a miss, schedules loading on the background pool and runs {@code onLoaded}
     * on the EDT once the thumbnail is cached.
     */
    public Result get(Path path, Runnable onLoaded) {
        // Already in the cache (hit): cache.get() also updates the LRU order
        Optional<BufferedImage> cached = cache.get(path);
        if (cached != null) {
            hits++;
            listener.onUpdate(usedBytes, hits, misses, evictions);
            if (cached.isPresent()) {
                BufferedImage img = cached.get();
                return Result.ready(img);
            } else {
                return Result.failed();
            }
        }

        // Already being loaded by a thread: avoiding duplicates
        if (loading.contains(path) || executor.isShutdown()) {
            return Result.loading();
        }

        // Cache miss: the read operation is sent to the thread pool.
        misses++;
        listener.onUpdate(usedBytes, hits, misses, evictions);
        loading.add(path);

        executor.execute(() -> {
            // executed on a thread from the pool
            Optional<BufferedImage> thumbnail = load(path);

            // Must return to the EDT to access the cache and the interface
            SwingUtilities.invokeLater(() -> {
                loading.remove(path);
                cache.put(path, thumbnail);
                usedBytes += estimatedSize(thumbnail.orElse(null));

                var eldestFirst = cache.values().iterator();
                while (usedBytes > maxBytes && eldestFirst.hasNext()) {
                    usedBytes -= estimatedSize(eldestFirst.next().orElse(null));
                    eldestFirst.remove();
                    evictions++;
                }

                listener.onUpdate(usedBytes, hits, misses, evictions);
                if (onLoaded != null) {
                    onLoaded.run();
                }
            });
        });

        return Result.loading();
    }

    /**
     * @return the number of cached entries, including failed loads
     */
    public int size() {
        return cache.size();
    }

    private Optional<BufferedImage> load(Path path) {
        try {
            return Optional.ofNullable(loader.load(path));
        } catch (IOException e) {
            System.err.println("Cannot read " + path + ": " + e.getMessage());
            return Optional.empty();
        }
    }

    public void registerListener(Listener listener) {
        this.listener = listener;
    }

    public void shutdown() {
        executor.shutdownNow();
    }

}
