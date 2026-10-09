package be.ecam;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ThumbnailPanel {

    private JScrollPane scrollPane;

    public ThumbnailPanel(ImageManager manager) {
        
        //Création du panneau intérieur qui contiendra la grille
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(0, 2, 5, 5)); //GridLayout(lignes, colonnes, espacementHorizontal, espacementVertical)

        File[] files = manager.getFiles();
        
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                
                //On doit "figer" l'index pour pouvoir l'utiliser dans le clic du bouton
                final int index = i; 
                File file = files[i];
                
                try {
                    //On charge l'image
                    BufferedImage img = ImageIO.read(file);
                    
                    if (img != null) {
                        //On crée une miniature de 100x100 pixels
                        Image thumbnail = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        
                        JButton thumbButton = new JButton(new ImageIcon(thumbnail));
                        
                        //Action quand on clique sur la miniature
                        thumbButton.addActionListener(e -> {
                        manager.setIndex(index);
                    });
                        
                        gridPanel.add(thumbButton);
                    }
                } catch (Exception e) {
                    System.err.println("Impossible de charger la miniature pour : " + file.getName());
                }
            }
        }

        scrollPane = new JScrollPane(gridPanel);
        
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    }
    public JScrollPane getPanel() {
        return scrollPane;
    }
}