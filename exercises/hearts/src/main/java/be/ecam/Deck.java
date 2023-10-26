package be.ecam;

import org.jetbrains.annotations.Nullable;

import java.util.Stack;

/**
 * Represents a deck of cards.
 * This deck supports operations to draw and shuffle cards.
 * The shuffling behavior can be customized by injecting a Shuffler.
 */
public class Deck {

    /** Stack of cards representing the deck. */
    private final Stack<Card> cards = new Stack<>();

    /** Shuffler to customize shuffle behavior. */
    private final Shuffler shuffler;

    /**
     * Constructs a deck of cards with the given shuffling mechanism.
     *
     * @param shuffler The mechanism to shuffle cards in the deck.
     */
    public Deck(Shuffler shuffler) {
        this.shuffler = shuffler;

        // Initialize the deck with all 52 cards.
        for(int rank = 1; rank <= 13; rank++) {
            for(Suit suit : Suit.values()) {
                Card card = new Card(suit, rank);
                cards.add(card);
            }
        }
    }

    /**
     * Draws the top card from the deck.
     *
     * @return The top {@link Card} from the deck, or null if the deck is empty.
     */
    @Nullable
    public Card draw() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.pop();
    }

    /**
     * Shuffles the cards in the deck using the provided shuffling mechanism.
     */
    public void shuffle() {
        shuffler.shuffle(cards);
    }
}

