package be.ecam.basics.exercises;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListUtilsTest {

    @Test
    void removesShortStringsInPlace() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "abcd", "zz", "longer"));
        List<String> out = ListUtils.removeShortStrings(list, 3);
        assertEquals(Arrays.asList("abcd", "longer"), out);
        assertSame(list, out, "should modify in place and return the same list");
    }

    @Test
    void leavesListUntouchedWhenAllLongEnough() {
        List<String> list = new ArrayList<>(Arrays.asList("abc", "abcd"));
        List<String> out = ListUtils.removeShortStrings(list, 3);
        assertEquals(Arrays.asList("abc", "abcd"), out);
    }
}
