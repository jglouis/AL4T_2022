package be.ecam.basics.exercises;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

public class Catalog {
    public static class Product {
        private final String name;
        private final List<String> tags;

        public Product(String name, @NotNull List<String> tags) {
            this.name = name;
            this.tags = tags; // added if null then send back an empty list
            // if (tags == null) {
            //     this.tags = new ArrayList<>();
            // } else {
            //     this.tags = tags;
            // }
        }

        public String getName() { return name; }
        public List<String> getTags() { return tags; }
    }

    public static int countTags(Product p) {
        return p.getTags().size();
    }
}
