package view;

import manager.ButtonAction;
import manager.GameEngine;
import manager.GameStatus;
import manager.InputManager;
import model.hero.Mario;
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
    public int selectedMap = 0;
    public StartScreenSelection startScreenSelection = StartScreenSelection.START_GAME;

    public UIManager(GameEngine engine, int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));

        InputManager inputManager = new InputManager(engine);

        JFrame frame = new JFrame("Super Mario Bros.");
        frame.add(this);
        frame.addKeyListener(inputManager);
        frame.addMouseListener(inputManager);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        mapSelection = new MapSelection();
        this.engine = engine;
        this.imageResourceManager = new ImageResourceManager(engine);

        this.startScreenRenderer = new StartScreenRenderer(this);
        this.mapSelectionScreenRenderer = new MapSelectionScreenRenderer(this);
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

    public void mapSelectionRender(Graphics2D g2) {
        mapSelection.draw(g2);
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

    public void receiveInput(ButtonAction input) {

        if (engine.gameStatus == GameStatus.START_SCREEN) {
            if (input == ButtonAction.SELECT && startScreenSelection == StartScreenSelection.START_GAME) {
                engine.startGame();
            } else if (input == ButtonAction.SELECT && startScreenSelection == StartScreenSelection.VIEW_ABOUT) {
                engine.setGameStatus(GameStatus.ABOUT_SCREEN);
            } else if (input == ButtonAction.SELECT && startScreenSelection == StartScreenSelection.VIEW_HELP) {
                engine.setGameStatus(GameStatus.HELP_SCREEN);
            } else if (input == ButtonAction.GO_UP) {
                selectOption(true);
            } else if (input == ButtonAction.GO_DOWN) {
                selectOption(false);
            }
        }
        else if(engine.gameStatus == GameStatus.MAP_SELECTION){
            if(input == ButtonAction.SELECT){
                selectMapViaKeyboard();
            }
            else if(input == ButtonAction.GO_UP){
                changeSelectedMap(true);
            }
            else if(input == ButtonAction.GO_DOWN){
                changeSelectedMap(false);
            }
        } else if (engine.gameStatus == GameStatus.RUNNING) {
            Mario mario = engine.mapManager.getMario();
            if (input == ButtonAction.JUMP) {
                mario.jump(engine);
            } else if (input == ButtonAction.M_RIGHT) {
                mario.move(true, engine.camera);
            } else if (input == ButtonAction.M_LEFT) {
                mario.move(false, engine.camera);
            } else if (input == ButtonAction.ACTION_COMPLETED) {
                mario.setVelX(0);
            } else if (input == ButtonAction.FIRE) {
                engine.mapManager.fire(engine);
            } else if (input == ButtonAction.PAUSE_RESUME) {
                engine.pauseGame();
            }
        } else if (engine.gameStatus == GameStatus.PAUSED) {
            if (input == ButtonAction.PAUSE_RESUME) {
                engine.pauseGame();
            }
        } else if(engine.gameStatus == GameStatus.GAME_OVER && input == ButtonAction.GO_TO_START_SCREEN){
            engine.reset();
        } else if(engine.gameStatus == GameStatus.MISSION_PASSED && input == ButtonAction.GO_TO_START_SCREEN){
            engine.reset();
        }

        if(input == ButtonAction.GO_TO_START_SCREEN){
            engine.setGameStatus(GameStatus.START_SCREEN);
        }
    }

    private void selectOption(boolean selectUp) {
        startScreenSelection = startScreenSelection.select(selectUp);
    }

    public void selectMapViaMouse() {
        String path = mapSelection.selectMap(getMousePosition());
        if (path != null) {
            engine.createMap(path);
        }
    }

    public void selectMapViaKeyboard(){
        String path = mapSelection.selectMap(selectedMap);
        if (path != null) {
            engine.createMap(path);
        }
    }

    public void changeSelectedMap(boolean up){
        selectedMap = mapSelection.changeSelectedMap(selectedMap, up);
    }

}