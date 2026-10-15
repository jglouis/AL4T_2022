package be.ecam.ui;

import be.ecam.domain.Photo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static be.ecam.ui.UiUtils.scale;

public class PhotoCellRenderer extends JLabel implements ListCellRenderer<Photo> {

    private final ThumbnailCache cache;

    public PhotoCellRenderer(ThumbnailCache cache) {
        this.cache = cache;
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
            setIcon(null);
            return this;
        }

        setText("");

        //  On cherche dabord l'image en mémoire
        ImageIcon cachedIcon = cache.get(photo);

        if (cachedIcon != null) {
            setIcon(cachedIcon);
        } else {
            // 'image n'est pas là, on la lit depuis le disque
            try {
                BufferedImage original = ImageIO.read(photo.path().toFile());
                if (original == null) {
                    setIcon(null);
                } else {
                    ImageIcon newIcon = new ImageIcon(scale(original, 120, 120));

                    cache.put(photo, newIcon); 
                    setIcon(newIcon);
                }
            } catch (IOException e) {
                setIcon(null);
                setText("Erreur");
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
