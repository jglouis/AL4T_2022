package manager;

import javax.inject.Singleton;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;
import javax.inject.Inject;

@Singleton
public class SoundManager implements ISoundManager {

    private final Clip background;
    private long clipTime = 0;

    @Inject
    public SoundManager() {
        background = getClip(loadAudio("background"));
    }

    private AudioInputStream loadAudio(String url) {
        try {
            InputStream audioSrc = getClass().getResourceAsStream("/media/audio/" + url + ".wav");
            assert audioSrc != null;
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            return AudioSystem.getAudioInputStream(bufferedIn);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    private Clip getClip(AudioInputStream stream) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            return clip;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Clip getBackgroundClip() {
        return background;
    }

    public long getClipTime() {
        return clipTime;
    }

    @Override
    public void resumeBackground(){
        background.setMicrosecondPosition(clipTime);
        background.start();
    }

    @Override
    public void pauseBackground(){
        clipTime = background.getMicrosecondPosition();
        background.stop();
    }

    @Override
    public void restartBackground() {
        clipTime = 0;
        resumeBackground();
    }

    @Override
    public void playJump() {
        Clip clip = getClip(loadAudio("jump"));
        assert clip != null;
        clip.start();

    }

    @Override
    public void playCoin() {
        Clip clip = getClip(loadAudio("coin"));
        assert clip != null;
        clip.start();
    }

    @Override
    public void playFireball() {
        Clip clip = getClip(loadAudio("fireball"));
        assert clip != null;
        clip.start();
    }

    @Override
    public void playGameOver() {
        Clip clip = getClip(loadAudio("gameOver"));
        assert clip != null;
        clip.start();
    }

    @Override
    public void playStomp() {
        Clip clip = getClip(loadAudio("stomp"));
        assert clip != null;
        clip.start();
    }

    @Override
    public void playOneUp() {
        Clip clip = getClip(loadAudio("oneUp"));
        assert clip != null;
        clip.start();
    }

    @Override
    public void playSuperMushroom() {
        Clip clip = getClip(loadAudio("superMushroom"));
        assert clip != null;
        clip.start();
    }

    @Override
    public void playMarioDies() {
        Clip clip = getClip(loadAudio("marioDies"));
        assert clip != null;
        clip.start();
    }

    @Override
    public void playFireFlower() {

    }
}
