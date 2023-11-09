package view;

import manager.GameEngine;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageResourceManager {

    private BufferedImage startScreenImage, aboutScreenImage, helpScreenImage, gameOverScreen;
    private BufferedImage heartIcon, coinIcon, selectIcon;

    public ImageResourceManager(GameEngine engine) {
        IImageLoader loader = engine.getImageLoader();
        BufferedImage sprite = loader.loadImage("/sprite.png");

        this.heartIcon = loader.loadImage("/heart-icon.png");
        this.coinIcon = loader.getSubImage(sprite, 1, 5, 48, 48);
        this.selectIcon = loader.loadImage("/select-icon.png");
        this.startScreenImage = loader.loadImage("/start-screen.png");
        this.helpScreenImage = loader.loadImage("/help-screen.png");
        this.aboutScreenImage = loader.loadImage("/about-screen.png");
        this.gameOverScreen = loader.loadImage("/game-over.png");
    }

    public BufferedImage getStartScreenImage() { return startScreenImage; }
    public BufferedImage getAboutScreenImage() { return aboutScreenImage; }
    public BufferedImage getHelpScreenImage() { return helpScreenImage; }
    public BufferedImage getGameOverScreen() { return gameOverScreen; }
    public BufferedImage getHeartIcon() { return heartIcon; }
    public BufferedImage getCoinIcon() { return coinIcon; }
    public BufferedImage getSelectIcon() { return selectIcon; }
}
