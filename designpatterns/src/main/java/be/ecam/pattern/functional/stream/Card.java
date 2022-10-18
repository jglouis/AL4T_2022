package be.ecam.pattern.functional.stream;


import java.util.Objects;

/**
 * A standard playing card.
 */
public class Card {
    private final int rank;
    private final Suit suit;

    public Card(int rank, Suit suit) {
        // comment 1
        this.rank = rank;
        this.suit = suit;
        // some comments
        // comment 2
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return rank == card.rank && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }

    @Override
    public String toString() {
        String rankStr = switch (rank) {
            case 1 -> "Ace";
            case 11 -> "Jack";
            case 12 -> "Queen";
            case 13 -> "King";
            default -> String.valueOf(rank);
        };
        //return rankStr + " of " + suit.toString();
        return String.format("%s of %s", rankStr, suit);
    }

    public Suit getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }
}
