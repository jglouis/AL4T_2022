package be.ecam.chess;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MainTest {

    @Test
    void main() {
        // Can be seen as some kind of integration test
        InputStream in = new ByteArrayInputStream("""                
                move a2 a4
                move b7 b5
                move b1 c3
                move b5 a4
                quit"""
                .getBytes(StandardCharsets.UTF_8));
        // assertThrows(ExitException.class, () -> Main._main(in, new String[]{}));
        // TODO: Find replacement for deprecated SecurityManager (flagged for removal in Java 17)
    }
}