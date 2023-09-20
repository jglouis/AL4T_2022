package be.ecam;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    // Each array is indexed by player number.
    // Player 0 is the human player.
    private static final Hand[] hands = new Hand[]{new Hand(), new Hand(), new Hand(), new Hand()};
    private static final int[] points = new int[4];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // print your hand and every one score
            System.out.printf("Your score: %d\n", points[0]);
            System.out.printf("score adversary 1: %d\n", points[1]);
            System.out.printf("score adversary 2: %d\n", points[2]);
            System.out.printf("score adversary 3: %d\n", points[3]);

            System.out.println(hands[0]);

            String userInput = scanner.nextLine();
            switch (userInput) {
                case "start" -> { // start a new game
                    // reinitialize hand
                    for (Hand hand : hands) {
                        hand.empty();
                    }
                    // ... and points
                    Arrays.fill(points, 0);

                    Deck deck = new Deck();
                    deck.shuffle();

                    // deal hands
                    for (Hand hand : hands) {
                        for (int i = 0; i <13; i++) {
                            hand.addCard(deck.draw());
                        }
                    }
                    // sort your hand
                    hands[0].sort();
                }
                case "exit" -> // exit program
                        System.exit(0);
            }
        }
    }
}
