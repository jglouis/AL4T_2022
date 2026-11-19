package be.ecam.ui;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

public class ImageLoader {

    private final ExecutorService executor;

    private final int thumbnailSize;

    public ImageLoader(int numberOfThreads, int thumbnailSize) {

        if (numberOfThreads <= 0 || thumbnailSize <= 0) {
            throw new IllegalArgumentException();
        }

        this.executor = Executors.newFixedThreadPool(numberOfThreads);
        this.thumbnailSize = thumbnailSize;
    }

    /**
     * Reads and scales the image on a worker thread (consumer), then hands the
     * thumbnail to the callback on the Event Dispatch Thread.
     * The callback receives null if the file is not a readable image.
     */
    public void loadAsync(
            Path path,
            Consumer<BufferedImage> callback) {

        executor.submit(() -> {

            BufferedImage thumbnail = null;

            try {

                BufferedImage original =
                        ImageIO.read(path.toFile());

                if (original != null) {

                    thumbnail = UiUtils.scale(
                            original,
                            thumbnailSize,
                            thumbnailSize
                    );

                }

            } catch (IOException | RuntimeException e) {
                // RuntimeException too: ImageIO can throw one on a corrupted file. Without this,
                // submit() would swallow it silently and the callback would never be called.
                System.err.println(
                        "Cannot read " + path + ": " + e.getMessage()
                );

            }

            BufferedImage result = thumbnail;

            SwingUtilities.invokeLater(() -> {
                callback.accept(result);
            });

        });
    }

    public void shutdown() {
        executor.shutdown();
    }
}

