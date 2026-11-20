package be.ecam.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests of {@link ImageLoader} with real files and real threads.
 * <p>
 * Unlike {@link PhotoCacheTest}, nothing is mocked here: the point is precisely to check
 * the work done on the worker threads (reading, scaling, error handling) and the hand-off
 * of the result to the Event Dispatch Thread.
 * <p>
 * loadAsync() returns immediately, so each test waits for the callback through a
 * {@link CompletableFuture}, with a timeout so a callback that is never called fails
 * the test instead of blocking it forever.
 */
class ImageLoaderTest {

    private static final int THUMBNAIL_SIZE = 50;
    private static final long TIMEOUT_SECONDS = 5;

    @TempDir // temporay directory
    Path folder;

    private ImageLoader loader;

    @BeforeEach
    void setUp() {
        loader = new ImageLoader(2, THUMBNAIL_SIZE);
    }

    @AfterEach
    void tearDown() {
        loader.shutdown();
    }

    /**
     * Writes a real PNG image of the given size into the temporary folder.
     */
    private Path png(String name, int width, int height) throws IOException {
        Path path = folder.resolve(name);
        ImageIO.write(new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB), "png", path.toFile());
        return path;
    }

    /**
     * Starts an asynchronous load; the returned future completes when the callback is called.
     */
    private CompletableFuture<BufferedImage> loadAsync(Path path) {
        CompletableFuture<BufferedImage> result = new CompletableFuture<>();
        loader.loadAsync(path, result::complete);
        return result;
    }

    private static BufferedImage await(CompletableFuture<BufferedImage> future) throws Exception {
        return future.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }

    @Test
    void constructorRejectsNonPositiveArguments() {
        assertThrows(IllegalArgumentException.class, () -> new ImageLoader(0, THUMBNAIL_SIZE));
        assertThrows(IllegalArgumentException.class, () -> new ImageLoader(-1, THUMBNAIL_SIZE));
        assertThrows(IllegalArgumentException.class, () -> new ImageLoader(2, 0));
        assertThrows(IllegalArgumentException.class, () -> new ImageLoader(2, -1));
    }

    @Test
    void readableImageIsScaledDownKeepingTheAspectRatio() throws Exception {
        Path image = png("wide.png", 200, 100);

        BufferedImage thumbnail = await(loadAsync(image));

        assertNotNull(thumbnail);
        assertEquals(50, thumbnail.getWidth());
        assertEquals(25, thumbnail.getHeight());
    }

    @Test
    void callbackRunsOnTheEventDispatchThread() throws Exception {
        Path image = png("photo.png", 100, 100);
        CompletableFuture<Boolean> onEdt = new CompletableFuture<>();

        loader.loadAsync(image, thumbnail -> onEdt.complete(SwingUtilities.isEventDispatchThread()));

        // The cache is not thread-safe: it relies on being called back on the EDT only.
        assertTrue(onEdt.get(TIMEOUT_SECONDS, TimeUnit.SECONDS));
    }

    @Test
    void fileThatIsNotAnImageGivesNull() throws Exception {
        Path text = folder.resolve("notes.txt");
        Files.writeString(text, "This is not an image");

        assertNull(await(loadAsync(text)));
    }

    @Test
    void missingFileGivesNull() throws Exception {
        // ImageIO throws an IOException here: the callback must still be called.
        assertNull(await(loadAsync(folder.resolve("missing.png"))));
    }

    @Test
    void corruptedImageGivesNull() throws Exception {
        // A valid PNG signature followed by garbage: ImageIO recognises the format, then fails.
        Path corrupted = folder.resolve("corrupted.png");
        byte[] content = {(byte) 0x89, 'P', 'N', 'G', '\r', '\n', 0x1A, '\n', 1, 2, 3, 4, 5};
        Files.write(corrupted, content);

        assertNull(await(loadAsync(corrupted)));
    }

    @Test
    void manyLoadsAreAllCompleted() throws Exception {
        // More jobs than threads: the extra ones wait in the executor queue.
        List<CompletableFuture<BufferedImage>> results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            results.add(loadAsync(png("photo" + i + ".png", 100, 100)));
        }

        for (CompletableFuture<BufferedImage> result : results) {
            assertNotNull(await(result));
        }
    }

    @Test
    void shutdownLetsSubmittedLoadsFinish() throws Exception {
        CompletableFuture<BufferedImage> result = loadAsync(png("photo.png", 100, 100));

        loader.shutdown();

        // shutdown() refuses new jobs but does not cancel the ones already submitted.
        assertNotNull(await(result));
    }
}
