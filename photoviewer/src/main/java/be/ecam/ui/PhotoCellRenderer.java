package be.ecam.ui;

import be.ecam.domain.Photo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static be.ecam.ui.UiUtils.scale;

public class PhotoCellRenderer extends JLabel implements ListCellRenderer<Photo> {

    private static final int THUMBNAIL_SIZE = 120;
    private static final int CACHE_CAPACITY = 100;

    // One renderer instance per JList, so the cache lives as long as the window
    private final LruCache<Photo, ImageIcon> thumbnailCache = new LruCache<>(CACHE_CAPACITY);

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

        // Happy path: disk is only read on a cache miss
        setIcon(thumbnailFor(photo));

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }

    private ImageIcon thumbnailFor(Photo photo) {
        return thumbnailCache.get(photo).orElseGet(() -> {
            ImageIcon loaded = loadThumbnail(photo);
            if (loaded != null) {
                thumbnailCache.put(photo, loaded);
            }
            return loaded;
        });
    }

    private ImageIcon loadThumbnail(Photo photo) {
        try {
            BufferedImage original = ImageIO.read(photo.path().toFile());
            if (original == null) {
                return null;
            }
            return new ImageIcon(scale(original, THUMBNAIL_SIZE, THUMBNAIL_SIZE));
        } catch (IOException e) {
            return null;
        }
    }
}
