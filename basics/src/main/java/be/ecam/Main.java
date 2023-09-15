package be.ecam;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World");

        Deck myDeck = new Deck();

        Card card = myDeck.draw();
        int rank = card.rank;
        Suit suit = card.suit;

        System.out.printf("Card %d of %s", rank, suit);

    }

}
