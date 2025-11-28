package be.ecam.basics.exercises;

import java.util.List;

public class ListUtils {
    public static List<String> removeShortStrings(List<String> list, int minLen) {
        var it = list.iterator(); // added - Using itertor instead of for-each to avoid ConcurrentModificationException
        while (it.hasNext()) {
        // for (String s : list) {
        //     if (s.length() < minLen) {
        //         list.remove(s);
        //     }
        // }
        String s = it.next();
        if (s.length() < minLen) {
            it.remove();
             }
        }
        return list;
    }
}
