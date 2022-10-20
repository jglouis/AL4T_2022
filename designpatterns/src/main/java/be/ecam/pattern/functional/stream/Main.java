package be.ecam.pattern.functional.stream;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Card> cards = new ArrayList<>();
        for (int i = 1; i <= 13; i++) {
            for (Suit suit : Suit.values()) {
                cards.add(new Card(i, suit));
            }
        }

        // Sum of all the card ranks, filtering only HEART
        int sum = cards.stream()
                .filter(card -> card.getSuit().equals(Suit.HEART))
                .mapToInt(Card::getRank)
                .sum();
        System.out.println(sum);
    }
}