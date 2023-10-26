package manager;

import javax.sound.sampled.Clip;

public interface SoundManager_I {
    void resumeBackground();

    void pauseBackground();

    void restartBackground() ;

    void playJump();

    void playCoin() ;

    void playFireball();

    void playGameOver();

    void playStomp() ;

    void playOneUp();

    void playSuperMushroom() ;

    void playMarioDies();

    void playFireFlower();
}
