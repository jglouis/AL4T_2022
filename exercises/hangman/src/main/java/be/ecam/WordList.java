package be.ecam;
import java.util.Random;

public class WordList {
    private static final String[] WORDS = {"JAVA", "APPLE","BANANA","CARROT","DOG","CAT","COMPUTER"};

    public static String getRandomWord() {
        Random random = new Random();
        return WORDS[random.nextInt(WORDS.length)];
    }
}