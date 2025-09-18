package be.ecam.basics.exercises;

import java.util.List;

public class ListUtils {
    public static List<String> removeShortStrings(List<String> list, int minLen) {
        for (String s : list) {
            if (s.length() < minLen) {
                list.remove(s);
            }
        }
        return list;
    }
}
