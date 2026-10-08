package be.ecam.ui;

import be.ecam.domain.Photo;
import be.ecam.domain.PhotoException;
import be.ecam.domain.PhotoSource;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class MainWindow extends JFrame {

    private final PhotoSource photoSource;
    private final ImagePanel imagePanel = new ImagePanel();
    private final StatusBar statusBar = new StatusBar();

    public MainWindow(PhotoSource photoSource) {
        this.photoSource = photoSource;

        setTitle("Simple Photo Viewer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JButton openButton = new JButton("Open");
        openButton.addActionListener(e -> onOpenClicked());

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolbar.add(openButton);

        add(toolbar, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);

        imagePanel.clear();
        statusBar.showMessage("Select folder with Open button");
    }

    private void onOpenClicked() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = chooser.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File directory = chooser.getSelectedFile();
        try {
            List<Photo> photos = photoSource.listPhotos(directory);
            BufferedImage image = photoSource.load(photos.get(0));
            imagePanel.showImage(image);
            statusBar.showMessage(photos.get(0).getName() + " — " + photos.size() + " image(s)");
        } catch (PhotoException ex) {
            imagePanel.clear();
            statusBar.showError(ex.getMessage());
        }
    }
}