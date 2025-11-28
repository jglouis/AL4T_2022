package model.hero;

import manager.ICamera;
import manager.IMarioEngineFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testsupport.FakeImageLoader;
import view.IImageLoader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MarioTest {

    private IImageLoader imageLoader;

    @BeforeEach
    void setUp() {
        imageLoader = new FakeImageLoader();
    }

    @Test
    void jumpSetsVerticalVelocityAndPlaysSound() {
        Mario mario = new Mario(0, 0, imageLoader);
        IMarioEngineFacade engine = mock(IMarioEngineFacade.class);

        mario.setFalling(false);
        mario.setJumping(false);

        mario.jump(engine);

        assertThat(mario.isJumping()).isTrue();
        assertThat(mario.getVelY()).isEqualTo(10);
        verify(engine, times(1)).playJump();
    }

    @Test
    void moveRightSetsPositiveVelocity() {
        Mario mario = new Mario(0, 0, imageLoader);
        ICamera camera = mock(ICamera.class);
        when(camera.getX()).thenReturn(0.0);

        mario.move(true, camera);
        assertThat(mario.getVelX()).isEqualTo(5);
        assertThat(mario.getToRight()).isTrue();
    }

    @Test
    void moveLeftBlocksWhenBehindCamera() {
        Mario mario = new Mario(100, 0, imageLoader);
        ICamera camera = mock(ICamera.class);
        when(camera.getX()).thenReturn(150.0);

        mario.move(false, camera);
        // camera is ahead of mario; velocity should remain 0
        assertThat(mario.getVelX()).isEqualTo(0);
        assertThat(mario.getToRight()).isFalse();
    }

    @Test
    void onTouchEnemyWithNoPowerUpDecrementsLifeAndPlaysDeath() {
        Mario mario = new Mario(0, 0, imageLoader);
        IMarioEngineFacade engine = mock(IMarioEngineFacade.class);
        int lives = mario.getRemainingLives();

        boolean died = mario.onTouchEnemy(engine);

        assertThat(died).isTrue();
        assertThat(mario.getRemainingLives()).isEqualTo(lives - 1);
        verify(engine, times(1)).playMarioDies();
    }

    @Test
    void fireReturnsNullWhenNotFireForm() {
        Mario mario = new Mario(0, 0, imageLoader);
        assertThat(mario.fire()).isNull();
    }

    @Test
    void resetLocationResetsVelocityAndFlags() {
        Mario mario = new Mario(0, 0, imageLoader);
        mario.setVelX(3);
        mario.setVelY(2);
        mario.setJumping(true);
        mario.setFalling(false);

        mario.resetLocation();
        assertThat(mario.getVelX()).isZero();
        assertThat(mario.getVelY()).isZero();
        assertThat(mario.isJumping()).isFalse();
        assertThat(mario.isFalling()).isTrue();
        assertThat(mario.getX()).isEqualTo(50);
    }

    @Test
    void acquirePointsIncreasesScore() {
        Mario mario = new Mario(0, 0, imageLoader);
        int initialScore = mario.getPoints();
        mario.acquirePoints(100);
        assertThat(mario.getPoints()).isEqualTo(initialScore + 100);
    }
}
