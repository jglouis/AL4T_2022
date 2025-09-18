package be.ecam.basics.exercises;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    @Test
    void findsSkuByValueEquality() {
        List<Inventory.Item> items = Arrays.asList(
                new Inventory.Item("AAA"),
                new Inventory.Item("BBB"),
                new Inventory.Item("CCC")
        );
        assertTrue(Inventory.hasSku(items, new String("BBB")));
    }

    @Test
    void returnsFalseWhenNotPresent() {
        List<Inventory.Item> items = Arrays.asList(
                new Inventory.Item("AAA"),
                new Inventory.Item("BBB")
        );
        assertFalse(Inventory.hasSku(items, "CCC"));
    }
}
