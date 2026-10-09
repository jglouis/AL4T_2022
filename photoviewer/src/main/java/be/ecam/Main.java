package be.ecam;

import java.io.File;
import java.io.FilenameFilter;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        //Parcourir le répertoire donné à la recherche de fichiers images
        String folderPath = args.length > 0 ? args[0] : "."; // Remplacez par le chemin de votre dossier d'images exemple C:\\Users\\name\\Pictures
        File dir = new File(folderPath);
        if (!dir.exists()) return;
        
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".jpeg") || name.toLowerCase().endsWith(".png");
            }
        });
        
        if (files == null || files.length == 0) {
            System.out.println("Aucune image trouvée dans le dossier.");
            return;
        }

        for (File file : files) {
            System.out.println(file.getName());
        }

        //Lancer l'interface graphique (UI)
        ImageManager manager = new ImageManager(files);
        ViewerFrame frame = new ViewerFrame(manager);
    }
}