package be.ecam.card;


import java.util.Objects;

/**
 * A standard playing card.
 */
public class Card extends Object {
    private int rank;
    private Suit suit;

    public Card(int rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
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
        String rankStr;
        switch (rank) {
            case 1:
                rankStr = "Ace";
                break;
            case 11:
                rankStr = "Jack";
                break;
            case 12:
                rankStr = "Queen";
                break;
            case 13:
                rankStr = "King";
                break;
            default:
                rankStr = String.valueOf(rank);
        }
//return rankStr + " of " + suit.toString();
        return String.format("%s of %s", rankStr, suit);
    }
}
