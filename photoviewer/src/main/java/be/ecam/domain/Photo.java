package be.ecam.domain;

import java.io.File;

public class Photo {
    private final File file;

    public Photo(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return file.getName();
    }

    @Override
    public String toString() {
        return getName();
    }
}