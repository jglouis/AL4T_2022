package be.ecam;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainDisplayPanel  {
    
    private ImageManager manager;
    private JLabel imageLabel;
    JPanel Panel;

    public MainDisplayPanel(ImageManager manager) {
        this.manager = manager;
        
        Panel = new JPanel(new BorderLayout());

        manager.setUpdateCallback(() -> updateImage());

        //Zone de l'image au centre
        imageLabel = new JLabel("Chargement...", SwingConstants.CENTER);
        Panel.add(imageLabel, BorderLayout.CENTER);

        //Zone des boutons en bas
        JPanel buttonPanel = new JPanel(); // Par défaut, met les éléments côte à côte
        JButton prevButton = new JButton("Précédent");
        JButton nextButton = new JButton("Suivant");

        prevButton.addActionListener(e -> {
            manager.previous(); 
        });

        nextButton.addActionListener(e -> {
            manager.next();     
        });

        //On ajoute les boutons dans leur petit panneau, puis on fixe le tout en bas
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        Panel.add(buttonPanel, BorderLayout.SOUTH);

        updateImage();
    }

    //Méthode pour rafraîchir l'image
    public void updateImage() {
        File currentFile = manager.getCurrentFile();
        
        if (currentFile == null) {
            imageLabel.setText("Aucune image à afficher.");
            imageLabel.setIcon(null);
            return;
        }

        try {
            BufferedImage img = ImageIO.read(currentFile);
            if (img == null) {
                System.err.println("Format d'image non reconnu ou fichier corrompu.");
                imageLabel.setText("Format non reconnu.");
                imageLabel.setIcon(null);
                return;
            }

            int targetWidth = 720;  
            int targetHeight = 520; 

            double ratio = Math.min((double) targetWidth / img.getWidth(),
                                    (double) targetHeight / img.getHeight());
            
            int newWidth = (int) (img.getWidth() * ratio);
            int newHeight = (int) (img.getHeight() * ratio);

            Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            
            imageLabel.setText(""); 
            imageLabel.setIcon(new ImageIcon(scaledImg));
            
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
            imageLabel.setText("Erreur de chargement.");
            imageLabel.setIcon(null);
        }
    }
    public JPanel getPanel() {
        return Panel;
    }
}