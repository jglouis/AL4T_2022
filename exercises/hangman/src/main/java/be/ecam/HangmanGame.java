package be.ecam;
import java.util.Scanner;

public class HangmanGame {
    private String wordToGuess;
    private StringBuilder currentWordState;
    private int remainingTries;

    public HangmanGame() {
        this.wordToGuess = WordList.getRandomWord();
        this.currentWordState = new StringBuilder("_".repeat(wordToGuess.length()));
        this.remainingTries = 6; 
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (remainingTries > 0 && currentWordState.indexOf("_") != -1) {
            System.out.println("Current word: " + currentWordState);
            System.out.println("Tries left: " + remainingTries);
            System.out.print("Enter a letter: ");
            char guess = scanner.next().toUpperCase().charAt(0);

            if (wordToGuess.indexOf(guess) != -1) {
                for (int i = 0; i < wordToGuess.length(); i++) {
                    if (wordToGuess.charAt(i) == guess) {
                        currentWordState.setCharAt(i, guess);
                    }
                }
            } else {
                System.out.println("Incorrect!");
                remainingTries--;
            }
        }

        if (currentWordState.indexOf("_") == -1) {
            System.out.println("You guessed the word: " + currentWordState);
        } else {
            System.out.println("No more tries left. The word was: " + wordToGuess);
        }
    }

    public static void main(String[] args) {
        HangmanGame hangmanGame = new HangmanGame();
        hangmanGame.play();
    }
}
