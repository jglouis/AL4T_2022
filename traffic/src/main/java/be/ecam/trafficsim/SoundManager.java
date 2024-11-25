package be.ecam.trafficsim;

import javax.sound.sampled.*;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SoundManager implements Closeable {
    private final Clip clip;
    private final Clip driftClip;

    public SoundManager(List<InputStream> inputs) {
        if (inputs == null || inputs.size() < 2) {
            throw new IllegalArgumentException("At least two input streams are required.");
        }

        Clip clip = null;
        Clip driftClip = null;
        try {
            // Use the provided InputStreams
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(inputs.get(0));
            AudioInputStream driftStream = AudioSystem.getAudioInputStream(inputs.get(1));

            clip = AudioSystem.getClip();
            driftClip = AudioSystem.getClip();

            clip.open(inputStream);
            driftClip.open(driftStream);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            System.err.println("Error initializing SoundManager: " + e.getMessage());
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
