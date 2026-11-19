package be.ecam.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PhotoExceptionTest {

    @Test
    void retainsItsMessage() {
        PhotoException exception = new PhotoException("Could not load photo");

        assertEquals("Could not load photo", exception.getMessage());
    }
}
