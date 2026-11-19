package be.ecam.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileSystemPhotoSourceTest {

    @TempDir
    Path tempDirectory;

    private FileSystemPhotoSource source;

    @BeforeEach
    void setUp() {
        source = new FileSystemPhotoSource();
    }

    @Test
    void listsSupportedImagesRecursively() throws IOException {
        Path nestedDirectory = Files.createDirectories(tempDirectory.resolve("nested"));
        Files.writeString(tempDirectory.resolve("sunset.jpg"), "jpg");
        Files.writeString(tempDirectory.resolve("portrait.JPEG"), "jpeg");
        Files.writeString(nestedDirectory.resolve("icon.png"), "png");
        Files.writeString(tempDirectory.resolve("notes.txt"), "text");

        List<Photo> photos = source.listPhotos(tempDirectory);
        Set<String> names = photos.stream().map(Photo::name).collect(Collectors.toSet());

        assertTrue(names.containsAll(Set.of("sunset.jpg", "portrait.JPEG", "icon.png")));
        assertTrue(names.stream().noneMatch(name -> name.equals("notes.txt")));
    }

    @Test
    void returnsAnEmptyListForMissingDirectory() {
        List<Photo> photos = source.listPhotos(tempDirectory.resolve("missing"));

        assertTrue(photos.isEmpty());
    }

    @Test
    void loadsPhotoContent() throws IOException, PhotoException {
        Path image = tempDirectory.resolve("image.jpg");
        byte[] content = {10, 20, 30, 40};
        Files.write(image, content);

        try (InputStream input = source.load(new Photo(image))) {
            assertArrayEquals(content, input.readAllBytes());
        }
    }

    @Test
    void throwsPhotoExceptionWhenPhotoCannotBeLoaded() {
        Photo missingPhoto = new Photo(tempDirectory.resolve("missing.jpg"));

        PhotoException exception = assertThrows(
                PhotoException.class,
                () -> source.load(missingPhoto));

        assertTrue(exception.getMessage().contains("missing.jpg"));
    }
}
