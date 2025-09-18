package be.ecam.basics.exercises;

import java.util.List;

public class Inventory {
    public static class Item {
        private final String sku;
        public Item(String sku) { this.sku = sku; }
        public String getSku() { return sku; }
    }

    public static boolean hasSku(List<Item> items, String sku) {
        for (Item i : items) {
            if (i.getSku() == sku) {
                return true;
            }
        }
        return false;
    }
}
