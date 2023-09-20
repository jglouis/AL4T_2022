package be.ecam;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    // Each array is indexed by player number.
    // Player 0 is the human player.
    private static final Hand[] hands = new Hand[]{new Hand(), new Hand(), new Hand(), new Hand()};
    private static final int[] points = new int[4];
    private static int startingPlayer;
    private static final Card[] currentTrick = new Card[4];

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
            String[] split = userInput.split(" ");
            String command = split[0];
            switch (command) {
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
                    for (int i = 0; i < hands.length; i++) {
                        for (int j = 0; j < 13; j++) {
                            Card card = deck.draw();
                            if (card == null) {
                                // should really never happen.
                                throw new RuntimeException("WTF?");
                            }
                            // the player getting the 2 of Club is the starting player for the first trick.
                            if (Suit.CLUB.equals(card.getSuit()) && card.getValue() == 2) {
                                startingPlayer = i;
                            }
                            hands[i].addCard(card);
                        }
                    }
                    // sort your hand
                    hands[0].sort();
                }
                case "play" -> {
                    if (split.length < 2) {
                        System.out.println("You have to specify the index of the card you want to play.");
                        continue;
                    }
                    try {
                        int cardIndex = Integer.parseInt(split[1]);
                        Card playedCard = hands[0].take(cardIndex);
                        currentTrick[0] = playedCard;
                    } catch (NumberFormatException e) {
                        System.out.printf("Could not parse %s to an integer.\n", split[1]);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.printf("%d is not a valid index.\n", Integer.parseInt(split[1]));
                    }
                }
                case "exit" -> // exit program
                        System.exit(0);
            }
        }
    }
}
