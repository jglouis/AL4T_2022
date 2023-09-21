package be.ecam;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Contains code for AI play.
 */
public class AiOpponent {

    /**
     * Given all relevant information, pick a card's index to be played.
     *
     * @param hand         The opponent's {@link Hand}] of card
     * @param cardsPlayed  The {@link Card}s already played
     * @param suitToFollow The {@link Suit} to follow
     * @return the {@link Hand} {@link Card}'s index to play
     */
    public static int chooseCardToPlay(
            @NotNull Hand hand, Card[] cardsPlayed, @Nullable Suit suitToFollow) {
        //Check if hand contains suit to Follow
        hand.sort(); //==> highest heart will be at the end

        if (suitToFollow == null) { //open with a low card
            return 0;
        }

        int indexQueenOfSpade = -1;
        int startSuit = -1;
        int endSuit = -1;

        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            if (Suit.SPADE.equals(card.getSuit()) && card.getValue() == 12) {
                indexQueenOfSpade = i;
            }
            if (suitToFollow.equals(card.getSuit())) {
                if (startSuit == -1) {
                    startSuit = i;
                }
                endSuit = i;
            }
        }

        if (startSuit != -1) {
            // Must follow suit
            // play highest from the suit if no points
            // play lowest otherwise
            boolean isTherePointInThisTrick = false;
            for (Card cardPlayed : cardsPlayed) {
                if (cardPlayed == null) continue;
                if (Suit.SPADE.equals(cardPlayed.getSuit()) && cardPlayed.getValue() == 12) {
                    isTherePointInThisTrick = true;
                    break;
                }
                if (Suit.HEART.equals(cardPlayed.getSuit())) {
                    isTherePointInThisTrick = true;
                    break;
                }
            }
            if (isTherePointInThisTrick) {
                return endSuit;
            } else {
                return startSuit;
            }
        } else {
            // Play Queen of spade -> card at the end
            if (indexQueenOfSpade != -1) {
                return indexQueenOfSpade;
            }
            return hand.size() - 1;
        }
    }
}
