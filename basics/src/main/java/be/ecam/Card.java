package be.ecam;

import org.jetbrains.annotations.NotNull;

public class Card {
    Suit suit;
    int rank;

    public Card(@NotNull Suit suit, int rank) {
        //if (suit == null) throw new IllegalArgumentException("Suit cannot be null");
        this.suit = suit;
        this.rank = rank;
    }
}
