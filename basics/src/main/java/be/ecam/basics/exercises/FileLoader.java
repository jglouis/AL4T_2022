package be.ecam.basics.exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileLoader {
    public static String firstLine(Path path) {
        BufferedReader br = null;
        try {
            br = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            return br.readLine();
        } catch (IOException e) {
            return null;
        } finally {
            if (br != null) {
                try { br.close(); } catch (IOException ignored) { }
            }
        }
    }
}
