package be.ecam;

import be.ecam.domain.FileSystemPhotoSource;
import be.ecam.domain.PhotoSource;
import be.ecam.ui.MainWindow;
import be.ecam.ui.PhotoCache;

import javax.swing.SwingUtilities;

public class Main {
    public static final int THUMBNAIL_SIZE = 120;
    private static final int THUMBNAIL_CACHE_MAX_BYTES = 500 * 1024 * 1024;

    public static void main(String[] args) {
        PhotoCache photoCache = new PhotoCache(THUMBNAIL_CACHE_MAX_BYTES, THUMBNAIL_SIZE);
        PhotoSource source = new FileSystemPhotoSource();
        SwingUtilities.invokeLater(() -> new MainWindow(source, photoCache).setVisible(true));
    }
}