package be.ecam.domain;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PhotoTest {

    @Test
    void exposesItsNameAndPath() {
        Path path = Path.of("photos", "holiday.JPG");
        Photo photo = new Photo(path);

        assertEquals(path, photo.path());
        assertEquals("holiday.JPG", photo.name());
    }
}
