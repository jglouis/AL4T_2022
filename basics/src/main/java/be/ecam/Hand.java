package be.ecam;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A Hand of {@link Card}.
 */
public class Hand {
    private final List<Card> cards = new ArrayList<>();

    /**
     * Add a {@link Card} to the hand.
     *
     * @param card the {@link Card} to add
     */
    public void addCard(@NotNull Card card) {
        cards.add(card);
    }

    /**
     * Remove a {@link Card} from hand.
     *
     * @param index the card index
     * @return the {@link Card}
     * @throws IndexOutOfBoundsException if index was out of bond
     */
    @NotNull
    public Card take(int index) {
        return cards.remove(index);
    }

    /**
     * Return the {@link Card} at given index.
     *
     * @param index the {@link Card}'s index
     * @return The {@link Card} to return
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public Card get(int index) {
        return cards.get(index);
    }

    /**
     * Check is the hand contains the given {@link Suit}.
     *
     * @param suit the given {@link Suit}
     * @return `true` if the hand contains at least one {@link Card} of this {@link Suit}. `false` otherwise.
     */
    public boolean has(@NotNull Suit suit) {
        return cards.stream().anyMatch(card -> suit.equals(card.getSuit()));
    }

    /**
     * Empty the hand.
     */
    public void empty() {
        cards.clear();
    }

    /**
     * sort the hand from Club to Spade to Diamond to Heart, then by ascending order of rank.
     */
    public void sort() {
        cards.sort(
                Comparator.comparingInt((Card card) -> card.getSuit().ordinal())
                        .thenComparingInt(Card::getValue));
    }

    /**
     * Get {@link Hand} size.
     *
     * @return number of {@link Card} in hand.
     */
    public int size() {
        return cards.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Hand:\n");
        for (int i = 0; i < cards.size(); i++) {
            String handSlot = String.format("%d: %s\n", i, cards.get(i));
            sb.append(handSlot);
        }
        return sb.toString();
    }
}
