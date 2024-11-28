package be.ecam.trafficsim;

import javax.sound.sampled.*;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

public class SoundManager implements Closeable {
    private final Clip clip;
    private final Clip driftClip;

    // TODO refactor the SoundManager
    // - remove the hardcoded file names in this class
    // - instead Use InputStream interface to respect SOLID principles
    public SoundManager(/*List<InputStream> inputs*/) {
        Clip clip = null;
        Clip driftClip = null;
        try {
            // TODO use inputs
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResourceAsStream("/Traffic Sounds - Free Sound Effects - Traffic Sound Clips - Sound Bites.wav")));
            AudioInputStream driftStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResourceAsStream("/drift.wav")));
            clip = AudioSystem.getClip();
            driftClip = AudioSystem.getClip();
            clip.open(inputStream);
            driftClip.open(driftStream);
        } catch (NullPointerException | UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println(e.getMessage());
            System.exit(1);
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
}
