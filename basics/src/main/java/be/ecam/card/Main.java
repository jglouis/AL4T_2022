package be.ecam.card;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Deck deck = new Deck();
            while (true) {
                String userInput = scanner.nextLine();
                switch (userInput) {
                    case "draw":
                        Card card = deck.draw();
                        System.out.println(card);
                        break;
                    case "exit":
                        System.exit(0);
                }
            }
        }
    }
}
