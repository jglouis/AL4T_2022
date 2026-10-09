package be.ecam;

import javax.swing.*;
import java.awt.*;

public class ViewerFrame {

    private ImageManager manager;

    public ViewerFrame(ImageManager manager) {
        JFrame frame = new JFrame("Simple photo Viewer"); 
        
        this.manager = manager;

        // Configuration de base de la fenêtre
        int targetWidth = 1000;
        int targetHeight = 600;
        frame.setSize(targetWidth, targetHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Création des deux panneaux (Gauche et Droite)
        MainDisplayPanel rightPanel = new MainDisplayPanel(manager);
        ThumbnailPanel leftPanel = new ThumbnailPanel(manager);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel.getPanel(), rightPanel.getPanel());
        
        splitPane.setDividerLocation(250); 

        //Ajout du séparateur à la fenêtre et affichage
        frame.add(splitPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}