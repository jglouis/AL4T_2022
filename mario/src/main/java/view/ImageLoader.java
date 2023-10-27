package view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import model.hero.MarioForm;

public class ImageLoader {

    private static final int TILE_SIZE = 48;
    private static final int LARGE_TILE_SIZE = 96;
    private static final int BRICK_SIZE = 105;
    private static final int NUM_FRAMES = 5;

    private final BufferedImage marioForms;
    private final BufferedImage brickAnimation;

    public ImageLoader() {
        marioForms = loadImageFromPath("/mario-forms.png");
        brickAnimation = loadImageFromPath("/brick-animation.png");
    }

    public BufferedImage loadImageFromPath(String path) {
        return loadImage(() -> ImageIO.read(getClass().getResource("/media" + path)));
    }

    public BufferedImage loadImageFromFile(File file) {
        return loadImage(() -> ImageIO.read(file));
    }

    private BufferedImage loadImage(ImageLoaderFunction loader) {
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FunctionalInterface
    private interface ImageLoaderFunction {
        BufferedImage load() throws IOException;
    }

    public BufferedImage getSubImage(BufferedImage image, int col, int row, int w, int h) {
        int x = (col - 1) * TILE_SIZE;
        int y = isKoopa(col, row) ? TILE_SIZE * 2 : (row - 1) * TILE_SIZE;
        return image.getSubimage(x, y, w, h);
    }

    private boolean isKoopa(int col, int row) {
        return (col == 1 || col == 4) && row == 3;
    }

    public BufferedImage[] getFrames(MarioForm marioForm, boolean isLeft) {
        BufferedImage[] frames = new BufferedImage[NUM_FRAMES];
        FrameInfo frameInfo = getFrameInfo(marioForm, isLeft);
        extractFrames(frames, frameInfo.col, frameInfo.width, frameInfo.height);
        return frames;
    }

    private void extractFrames(BufferedImage[] frames, int col, int width, int height) {
        for (int i = 0; i < NUM_FRAMES; i++) {
            frames[i] = marioForms.getSubimage((col - 1) * width, i * height, width, height);
        }
    }

    public BufferedImage[] getBrickFrames() {
        return extractFrames(brickAnimation, 4, BRICK_SIZE, BRICK_SIZE);
    }

    private BufferedImage[] extractFrames(BufferedImage source, int numFrames, int width, int height) {
        BufferedImage[] frames = new BufferedImage[numFrames];
        for (int i = 0; i < numFrames; i++) {
            frames[i] = source.getSubimage(i * width, 0, width, height);
        }
        return frames;
    }

    private static class FrameInfo {
        final int col, width, height;
        FrameInfo(int col, int width, int height) {
            this.col = col;
            this.width = width;
            this.height = height;
        }
    }

    private FrameInfo getFrameInfo(MarioForm marioForm, boolean isLeft) {
        int col = isLeft ? 1 : 2;
        int width = TILE_SIZE + 4, height = TILE_SIZE;

        if (marioForm.isSuper()) {
            col = isLeft ? 4 : 5;
            width = TILE_SIZE;
            height = LARGE_TILE_SIZE;
        } else if (marioForm.isFire()) {
            col = isLeft ? 7 : 8;
            width = TILE_SIZE;
            height = LARGE_TILE_SIZE;
        }

        return new FrameInfo(col, width, height);
    }
}
