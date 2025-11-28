package manager;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class SoundManagerTest {

    @Test
    void makeSoundManager() {
        SoundManager DJ = new SoundManager();
        assertThat(DJ.getBackgroundClip()).isNotNull();
    }

    /*@Test
    void backgroundClipTime() { // TODO : fix test (clip never start : cliptime always equal 0)
        SoundManager DJ = new SoundManager();

        DJ.restartBackground();
        DJ.pauseBackground();

        long T1 = DJ.getClipTime();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        long T2 = DJ.getClipTime();
        assertThat(T1).isEqualTo(T2);

        DJ.restartBackground();
        T1 = DJ.getClipTime();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        T2 = DJ.getClipTime();
        assertThat(T1).isNotEqualTo(T2);
    }*/
}
