package be.ecam;

import java.util.Collections;
import java.util.Stack;

/**
 * Provides the default shuffling mechanism for a deck of cards.
 * This uses the {@link Collections#shuffle(java.util.List)} method.
 */
public class DefaultShuffler implements Shuffler {

    /**
     * Shuffles the given stack of cards using the default mechanism.
     *
     * @param cards The stack of {@link Card} objects to be shuffled.
     */
    @Override
    public void shuffle(Stack<Card> cards) {
        Collections.shuffle(cards);
    }
}






