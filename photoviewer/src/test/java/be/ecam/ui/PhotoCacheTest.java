package be.ecam.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests of {@link PhotoCache}, in isolation.
 * <p>
 * The cache depends on two collaborators: a {@link PhotoCache.Loader} that produces the thumbnails,
 * and a {@link PhotoCache.Listener} that receives the statistics. Both are replaced by Mockito mocks,
 * so these tests never touch the disk: the paths below do not even exist.
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

    private PhotoCache.Loader loader;
    private PhotoCache.Listener listener;
    private PhotoCache cache;

    @BeforeEach
    void setUp() throws IOException {
        loader = mock(PhotoCache.Loader.class);
        listener = mock(PhotoCache.Listener.class);

        // Every path gets its own image, so a test can tell them apart with assertSame().
        when(loader.load(any(Path.class))).thenAnswer(invocation -> image());

        cache = new PhotoCache(MAX_BYTES, loader);
        cache.registerListener(listener);
    }

    private static BufferedImage image() {
        return new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
    }

    @Test
    void constructorRejectsNonPositiveSize() {
        assertThrows(IllegalArgumentException.class, () -> new PhotoCache(0, loader));
        assertThrows(IllegalArgumentException.class, () -> new PhotoCache(-1, loader));
    }

    @Test
    void constructorRejectsNonPositiveThumbnailSize() {
        assertThrows(IllegalArgumentException.class, () -> new PhotoCache(MAX_BYTES, 0));
    }

    @Test
    void missLoadsTheThumbnail() throws IOException {
        // Arrange
        BufferedImage thumbnail = image();
        when(loader.load(A)).thenReturn(thumbnail);

        // Act
        BufferedImage result = cache.get(A);

        // Assert
        assertSame(thumbnail, result);
        verify(loader).load(A);
        assertEquals(1, cache.size());
    }

    @Test
    void hitDoesNotLoadAgain() throws IOException {
        BufferedImage first = cache.get(A);

        BufferedImage second = cache.get(A);

        assertSame(first, second);
        // The loader must have been called once only: the second get() is served from memory.
        verify(loader, times(1)).load(A);
    }

    @Test
    void unreadableImageIsCachedAsNull() throws IOException {
        when(loader.load(A)).thenReturn(null);

        assertNull(cache.get(A));
        assertNull(cache.get(A));

        // The broken file is not read again on every repaint.
        verify(loader, times(1)).load(A);
        assertEquals(1, cache.size());
    }

    @Test
    void ioErrorIsCachedAsNull() throws IOException {
        // A mock can simulate failures that are hard to reproduce with real files.
        when(loader.load(A)).thenThrow(new IOException("disk on fire"));

        assertNull(cache.get(A));
        assertNull(cache.get(A));

        verify(loader, times(1)).load(A);
    }

    @Test
    void evictsLeastRecentlyLoadedWhenFull() throws IOException {
        cache.get(A);
        cache.get(B);

        // Three images need 1200 bytes, more than the 1000 allowed: A is the eldest and goes.
        cache.get(C);

        assertEquals(2, cache.size());
        cache.get(A);
        verify(loader, times(2)).load(A);
    }

    @Test
    void hitMakesTheEntryMostRecentlyUsed() throws IOException {
        cache.get(A);
        cache.get(B);
        cache.get(A); // A is now more recent than B...

        cache.get(C); // ...so B is evicted, not A.

        cache.get(A);
        verify(loader, times(1)).load(A);
        cache.get(B);
        verify(loader, times(2)).load(B);
    }

    @Test
    void imageLargerThanTheCacheIsReturnedButNotKept() throws IOException {
        BufferedImage huge = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        when(loader.load(A)).thenReturn(huge);

        assertSame(huge, cache.get(A));

        assertEquals(0, cache.size());
    }

    @Test
    void listenerIsNotifiedOfMissesHitsAndEvictions() {
        cache.get(A); // miss
        cache.get(A); // hit
        cache.get(B); // miss
        cache.get(C); // miss, evicts A

        // Arguments: usedBytes, hits, misses, evictions.
        InOrder inOrder = inOrder(listener);
        inOrder.verify(listener).onUpdate(IMAGE_BYTES, 0, 1, 0);
        inOrder.verify(listener).onUpdate(IMAGE_BYTES, 1, 1, 0);
        inOrder.verify(listener).onUpdate(2 * IMAGE_BYTES, 1, 2, 0);
        inOrder.verify(listener).onUpdate(2 * IMAGE_BYTES, 1, 3, 1);
        verifyNoMoreInteractions(listener);
    }

    @Test
    void failedLoadsTakeNoMemory() throws IOException {
        when(loader.load(A)).thenReturn(null);

        cache.get(A);

        verify(listener).onUpdate(0, 0, 1, 0);
    }
}
