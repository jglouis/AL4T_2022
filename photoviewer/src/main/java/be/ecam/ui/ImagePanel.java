package be.ecam.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {

    private final JLabel label = new JLabel("", SwingConstants.CENTER);

    public ImagePanel() {
        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);
    }

    public void showImage(BufferedImage image) {
        int maxWidth = getWidth();
        int maxHeight = getHeight();

        double ratio = Math.min(
                (double) maxWidth / image.getWidth(),
                (double) maxHeight / image.getHeight());

        int newWidth = (int) (image.getWidth() * ratio);
        int newHeight = (int) (image.getHeight() * ratio);

        Image scaled = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
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