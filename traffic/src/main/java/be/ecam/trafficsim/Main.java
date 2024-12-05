package be.ecam.trafficsim;

import be.ecam.trafficsim.dagger.DaggerTrafficFactory;
import be.ecam.trafficsim.dagger.TrafficFactory;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Main {
    public static void main(String[] args) {
        JFrame jF = new JFrame("Traffic Simulation");
        TrafficFactory trafficFactory = DaggerTrafficFactory.builder().build();
        Simulation simulation = trafficFactory.simulation();
        simulation.start();
        jF.setContentPane(simulation);
        jF.setSize(1366, 750);
        jF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jF.setVisible(true);

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
                trafficFactory.soundManager().close();
            }

            public void windowActivated(WindowEvent e) {
            }
        });
    }
}
