package be.ecam.domain;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

public interface PhotoSource {
    List<Photo> listPhotos(Path directory);
    InputStream load(Photo photo) throws PhotoException;
}
