package be.ecam.solid.dip.bad;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReportGenerator {
    public void writeMessage(File file, String message) throws IOException {
        FileWriter fw = new FileWriter(file);
        fw.write(message);
        fw.close();
    }

    // usage example
    public static void main(String[] args) {
        File file = new File("test.txt");
        ReportGenerator rg = new ReportGenerator();

        try {
            rg.writeMessage(file, "the cake is a lie");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
