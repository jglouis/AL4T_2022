package be.ecam.trafficsim;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import be.ecam.trafficsim.Simulation;

public class Main {
    public static void main(String[] args) {
        JFrame jF = new JFrame("Traffic Simulation");
        Simulation sim = new Simulation();
        jF.setContentPane(sim);
        jF.setSize(1366, 750);
        jF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jF.setVisible(true);

        // DONE construct the list of input streams for sounds V
        InputStream mainAudioStream = Main.class.getResourceAsStream("/Traffic Sounds - Free Sound Effects - Traffic Sound Clips - Sound Bites.wav");
        InputStream driftAudioStream = Main.class.getResourceAsStream("/drift.wav");

        List<InputStream> inputSound =  Arrays.asList(mainAudioStream, driftAudioStream);

        final SoundManager soundManager = new SoundManager( inputSound );
        soundManager.play();
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
