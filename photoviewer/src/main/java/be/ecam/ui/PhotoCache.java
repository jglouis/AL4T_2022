package be.ecam.ui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;

import static be.ecam.ui.UiUtils.estimatedSize;

/**
 * LRU cache of thumbnails, keyed by file path, bounded by the memory used by the thumbnails.
 * <p>
 * Failed loads (unreadable image or I/O error) are cached too, as an empty
 * {@link Optional}, so a broken file is not read again on every repaint.
 * <p>
 * Not thread-safe: an access-ordered {@link LinkedHashMap} is modified even by
 * {@code get()}. It must only be used from the Event Dispatch Thread.
 */
public class PhotoCache {

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

    // Only one listener can be registered. No-op until then, so get() never has to check for null.
    private Listener listener = (usedBytes, hits, misses, evictions) -> {};

    private final int maxBytes;
    private final Loader loader;
    private final Map<Path, Optional<BufferedImage>> cache;
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
     * Returns the thumbnail for the given path, loading it from disk on a cache miss.
     *
     * @return the thumbnail, or null if the file cannot be read as an image
     */
    public BufferedImage get(Path path) {

        // containsKey() does not update the access order, so use get() for the hit.
        if (cache.containsKey(path)) {
            hits++;
            listener.onUpdate(usedBytes, hits, misses, evictions);
            return cache.get(path).orElse(null);
        }
        misses++;

        Optional<BufferedImage> thumbnail = load(path);
        cache.put(path, thumbnail);
        usedBytes += estimatedSize(thumbnail.orElse(null));

        // Evict the least recently used entries until the cache fits in its budget again.
        var eldestFirst = cache.values().iterator();
        while (usedBytes > maxBytes && eldestFirst.hasNext()) {
            usedBytes -= estimatedSize(eldestFirst.next().orElse(null));
            eldestFirst.remove();
            evictions++;
        }

        listener.onUpdate(usedBytes, hits, misses, evictions);

        return thumbnail.orElse(null);
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

}
