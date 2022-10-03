package be.ecam.solid.exercises.exo1;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class BlogUser {
    void createPost(Database db, String postMessage) {
        try {
            db.Add(postMessage);
        } catch (Exception ex) {
            db.logError("An error occurred: ", ex.getMessage());
            File f = new File("error_log.txt");
            try {
                FileWriter fw = new FileWriter(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
