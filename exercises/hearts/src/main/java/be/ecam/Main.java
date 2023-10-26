package be.ecam;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    // Each array is indexed by player number.
    // Player 0 is the human player.
    private static final Hand[] hands = new Hand[]{new Hand(), new Hand(), new Hand(), new Hand()};
    private static final int[] points = new int[4];
    private static int currentPlayer;
    private static final Card[] currentTrick = new Card[4];
    private static Suit currentSuit = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            runAiOpponents();
            if (hasGameEnded()) {
                System.out.println("Game has ended");
            }


            // print your hand and every one score
            System.out.printf("\nYour score: %d\n", points[0]);
            System.out.printf("score adversary 1: %d\n", points[1]);
            System.out.printf("score adversary 2: %d\n", points[2]);
            System.out.printf("score adversary 3: %d\n\n", points[3]);

            System.out.println();
            System.out.println(hands[0]);
            System.out.println();

            // Show cards played
            for (int i = 0; i < currentTrick.length; i++) {
                System.out.printf("Card played by player %d: %s \n", i, currentTrick[i]);
            }

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
                                currentPlayer = i;
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
                        if (currentSuit != null && hands[0].has(currentSuit) && !currentSuit.equals(hands[0].get(cardIndex).getSuit())) {
                            System.out.printf("Must follow suit: %s\n", currentSuit);
                            continue;
                        }
                        Card playedCard = hands[0].take(cardIndex);
                        playToTrick(playedCard, 0);
                        checkForTrickEnd();
                        runAiOpponents();
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

    private static void runAiOpponents() {

        CardChooser aiOpponent = new AiOpponent();
        while (currentPlayer != 0) {
            Hand currentPlayerHand = hands[currentPlayer];
            if (currentPlayerHand.size() == 0) return;
            int index = aiOpponent.chooseCardToPlay(currentPlayerHand, currentTrick, currentSuit);
            Card cardToPlay = currentPlayerHand.take(index);
            playToTrick(cardToPlay, currentPlayer);
            if (checkForTrickEnd()) break;


        }
    }

    private static boolean checkForTrickEnd() {
        boolean haveAllCardsBeenPlayed = true;
        for (Card card : currentTrick) {
            if (card == null) {
                haveAllCardsBeenPlayed = false;
                break;
            }
        }
        if (haveAllCardsBeenPlayed) {
            // Check winner
            int highestValue = 0;
            int winner = -1;
            int points = 0;
            for (int i = 0; i < currentTrick.length; i++) {
                Card card = currentTrick[i];
                if (currentSuit.equals(card.getSuit())) {
                    int value = card.getValue();
                    if (value > highestValue) {
                        highestValue = value;
                        winner = i;
                    }
                }
                if (Suit.HEART.equals(card.getSuit())) {
                    points += 1;
                } else if (Suit.SPADE.equals(card.getSuit()) && card.getValue() == 12) {
                    points += 13;
                }
            }
            System.out.printf("Trick taken by player %d with %d points\n", winner, points);

            // Winner get points.
            Main.points[winner] += points;

            // reset trick
            Arrays.fill(currentTrick, null);
            currentSuit = null;
            currentPlayer = winner;
        }
        return haveAllCardsBeenPlayed;
    }

    private static void playToTrick(@NotNull Card card, int playerIndex) {
        // if this is the first card of the trick, set the suit
        if (isFirstTrick()) {
            currentSuit = card.getSuit();
        }
        currentTrick[playerIndex] = card;
        currentPlayer = (currentPlayer + 1) % 4;
        System.out.printf("Player %d played %s, cards left in hand : %d\n", playerIndex, card, hands[playerIndex].size());
    }

    private static boolean isFirstTrick() {
        for (Card card : currentTrick) {
            if (card != null) {
                return false;
            }
        }
        return true;
    }

    private static boolean hasGameEnded() {
        for (Hand hand : hands) {
            if (hand.size() != 0) {
                return false;
            }
        }
        return true;
    }
}
