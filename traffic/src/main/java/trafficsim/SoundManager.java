package trafficsim;

import javax.sound.sampled.*;
import java.io.Closeable;
import java.util.Objects;

public class SoundManager implements Closeable {
    private final Clip clip;
    private final Clip driftClip;

    public SoundManager(Clip clip, Clip driftClip) {
        if (clip == null || driftClip == null) {
            throw new IllegalArgumentException("clips can not be null !");
        }
        this.clip = clip;
        this.driftClip = driftClip;
    }

    public void play() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        driftClip.start();
    }

    @Override
    public void close() {
        clip.close();
        driftClip.close();
    }

    public static SoundManager createDefault(){
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(SoundManager.class.getResourceAsStream("/Traffic Sounds - Free Sound Effects - Traffic Sound Clips - Sound Bites.wav")));
            // " Free Sound Effects - Traffic Sound Clips - Sound Bites.wav"
            AudioInputStream driftStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(SoundManager.class.getResourceAsStream("/drift.wav")));
            Clip clip = AudioSystem.getClip();
            Clip driftClip = AudioSystem.getClip();
            clip.open(inputStream);
            driftClip.open(driftStream);

            return new SoundManager(clip, driftClip);
        } catch (Exception e) {
            throw new RuntimeException("Error", e);
        }
    }
}
