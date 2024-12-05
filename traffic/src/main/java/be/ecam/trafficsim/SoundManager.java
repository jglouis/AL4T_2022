package be.ecam.trafficsim;

import org.jetbrains.annotations.NotNull;

import javax.sound.sampled.*;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SoundManager implements Closeable {
   private final Map<String, Clip> clipsByName = new HashMap<>();

    public SoundManager(Map<String, InputStream> inputs) {
        inputs.forEach((clipName, inputStream) -> {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputs.get(clipName));
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clipsByName.put(clipName, clip);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        });
    }

    /**
     * Play given clip name in a continuous loop.
     * @param clipName the clip name
     */
    public void playContinuousLoop(@NotNull String clipName) {
        try {
            Clip clip = clipsByName.get(clipName); // <- what could go wrong ? A name does not exist in the map
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (NullPointerException e) {
            String errorMsg = String.format("The clip name %s could not be found", clipName);
            System.err.println(errorMsg);
        }
    }

    // TODO playOnce

    @Override
    public void close() {
        for (Clip clip : clipsByName.values()) {
            clip.close();
        }
    }
}
