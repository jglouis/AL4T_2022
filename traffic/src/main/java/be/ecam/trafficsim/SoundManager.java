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

    // DONE refactor the SoundManager V
    // - remove the hardcoded file names in this class V
    // - instead Use InputStream interface to respect SOLID principles V
    public SoundManager(List<InputStream> inputs) {

        Clip driftClip = null;
        Clip clip = null;

        try {
            // DONE use inputs V
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(inputs.get(0));
            AudioInputStream driftStream = AudioSystem.getAudioInputStream(inputs.get(1));

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
