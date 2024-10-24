package trafficsim;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Main {
    public static void main(String[] args) {
        JFrame jF = new JFrame("Traffic Simulation");
        Simulation sim = new Simulation();
        jF.setContentPane(sim);
        jF.setSize(1366, 750);
        jF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jF.setVisible(true);
        final SoundManager soundManager = new SoundManager();
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
