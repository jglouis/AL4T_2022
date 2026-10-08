package be.ecam.ui;

import javax.swing.*;
import java.awt.Color;

public class StatusBar extends JLabel {

    public StatusBar() {
        super(" ");
        setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
    }

    public void showMessage(String message) {
        setForeground(Color.DARK_GRAY);
        setText(message);
    }

    public void showError(String message) {
        setForeground(Color.RED);
        setText(message);
    }
}