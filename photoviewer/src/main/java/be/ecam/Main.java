package be.ecam;

import be.ecam.domain.FileSystemPhotoSource;
import be.ecam.domain.PhotoSource;
import be.ecam.ui.MainWindow;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        PhotoSource source = new FileSystemPhotoSource();
        SwingUtilities.invokeLater(() -> new MainWindow(source).setVisible(true));
    }
}