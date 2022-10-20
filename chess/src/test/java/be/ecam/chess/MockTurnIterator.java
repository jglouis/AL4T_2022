package be.ecam.chess;

import be.ecam.chess.rule.ITurnIterator;

/**
 * A place-holder dummy iterator. It stays on first turn, and it is always WHITE's turn.
 */
public class MockTurnIterator implements ITurnIterator {

    @Override
    public void nextTurn() {
        // Do nothing
    }

    @Override
    public void reset() {
        // Do nothing
    }

    @Override
    public int getTurnNumber() {
        return 1;
    }

    @Override
    public Color getCurrentPlayer() {
        return Color.WHITE;
    }
}
