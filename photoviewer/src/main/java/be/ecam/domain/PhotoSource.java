package be.ecam.domain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public interface PhotoSource {
    List<Photo> listPhotos(File directory) throws PhotoException;
    BufferedImage load(Photo photo) throws PhotoException;
}
