package manager;

import model.hero.Mario;
import view.Animation;
import view.ImageLoader;
import view.StartScreenSelection;
import view.UIManager;
import java.awt.event.KeyEvent;

import javax.swing.*;
import java.awt.*;

public class GameEngine implements InputListener, Runnable {

    private static final int WIDTH = 1268;
    private static final int HEIGHT = 708;
    private static final double TICKS_PER_SECOND = 60.0;
    private static final double NS_PER_TICK = 1000000000 / TICKS_PER_SECOND;

    private JFrame frame;
    private MapManager mapManager;
    private UIManager uiManager;
    private SoundManager soundManager;
    private ImageLoader imageLoader;
    private Camera camera;

    private GameStatus gameStatus = GameStatus.START_SCREEN;
    private StartScreenSelection startScreenSelection = StartScreenSelection.START_GAME;
    private boolean isRunning;
    private Thread thread;
    private int selectedMap = 0;

    private GameEngine() {
        init();
    }

    private void init() {
        imageLoader = new ImageLoader();
        gameStatus = GameStatus.START_SCREEN;
        camera = new Camera();
        soundManager = new SoundManager();
        mapManager = new MapManager();

        uiManager = new UIManager(this, WIDTH, HEIGHT);
        InputManager inputManager = new InputManager(this);
        setupFrame(inputManager);
        start();
    }

    private void setupFrame(InputManager inputManager) {
        frame = new JFrame("Super Mario Bros.");
        frame.add(uiManager);
        frame.addKeyListener(inputManager);
        frame.addMouseListener(inputManager);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private synchronized void start() {
        if (isRunning) return;

        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public void reset() {
        camera = new Camera();
        soundManager.restartBackground();
        gameStatus = GameStatus.START_SCREEN;
    }

    public void selectMap() {
        String path = gameStatus == GameStatus.MAP_SELECTION ?
                uiManager.selectMapViaKeyboard(selectedMap) :
                uiManager.selectMapViaMouse(uiManager.getMousePosition());

        if (path != null) createMap(path);
    }

    public void changeSelectedMap(boolean up){
        selectedMap = uiManager.changeSelectedMap(selectedMap, up);
    }

    private void createMap(String path) {
        boolean loaded = mapManager.createMap(imageLoader, path);
        gameStatus = loaded ? GameStatus.RUNNING : GameStatus.START_SCREEN;
        if(loaded) soundManager.restartBackground();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double delta = 0;
        long timer = System.currentTimeMillis();

        while (isRunning && !thread.isInterrupted()) {

            long now = System.nanoTime();
            delta += (now - lastTime) / NS_PER_TICK;
            lastTime = now;
            while (delta >= 1) {
                if (gameStatus == GameStatus.RUNNING) {
                    gameLoop();
                }
                delta--;
            }
            render();

            if(gameStatus != GameStatus.RUNNING) {
                timer = System.currentTimeMillis();
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                mapManager.updateTime();
            }
        }
    }

    private void render() {
        uiManager.repaint();
    }

    private void gameLoop() {
        updateLocations();
        checkCollisions();
        updateCamera();

        if (isGameOver()) {
            setGameStatus(GameStatus.GAME_OVER);
        }

        int missionPassed = passMission();
        if(missionPassed > -1){
            mapManager.acquirePoints(missionPassed);
            //setGameStatus(GameStatus.MISSION_PASSED);
        } else if(mapManager.endLevel())
            setGameStatus(GameStatus.MISSION_PASSED);
    }

    private void updateCamera() {
        Mario mario = mapManager.getMario();
        double marioVelocityX = mario.getVelX();
        double shiftAmount = 0;

        if (marioVelocityX > 0 && mario.getX() - 600 > camera.getX()) {
            shiftAmount = marioVelocityX;
        }

        camera.moveCam(shiftAmount, 0);
    }

    private void updateLocations() {
        mapManager.updateLocations();
    }

    private void checkCollisions() {
        mapManager.checkCollisions(this, soundManager);
    }

    public void receiveInput(ButtonAction input) {
        switch(gameStatus) {
            case START_SCREEN:
                handleStartScreenInput(input); break;
            case MAP_SELECTION:
                handleMapSelectionInput(input); break;
            case RUNNING:
                handleRunningGameInput(input); break;
            case PAUSED:
                if (input == ButtonAction.PAUSE_RESUME) togglePauseGame(); break;
            case GAME_OVER:
            case MISSION_PASSED:
                if (input == ButtonAction.GO_TO_START_SCREEN) reset(); break;
            default:
                break;
        }

        if(input == ButtonAction.GO_TO_START_SCREEN) {
            gameStatus = GameStatus.START_SCREEN;
        }
    }

    private void handleStartScreenInput(ButtonAction input) {
        switch(input) {
            case SELECT:
                switch(startScreenSelection) {
                    case START_GAME: startGame(); break;
                    case VIEW_ABOUT: gameStatus = GameStatus.ABOUT_SCREEN; break;
                    case VIEW_HELP: gameStatus = GameStatus.HELP_SCREEN; break;
                }
                break;
            case GO_UP:
            case GO_DOWN:
                startScreenSelection = startScreenSelection.select(input == ButtonAction.GO_UP);
                break;
            default:
                break;
        }
    }

    private void handleMapSelectionInput(ButtonAction input) {
        switch(input) {
            case SELECT: selectMap(); break;
            case GO_UP:
            case GO_DOWN:
                selectedMap = uiManager.changeSelectedMap(selectedMap, input == ButtonAction.GO_UP);
                break;
            default:
                break;
        }
    }

    private void handleRunningGameInput(ButtonAction input) {
        Mario mario = mapManager.getMario();
        switch(input) {
            case JUMP: mario.jump(soundManager); break;
            case M_RIGHT: mario.move(true, camera); break;
            case M_LEFT: mario.move(false, camera); break;
            case ACTION_COMPLETED: mario.setVelX(0); break;
            case FIRE: mapManager.fire(soundManager); break;
            case PAUSE_RESUME: togglePauseGame(); break;
            default: break;
        }
    }

    public void togglePauseGame() {
        if (gameStatus == GameStatus.RUNNING) {
            gameStatus = GameStatus.PAUSED;
            soundManager.pauseBackground();
        } else if (gameStatus == GameStatus.PAUSED) {
            gameStatus = GameStatus.RUNNING;
            soundManager.resumeBackground();
        }
    }

    private void selectOption(boolean selectUp) {
        startScreenSelection = startScreenSelection.select(selectUp);
    }

    private void startGame() {
        if (gameStatus != GameStatus.GAME_OVER) {
            setGameStatus(GameStatus.MAP_SELECTION);
        }
    }

    public void onKeyPressed(int keyCode) {
        ButtonAction currentAction = mapActionFromKeyCode(keyCode);
        notifyInput(currentAction);
    }

    private ButtonAction mapActionFromKeyCode(int keyCode) {
        GameStatus status = getGameStatus();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                return (status == GameStatus.START_SCREEN || status == GameStatus.MAP_SELECTION) ?
                        ButtonAction.GO_UP : ButtonAction.JUMP;
            case KeyEvent.VK_DOWN:
                return (status == GameStatus.START_SCREEN || status == GameStatus.MAP_SELECTION) ?
                        ButtonAction.GO_DOWN : ButtonAction.NO_ACTION;
            case KeyEvent.VK_RIGHT:
                return ButtonAction.M_RIGHT;
            case KeyEvent.VK_LEFT:
                return ButtonAction.M_LEFT;
            case KeyEvent.VK_ENTER:
                return ButtonAction.SELECT;
            case KeyEvent.VK_ESCAPE:
                return (status == GameStatus.RUNNING || status == GameStatus.PAUSED) ?
                        ButtonAction.PAUSE_RESUME : ButtonAction.GO_TO_START_SCREEN;
            case KeyEvent.VK_SPACE:
                return ButtonAction.FIRE;
            default:
                return ButtonAction.NO_ACTION;
        }
    }

    @Override
    public void onKeyReleased(int keyCode) {
        if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_LEFT)
            notifyInput(ButtonAction.ACTION_COMPLETED);
    }

    @Override
    public void onMousePressed() {
        if(getGameStatus() == GameStatus.MAP_SELECTION) {
            selectMap();
        }
    }

    private void notifyInput(ButtonAction action) {
        if(action != ButtonAction.NO_ACTION)
            receiveInput(action);
    }

    public void shakeCamera(){
        camera.shakeCamera();
    }

    private boolean isGameOver() {
        if(gameStatus == GameStatus.RUNNING)
            return mapManager.isGameOver();
        return false;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public StartScreenSelection getStartScreenSelection() {
        return startScreenSelection;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getScore() {
        return mapManager.getScore();
    }

    public int getRemainingLives() {
        return mapManager.getRemainingLives();
    }

    public int getCoins() {
        return mapManager.getCoins();
    }

    public int getSelectedMap() {
        return selectedMap;
    }

    public void drawMap(Graphics2D g2) {
        mapManager.drawMap(g2);
    }

    public Point getCameraLocation() {
        return new Point((int)camera.getX(), (int)camera.getY());
    }

    private int passMission(){
        return mapManager.passMission();
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public static void main(String... args) {
        new GameEngine();
    }

    public int getRemainingTime() {
        return mapManager.getRemainingTime();
    }
}