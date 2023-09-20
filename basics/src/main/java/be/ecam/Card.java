package be.ecam;

import org.jetbrains.annotations.NotNull;

public class Card {
    private final Suit suit;
    private final int rank;

    public Card(@NotNull Suit suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public String toString() {
        String rankStr;
        switch (rank) {
            case 1 -> rankStr = "Ace";
            case 11 -> rankStr = "Jack";
            case 12 -> rankStr = "Queen";
            case 13 -> rankStr = "King";
            default -> rankStr = String.valueOf(rank);
        }
        return String.format("%s of %s", rankStr, suit);
    }

    public Suit getSuit() {
        return suit;
    }

    /**
     * The value of a card is equal to its rank (except for aces which have a value of 14 instead of one).
     * @return
     */
    public int getValue() {
        if (rank == 1) {
            return 14;
        }
        return rank;
    }
}
