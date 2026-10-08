package be.ecam.domain;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileSystemPhotoSource implements PhotoSource {

    @Override
    public List<Photo> listPhotos(File directory) throws PhotoException {
        if (!directory.exists()){ throw new PhotoException("Directory does not exist: "+directory.getAbsolutePath());}
        File[] files = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                String lower  = name.toLowerCase();
                return lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".png");
            }
        });
        if (files==null){throw new PhotoException("It is impossible to fetch all photos!: "+directory.getAbsolutePath());}
        List<Photo> photos = new ArrayList<>();
        for (File file : files) {photos.add(new Photo(file));}
        if (photos.isEmpty()){throw new PhotoException("No photos found!");}
        return photos;

    }

    @Override
    public BufferedImage load(Photo photo) throws PhotoException {
        try {
            BufferedImage image = ImageIO.read(photo.getFile());
            if (image==null){throw new PhotoException("This format is not supported!:" +photo.getName());}
            return image;
        } catch (IOException e) {
            throw new PhotoException("Impossible to fetch image!: " + photo.getName());
        }
    }
}