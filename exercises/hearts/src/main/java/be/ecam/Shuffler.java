package be.ecam;

import java.util.Stack;

/**
 * Represents a mechanism for shuffling cards in a deck.
 * Implementations of this interface can provide different shuffling strategies.
 */
public interface Shuffler {

    /**
     * Shuffles the given stack of cards.
     *
     * @param cards The stack of {@link Card} objects to be shuffled.
     */
    void shuffle(Stack<Card> cards);
}

