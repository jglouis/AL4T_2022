package manager;

import javax.sound.sampled.Clip; // Make sure to import the Clip class

public class Manager {
    public Clip clip; // This variable needs to be initialized somewhere

    // Constructor or methods to initialize the clip
    public Manager() {
        // Initialization code for clip
    }

    public void closeClip() {
        if (clip != null) {
            clip.close(); // Ensure clip is closed properly
        }
    }
}
