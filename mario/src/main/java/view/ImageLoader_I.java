package view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface ImageLoader_I {
    BufferedImage loadImage(String path);

    BufferedImage loadImage(File file);

    BufferedImage getSubImage(BufferedImage image, int col, int row, int w, int h);

    BufferedImage[] getLeftFrames(int marioForm);

    BufferedImage[] getRightFrames(int marioForm);

    BufferedImage[] getBrickFrames();
}
