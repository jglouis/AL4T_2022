package be.ecam.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests of {@link PhotoCache}, in isolation.
 * <p>
 * The cache depends on two collaborators: an {@link ImageLoader} that produces the thumbnails
 * in the background, and a {@link PhotoCache.Listener} that receives the statistics. Both are
 * replaced by Mockito mocks, so these tests never touch the disk and never start a thread:
 * the paths below do not even exist.
 * <p>
 * The mocked ImageLoader does nothing by itself. A test plays the role of the worker thread
 * by capturing the callback given to {@code loadAsync()} and calling it, see {@link #finishLoading}.
 * <p>
 * The thumbnails themselves are real {@link BufferedImage}s. They are simple values,
 * there is no reason to mock them.
 */
class PhotoCacheTest {

    // A 10x10 ARGB image takes 10 * 10 * 4 = 400 bytes, so the cache below holds two of them.
    private static final int IMAGE_BYTES = 400;
    private static final int MAX_BYTES = 1000;

    private static final Path A = Path.of("a.jpg");
    private static final Path B = Path.of("b.jpg");
    private static final Path C = Path.of("c.jpg");

    private ImageLoader imageLoader;
    private PhotoCache.Listener listener;
    private PhotoCache cache;

    @BeforeEach
    void setUp() {
        imageLoader = mock(ImageLoader.class);
        listener = mock(PhotoCache.Listener.class);

        cache = new PhotoCache(MAX_BYTES, imageLoader);
        cache.registerListener(listener);
    }

    private static BufferedImage image() {
        return new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * Simulates the worker thread: delivers the thumbnail to the callback of the last
     * {@code loadAsync()} call for this path.
     */
    @SuppressWarnings("unchecked")
    private void finishLoading(Path path, BufferedImage thumbnail) {
        ArgumentCaptor<Consumer<BufferedImage>> callback = ArgumentCaptor.forClass(Consumer.class);
        verify(imageLoader, atLeastOnce()).loadAsync(eq(path), callback.capture());
        callback.getValue().accept(thumbnail);
    }

    /**
     * A full miss: get() submits the load, then the worker thread delivers a new image.
     */
    private BufferedImage load(Path path) {
        assertNull(cache.get(path));
        BufferedImage thumbnail = image();
        finishLoading(path, thumbnail);
        return thumbnail;
    }

    @Test
    void constructorRejectsNonPositiveSize() {
        assertThrows(IllegalArgumentException.class, () -> new PhotoCache(0, imageLoader));
        assertThrows(IllegalArgumentException.class, () -> new PhotoCache(-1, imageLoader));
    }

    @Test
    void constructorRejectsNonPositiveThumbnailSize() {
        assertThrows(IllegalArgumentException.class, () -> new PhotoCache(MAX_BYTES, 0));
    }

    @Test
    void missStartsAnAsyncLoadAndReturnsNull() {
        BufferedImage result = cache.get(A);

        assertNull(result);
        verify(imageLoader).loadAsync(eq(A), any());
        // Nothing is cached until the worker thread delivers the thumbnail.
        assertEquals(0, cache.size());
    }

    @Test
    void loadedThumbnailIsReturnedByTheNextGet() {
        // Arrange
        cache.get(A);
        BufferedImage thumbnail = image();

        // Act
        finishLoading(A, thumbnail);

        // Assert
        assertSame(thumbnail, cache.get(A));
        assertEquals(1, cache.size());
    }

    @Test
    void pathBeingLoadedIsNotSubmittedAgain() {
        // Several repaints happen before the worker thread is done.
        cache.get(A);
        cache.get(A);
        cache.get(A);

        verify(imageLoader, times(1)).loadAsync(eq(A), any());
    }

    @Test
    void hitDoesNotLoadAgain() {
        BufferedImage first = load(A);

        BufferedImage second = cache.get(A);

        assertSame(first, second);
        // The loader must have been called once only: the second get() is served from memory.
        verify(imageLoader, times(1)).loadAsync(eq(A), any());
    }

    @Test
    void unreadableImageIsCachedAsNull() {
        // The ImageLoader delivers null for an unreadable image or an I/O error.
        cache.get(A);
        finishLoading(A, null);

        assertNull(cache.get(A));
        assertNull(cache.get(A));

        // The broken file is not read again on every repaint.
        verify(imageLoader, times(1)).loadAsync(eq(A), any());
        assertEquals(1, cache.size());
    }

    @Test
    void evictsLeastRecentlyLoadedWhenFull() {
        load(A);
        load(B);

        // Three images need 1200 bytes, more than the 1000 allowed: A is the eldest and goes.
        load(C);

        assertEquals(2, cache.size());
        cache.get(A);
        verify(imageLoader, times(2)).loadAsync(eq(A), any());
    }

    @Test
    void hitMakesTheEntryMostRecentlyUsed() {
        load(A);
        load(B);
        cache.get(A); // A is now more recent than B...

        load(C); // ...so B is evicted, not A.

        cache.get(A);
        verify(imageLoader, times(1)).loadAsync(eq(A), any());
        cache.get(B);
        verify(imageLoader, times(2)).loadAsync(eq(B), any());
    }

    @Test
    void imageLargerThanTheCacheIsNotKept() {
        BufferedImage huge = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        cache.get(A);

        finishLoading(A, huge);

        assertEquals(0, cache.size());
    }

    @Test
    void listenerIsNotifiedOfMissesHitsEvictionsAndLoads() {
        load(A);      // miss
        cache.get(A); // hit
        load(B);      // miss
        load(C);      // miss, evicts A

        // onUpdate arguments: usedBytes, hits, misses, evictions.
        InOrder inOrder = inOrder(listener);
        inOrder.verify(listener).onUpdate(IMAGE_BYTES, 0, 1, 0);
        inOrder.verify(listener).onThumbnailLoaded(A);
        inOrder.verify(listener).onUpdate(IMAGE_BYTES, 1, 1, 0);
        inOrder.verify(listener).onUpdate(2 * IMAGE_BYTES, 1, 2, 0);
        inOrder.verify(listener).onThumbnailLoaded(B);
        inOrder.verify(listener).onUpdate(2 * IMAGE_BYTES, 1, 3, 1);
        inOrder.verify(listener).onThumbnailLoaded(C);
        verifyNoMoreInteractions(listener);
    }

    @Test
    void failedLoadsTakeNoMemory() {
        cache.get(A);

        finishLoading(A, null);

        verify(listener).onUpdate(0, 0, 1, 0);
    }

    @Test
    void shutdownStopsTheImageLoader() {
        cache.shutdown();

        verify(imageLoader).shutdown();
    }
}
