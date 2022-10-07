package be.ecam.solid.dip.better;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ReportGenerator {
    public void writeMessage(OutputStream is, String message) throws IOException {
        is.write(message.getBytes(StandardCharsets.UTF_8));
    }

    // usage example
    public static void main(String[] args) {
        File file = new File("test.txt");
        try {
            OutputStream os = new FileOutputStream(file);
            ReportGenerator rg = new ReportGenerator();
            rg.writeMessage(os, "the cake is a lie");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
