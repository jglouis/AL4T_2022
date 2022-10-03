package be.ecam.chess.rule;

import be.ecam.chess.Color;

public interface ITurnIterator {
    void nextTurn();

    void reset();

    int getTurnNumber();

    Color getCurrentPlayer();
}
