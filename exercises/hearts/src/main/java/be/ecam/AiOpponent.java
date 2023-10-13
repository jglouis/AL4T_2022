package be.ecam;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;



public class AiOpponent implements CardChooser {


    public int chooseCardToPlay(@NotNull Hand hand, Card[] cardsPlayed, @Nullable Suit suitToFollow) {
        hand.sort();

        if (suitToFollow == null) {
            return 0;
        }

        SuitChecker suitChecker = new SuitChecker(hand);
        int indexQueenOfSpade = suitChecker.getQueenOfSpadeIndex();
        int startSuit = suitChecker.getStartSuitIndex(suitToFollow);
        int endSuit = suitChecker.getEndSuitIndex(suitToFollow);

        if (startSuit != -1) {
            boolean isTherePointInThisTrick = PointChecker.isTherePoint(cardsPlayed);
            return isTherePointInThisTrick ? endSuit : startSuit;
        } else {
            return indexQueenOfSpade != -1 ? indexQueenOfSpade : hand.size() - 1;
        }
    }
}

class SuitChecker {

    private final Hand hand;

    public SuitChecker(Hand hand) {
        this.hand = hand;
    }

    public int getQueenOfSpadeIndex() {
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            if (Suit.SPADE.equals(card.getSuit()) && card.getValue() == 12) {
                return i;
            }
        }
        return -1;
    }

    public int getStartSuitIndex(Suit suit) {
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            if (suit.equals(card.getSuit())) {
                return i;
            }
        }
        return -1;
    }

    public int getEndSuitIndex(Suit suit) {
        int endSuit = -1;
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            if (suit.equals(card.getSuit())) {
                endSuit = i;
            }
        }
        return endSuit;
    }
}

class PointChecker {

    public static boolean isTherePoint(Card[] cardsPlayed) {
        for (Card cardPlayed : cardsPlayed) {
            if (cardPlayed == null) continue;
            if (Suit.SPADE.equals(cardPlayed.getSuit()) && cardPlayed.getValue() == 12) {
                return true;
            }
            if (Suit.HEART.equals(cardPlayed.getSuit())) {
                return true;
            }
        }
        return false;
    }
}

