package view;

import manager.GameEngine;
import manager.GameStatus;
import view.screens.ScreenRenderer;
import view.screens.StartScreenRenderer;
import view.screens.MapSelectionScreenRenderer;
import view.screens.AboutScreenRenderer;
import view.screens.HelpScreenRenderer;
import view.screens.GameOverScreenRenderer;
import view.screens.PauseScreenRenderer;
import view.screens.VictoryScreenRenderer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class UIManager extends JPanel{

    private GameEngine engine;
    private Font gameFont;
    private ImageResourceManager imageResourceManager;

    private ScreenRenderer startScreenRenderer;
    private ScreenRenderer mapSelectionScreenRenderer;
    private ScreenRenderer aboutScreenRenderer;
    private ScreenRenderer helpScreenRenderer;
    private ScreenRenderer gameOverScreenRenderer;
    private ScreenRenderer pauseScreenRenderer;
    private ScreenRenderer victoryScreenRenderer;
    private MapSelection mapSelection;

    public UIManager(GameEngine engine, int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));


        mapSelection = new MapSelection();
        this.engine = engine;
        this.imageResourceManager = new ImageResourceManager(engine);

        this.startScreenRenderer = new StartScreenRenderer(this);
        this.mapSelectionScreenRenderer = new MapSelectionScreenRenderer(this, mapSelection);
        this.aboutScreenRenderer = new AboutScreenRenderer(this);
        this.helpScreenRenderer = new HelpScreenRenderer(this);
        this.gameOverScreenRenderer = new GameOverScreenRenderer(this);
        this.pauseScreenRenderer = new PauseScreenRenderer(this);
        this.victoryScreenRenderer = new VictoryScreenRenderer(this);

        try {
            InputStream in = getClass().getResourceAsStream("/media/font/mario-font.ttf");
            gameFont = Font.createFont(Font.TRUETYPE_FONT, in);
        } catch (FontFormatException | IOException e) {
            gameFont = new Font("Verdana", Font.PLAIN, 12);
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        GameStatus gameStatus = engine.getGameStatus();

        switch(gameStatus) {
            case START_SCREEN:
                startScreenRenderer.render(g2);
                break;
            case MAP_SELECTION:
                mapSelectionScreenRenderer.render(g2);
                break;
            case ABOUT_SCREEN:
                aboutScreenRenderer.render(g2);
                break;
            case HELP_SCREEN:
                helpScreenRenderer.render(g2);
                break;
            case GAME_OVER:
                gameOverScreenRenderer.render(g2);
                break;
            default:
                renderGamePlayScreen(g2);
                break;
        }

        if(gameStatus == GameStatus.PAUSED){
            pauseScreenRenderer.render(g2);
        }
        else if(gameStatus == GameStatus.MISSION_PASSED){
            victoryScreenRenderer.render(g2);
        }

        g2.dispose();
    }

    public ImageResourceManager getImageResourceManager() {
        return this.imageResourceManager;
    }

    public GameEngine getGameEngine() {
        return this.engine;
    }

    public Font getGameFont() {
        return gameFont;
    }

    private void renderGamePlayScreen(Graphics2D g2) {
        Point camLocation = engine.getCameraLocation();
        g2.translate(-camLocation.x, -camLocation.y);
        engine.drawMap(g2);
        g2.translate(camLocation.x, camLocation.y);

        drawUIElement(g2, "TIME: " + engine.getRemainingTime(), 750, 50, 25f, null, 0, 0);
        drawUIElement(g2, "" + engine.getCoins(), getWidth()-65, 50, 30f, imageResourceManager.getCoinIcon(), getWidth()-115, 10);
        drawUIElement(g2, "" + engine.getRemainingLives(), 100, 50, 30f, imageResourceManager.getHeartIcon(), 50, 10);
        drawUIElement(g2, "Points: " + engine.getScore(), 300, 50, 25f, null, 0, 0);
    }

    private void drawUIElement(Graphics2D g2, String text, int x, int y, float fontSize, Image icon, int iconX, int iconY) {
        g2.setFont(gameFont.deriveFont(fontSize));
        g2.setColor(Color.WHITE);
        if (icon != null) {
            g2.drawImage(icon, iconX, iconY, null);
        }
        g2.drawString(text, x, y);
    }

    public String selectMapViaMouse(Point mouseLocation) {
        return mapSelection.selectMap(mouseLocation);
    }

    public String selectMapViaKeyboard(int index){
        return mapSelection.selectMap(index);
    }

    public int changeSelectedMap(int index, boolean up){
        return mapSelection.changeSelectedMap(index, up);
    }
}