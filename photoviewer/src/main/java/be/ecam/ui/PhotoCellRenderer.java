package be.ecam.ui;

import be.ecam.domain.Photo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static be.ecam.ui.UiUtils.scale;

public class PhotoCellRenderer extends JLabel implements ListCellRenderer<Photo> {

    public PhotoCellRenderer() {
        setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(
            JList<? extends Photo> list,
            Photo photo,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        // Handle the case where photo is null.
        if (photo == null) {
            setText("No photo!");
            return this;
        }

        // Happy path
        try {
            BufferedImage original = ImageIO.read(photo.path().toFile());
            if (original == null) {
                setIcon(null);
            } else {
                setIcon(new ImageIcon(scale(original, 120, 120)));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}
