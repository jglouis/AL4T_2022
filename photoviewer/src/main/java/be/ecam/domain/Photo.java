package be.ecam.domain;

import java.io.File;
import java.nio.file.Path;

public record Photo(Path path) {

    public String name() {
        return path.getFileName().toString();
    }
}