package be.ecam;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CardChooser {
    int chooseCardToPlay(@NotNull Hand hand, Card[] cardsPlayed, @Nullable Suit suitToFollow);
}
