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
    private final JList<Photo> photoList = new JList<>();
    private final DefaultListModel<Photo> listModel = new DefaultListModel<>();

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

        photoList.setModel(listModel);
        photoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        photoList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                onPhotoSelected();
            }
        });

        JScrollPane listScroll = new JScrollPane(photoList);
        listScroll.setPreferredSize(new Dimension(200, 0));
        add(listScroll, BorderLayout.WEST);

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
            listModel.clear();
            for (Photo photo : photos) {
                listModel.addElement(photo);
            }
            photoList.setSelectedIndex(0);
        } catch (PhotoException ex) {
            listModel.clear();
            imagePanel.clear();
            statusBar.showError(ex.getMessage());
        }
    }


    private void onPhotoSelected() {
        Photo selected = photoList.getSelectedValue();
        if (selected == null) {
            return;
        }
        try {
            BufferedImage image = photoSource.load(selected);
            imagePanel.showImage(image);
            statusBar.showMessage(selected.getName());
        } catch (PhotoException ex) {
            imagePanel.clear();
            statusBar.showError(ex.getMessage());
        }
    }
}