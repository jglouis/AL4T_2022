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
}
