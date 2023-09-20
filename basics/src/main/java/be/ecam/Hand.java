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

    @Override
    public String toString() {
        return "Hand{" +
                "cards=" + cards +
                '}';
    }
}
