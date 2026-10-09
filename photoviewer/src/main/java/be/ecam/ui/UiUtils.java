package be.ecam.ui;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UiUtils {
    public static Image scale(BufferedImage source, int targetWidth, int targetHeight) {
        double ratio = Math.min(
                (double) targetWidth / source.getWidth(),
                (double) targetHeight / source.getHeight());

        int newWidth = (int) (source.getWidth() * ratio);
        int newHeight = (int) (source.getHeight() * ratio);

        return source.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    }
}
