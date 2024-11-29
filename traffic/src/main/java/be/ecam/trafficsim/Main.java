package be.ecam.trafficsim;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        JFrame jF = new JFrame("Traffic Simulation");
        Simulation sim = new Simulation();
        jF.setContentPane(sim);
        jF.setSize(1366, 750);
        jF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jF.setVisible(true);

        Map<String, InputStream> sounds = null;
        try {
            sounds = Map.of(
                    "traffic", Objects.requireNonNull(Main.class.getResourceAsStream("/Traffic Sounds - Free Sound Effects - Traffic Sound Clips - Sound Bites.wav")),
                    "drift", Objects.requireNonNull(Main.class.getResourceAsStream("/drift.wav"))
            );
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        final SoundManager soundManager = new SoundManager(sounds);
        soundManager.playContinuousLoop("traffic");
        // TODO this should be played when a car is actually braking
//        soundManager.playContinuousLoop("drift");

        jF.addWindowListener(new WindowListener() {
            public void windowOpened(WindowEvent e) {
            }

            public void windowIconified(WindowEvent e) {
            }

            public void windowDeiconified(WindowEvent e) {
            }

            public void windowDeactivated(WindowEvent e) {
            }

            public void windowClosing(WindowEvent e) {
            }

            public void windowClosed(WindowEvent e) {
                soundManager.close();
            }

            public void windowActivated(WindowEvent e) {
            }
        });
    }
}
