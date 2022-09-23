package be.ecam.card;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    final private List<Card> cards = new ArrayList<>();

    public Deck() {
        for (int rank = 1; rank <= 13; rank++) {
            for (Suit suit : Suit.values()) {
                Card card = new Card(rank, suit);
                cards.add(card);
            }
        }
    }

    /**
     * Draws the top card of the {@link Deck}.
     *
     * @return the drawn {@link Card}. Null if None left.
     */
    public Card draw() {
        if (cards.size() == 0) return null;
        return cards.remove(0);
    }

//    public class NoMoreCardException extends Exception {
//        @Override
//        public String getMessage() {
//            return "No card left in the deck";
//        }
//    }
}
