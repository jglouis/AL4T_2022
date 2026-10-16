package be.ecam.ui;

import be.ecam.domain.Photo;
import be.ecam.domain.PhotoException;
import be.ecam.domain.PhotoSource;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static be.ecam.Main.THUMBNAIL_SIZE;

public class MainWindow extends JFrame implements PhotoCache.Listener {

    private final PhotoSource photoSource;
    private final ImagePanel imagePanel = new ImagePanel();
    private final StatusBar statusBar = new StatusBar();
    private final JList<Photo> photoList = new JList<>();
    private final JLabel cacheStatus = new JLabel("Cache: -");
    // Thumbnail plus the 8px top and bottom border of PhotoCellRenderer.
    private static final int PHOTO_CELL_HEIGHT = THUMBNAIL_SIZE + 2 * 8;

    private final DefaultListModel<Photo> listModel = new DefaultListModel<>();

    public MainWindow(PhotoSource photoSource, PhotoCache photoCache) {
        this.photoSource = photoSource;
        ListCellRenderer<Photo> photoListCellRenderer = new PhotoCellRenderer(photoCache);
        photoCache.registerListener(this);

        setTitle("Simple Photo Viewer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JButton openButton = new JButton("Open");
        openButton.addActionListener(e -> onOpenClicked());

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolbar.add(openButton);
        toolbar.add(cacheStatus);

        add(toolbar, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);

        photoList.setModel(listModel);
        photoList.setCellRenderer(photoListCellRenderer);

        // Without a fixed height, JList calls the renderer on every element to measure it,
        // which loads every thumbnail and flushes the LRU cache when there are many photos.

        photoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        photoList.setFixedCellHeight(PHOTO_CELL_HEIGHT);
        photoList.setFixedCellWidth(PHOTO_CELL_HEIGHT);
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
        Path directory = chooser.getSelectedFile().toPath();
        try {
            List<Photo> photos = photoSource.listPhotos(directory);
            listModel.clear();
            for (Photo photo : photos) {
                listModel.addElement(photo);
            }
            photoList.setSelectedIndex(0);
        } catch (RuntimeException ex) {
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
            BufferedImage image = ImageIO.read(photoSource.load(selected));
            imagePanel.showImage(image);
            statusBar.showMessage(selected.name());
        } catch (IOException | PhotoException ex) {
            imagePanel.clear();
            statusBar.showError(ex.getMessage());
        }
    }

    @Override
    public void onUpdate(int usedBytes, int hits, int misses, int evictions) {
        double usedMegabytes = usedBytes / (1024.0 * 1024.0);
        String label = String.format("size: %.1f MB, hits: %d, misses: %d, evictions: %d",
                usedMegabytes, hits, misses, evictions);
        cacheStatus.setText(label);
    }
}