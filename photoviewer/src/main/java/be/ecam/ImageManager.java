package be.ecam;

import java.io.File;

public class ImageManager {
    
    private File[] files;
    private int currentIndex;

    private Runnable updateCallback;

    public ImageManager(File[] files) {
        this.files = files;
        this.currentIndex = 0;
    }

    //Permet à l'interface graphique de suivre les changements
    public void setUpdateCallback(Runnable callback) {
        this.updateCallback = callback;
    }

    //Méthode pour déclencher la mise à jour
    private void notifyUpdate() {
        if (updateCallback != null) {
            updateCallback.run();
        }
    }

    public File[] getFiles() {
        return files;
    }

    public File getCurrentFile() {
        if (files == null || files.length == 0) return null;
        return files[currentIndex];
    }

    //Passer à l'image suivante et revenir au début si on a atteint la fin
    public void next() {
        if (files == null || files.length == 0) return;
        currentIndex++;
        if (currentIndex >= files.length) {
            currentIndex = 0;
        }
        notifyUpdate();
    }

    //Passer à l'image précédente et basculer à la toute fin si on est au début
    public void previous() {
        if (files == null || files.length == 0) return;
        currentIndex--;
        if (currentIndex < 0) {
            currentIndex = files.length - 1;
        }
        notifyUpdate();
    }
    
    public void setIndex(int index) {
        if (index >= 0 && index < files.length) {
            this.currentIndex = index;
            notifyUpdate();
        }
    }
}