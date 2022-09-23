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
    public boolean equals(Object obj) {
        return (this.rank == ((Card) obj).rank) && this.suit == ((Card) obj).suit;
    }

}
