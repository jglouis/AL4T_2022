package be.ecam.ui;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

        /**
         * Called on the EDT when a thumbnail loaded in the background is available,
         * so the view can repaint. Default: nothing.
         */
        default void onThumbnailLoaded(Path path) {}
    }

    // Only one listener can be registered. No-op until then, so get() never has to check for null.
    private Listener listener = (usedBytes, hits, misses, evictions) -> {};

    private final int maxBytes;
    private final Map<Path, Optional<BufferedImage>> cache;
    private int usedBytes;
    private int hits;
    private int misses;
    private int evictions;
    private final ImageLoader imageloader;
    // Paths whose thumbnail is being loaded in the background, so a repaint does not submit them again.
    private final Set<Path> pending = new HashSet<>();
    /**
     * Creates a cache that reads the images from disk and scales them down.
     *
     * @param maxBytes      maximum memory used by the cached thumbnails, in bytes
     * @param thumbnailSize maximum width and height of a thumbnail, in pixels
     */
    public PhotoCache(int maxBytes, int thumbnailSize) {
        this(maxBytes, new ImageLoader(4, thumbnailSize));
    }

    /**
     * @param maxBytes    maximum memory used by the cached thumbnails, in bytes
     * @param imageloader produces the thumbnails in the background on a cache miss
     */
    public PhotoCache(int maxBytes, ImageLoader imageloader) {
        if (maxBytes <= 0) {
            throw new IllegalArgumentException("Cache size must be positive");
        }

        this.maxBytes = maxBytes;
        this.cache = new LinkedHashMap<>(16, 0.75f, true);
        this.imageloader = imageloader;
    }


    /**
     * Returns the thumbnail for the given path. On a cache miss, the thumbnail is loaded
     * in the background by the {@link ImageLoader} and null is returned until it is ready;
     * the listener is then told through {@link Listener#onThumbnailLoaded(Path)}.
     *
     * @return the thumbnail, or null if it is still loading or the file cannot be read as an image
     */
    public BufferedImage get(Path path) {

        // containsKey() does not update the access order, so use get() for the hit.
        if (cache.containsKey(path)) {
            hits++;
            listener.onUpdate(usedBytes, hits, misses, evictions);
            return cache.get(path).orElse(null);
        }

        // Already submitted: wait for the worker thread instead of submitting it again.
        if (pending.contains(path)) {
            return null;
        }

        misses++;
        pending.add(path);

        // Producer: the EDT submits the job, a worker thread of the ImageLoader consumes it.
        imageloader.loadAsync(path, thumbnail -> onLoaded(path, thumbnail));
        return null;
    }

    /**
     * Callback of the ImageLoader. Runs on the EDT (invokeLater), so the cache stays single-threaded.
     */
    private void onLoaded(Path path, BufferedImage loaded) {
        pending.remove(path);

        Optional<BufferedImage> thumbnail = Optional.ofNullable(loaded);
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
        listener.onThumbnailLoaded(path);
    }

    /**
     * @return the number of cached entries, including failed loads
     */
    public int size() {
        return cache.size();
    }

    public void registerListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * Stops the background threads of the ImageLoader. Call it when the application closes.
     */
    public void shutdown() {
        imageloader.shutdown();
    }

}
