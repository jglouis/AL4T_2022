package be.ecam.ui;

import be.ecam.domain.Photo;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PhotoCellRenderer extends JLabel implements ListCellRenderer<Photo> {

    private final PhotoCache photoCache;

    public PhotoCellRenderer(PhotoCache photoCache) {
        this.photoCache = photoCache;
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(
            JList<? extends Photo> list,
            Photo photo,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        // This label is reused for every cell: reset what the previous cell left behind.
        setIcon(null);
        setText(null);

        if (photo == null) {
            setText("No photo!");
        } else {
            BufferedImage thumbnail = photoCache.get(photo.path());
            if (thumbnail != null) {
                setIcon(new ImageIcon(thumbnail));
            } else {
                setText("Unreadable image");
            }
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
