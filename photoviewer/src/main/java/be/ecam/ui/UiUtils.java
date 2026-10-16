package be.ecam.ui;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UiUtils {
    public static BufferedImage scale(BufferedImage source, int targetWidth, int targetHeight) {
        double ratio = Math.min(
                (double) targetWidth / source.getWidth(),
                (double) targetHeight / source.getHeight());

        // A very narrow or very flat image would otherwise round down to 0 pixels.
        int newWidth = Math.max(1, (int) (source.getWidth() * ratio));
        int newHeight = Math.max(1, (int) (source.getHeight() * ratio));

        BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = scaledImage.createGraphics();
        try {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(source, 0, 0, newWidth, newHeight, null);
        } finally {
            g2.dispose();
        }
        return scaledImage;
    }

    /**
     * We assume a TYPE_INT_ARGB
     */
    public static int estimatedSize(BufferedImage image) {
        if (image == null) return 0;
        return image.getWidth() * image.getHeight() * 4;
    }
}
