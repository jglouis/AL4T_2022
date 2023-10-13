package be.ecam;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World");

        Deck myDeck = new Deck();

        Card card = myDeck.draw();
        int rank = card.rank;
        Suit suit = card.suit;

        printCard(rank, suit);
    }

    public static void printCard(int rank, Suit suit) {
        String[] ranks = {"", "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] suits = {"♥", "♦", "♠", "♣"};

        System.out.println("┌─────────┐");
        System.out.println("│       " + ranks[rank] + " │");
        System.out.println("│         │");
        System.out.println("│    " + suits[suit.ordinal()] + "    │"); // se base sur l'odre dans la liste
        System.out.println("│         │");
        System.out.println("│   " + ranks[rank] + "     │");
        System.out.println("└─────────┘");
    }
}
