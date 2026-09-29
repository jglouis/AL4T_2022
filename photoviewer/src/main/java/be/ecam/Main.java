package be.ecam;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        // 1. Scan the given directory for pictures files.
        String folderPath = args.length > 0 ? args[0] : ".";
        File dir = new File(folderPath);
        if (!dir.exists()) return;
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".jpeg") || name.toLowerCase().endsWith(".png");
            }
        });
        // Because if dir exist but is not a directory dir value is null and if we don't have any file in files it's useful to continue
        if (files == null || files.length == 0) {
            System.out.println("Aucune image trouvée dans le dossier.");
            return;
        }

        for (File file : files) {
            System.out.println(file.getName());
        }

        // 2. Create the UI.
        JFrame frame = new JFrame("Simple photo Viewer");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Display the first picture in the array
        try {
            BufferedImage img = ImageIO.read(files[0]);
            frame.add(new JLabel(new ImageIcon(img)), SwingConstants.CENTER);
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }

        frame.setVisible(true);


    }
}