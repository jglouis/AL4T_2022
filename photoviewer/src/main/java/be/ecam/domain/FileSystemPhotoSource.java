package be.ecam.domain;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class FileSystemPhotoSource implements PhotoSource {

    @Override
    public List<Photo> listPhotos(Path directory) {
        try {
            return Files.walk(directory)
                    .filter(this::isImage)
                    .map(Photo::new)
                    .toList();
        } catch (IOException e) {
            return Collections.emptyList();
        }

    }

    private boolean isImage(Path path) {
        String name = path.getFileName().toString();
        String lower  = name.toLowerCase();
        return lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".png");
    }

    @Override
    public InputStream load(Photo photo) throws PhotoException {
        try {
            return Files.newInputStream(photo.path());
        } catch (IOException e) {
            throw new PhotoException("Impossible to fetch image!: " + photo.name());
        }
    }
}