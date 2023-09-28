package be.ecam;

import org.jetbrains.annotations.Nullable;

import java.util.Stack;

public class Deck {
    private Stack<Card> cards = new Stack<>();

    public Deck() {
        for(int rank = 1; rank<=13; rank++) {
            for(Suit suit : Suit.values()) {
                Card card = new Card(suit, rank);
                cards.add(card);
            }
        }
    }

    /**
     * Retrieve the first card from the deck.
     *
     * @return The {@link Card} object or null if the deck was empty.
     */
    @Nullable
    public Card draw() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.pop();
    }
}
