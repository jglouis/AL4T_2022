package manager;

import model.brick.OrdinaryBrick;
import model.hero.Mario;
import view.IImageLoader;

import java.awt.*;

public interface IMapManager {

        void updateLocations() ;

        void resetCurrentMap(GameEngine engine);

        boolean createMap(IImageLoader loader, String path) ;

        void acquirePoints(int point);

        Mario getMario();

        void fire(GameEngine engine);

        boolean isGameOver();

        int getScore();

        int getRemainingLives();

        int getCoins();

        void drawMap(Graphics2D g2);

        int passMission() ;

        boolean endLevel();

        void checkCollisions(GameEngine engine);


        void addRevealedBrick(OrdinaryBrick ordinaryBrick);

        void updateTime();

        int getRemainingTime();

}
