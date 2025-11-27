package manager;

import model.hero.Mario;
import model.prize.OneUpMushroom;
import org.junit.jupiter.api.Test;
import testsupport.FakeImageLoader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class OneUpMushroomTest {

    @Test
    void oneUpRaisesLivesToThreeButNotAbove() {
        Mario mario = new Mario(0, 0, new FakeImageLoader());
        GameEngine engine = mock(GameEngine.class);
        OneUpMushroom shroom = new OneUpMushroom(0, 0, null);

        mario.setRemainingLives(2);
        shroom.onTouch(mario, engine);
        assertThat(mario.getRemainingLives()).isEqualTo(3);

        shroom.onTouch(mario, engine);
        assertThat(mario.getRemainingLives()).isEqualTo(3);
        verify(engine, atLeastOnce()).playOneUp();
    }
}
