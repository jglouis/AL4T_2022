package be.ecam.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static be.ecam.ui.UiUtils.scale;

public class ImagePanel extends JPanel {

    private final JLabel label = new JLabel("", SwingConstants.CENTER);

    public ImagePanel() {
        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);
    }

    public void showImage(BufferedImage image) {
        int maxWidth = getWidth();
        int maxHeight = getHeight();

        Image scaled = scale(image, maxWidth, maxHeight);
        label.setIcon(new ImageIcon(scaled));
        label.setText("");

        revalidate();
        repaint();
    }

    public void clear() {
        label.setIcon(null);
        label.setText("Aucune image");
        revalidate();
        repaint();
    }
}