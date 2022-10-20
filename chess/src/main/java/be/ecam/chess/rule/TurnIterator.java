package be.ecam.chess.rule;

import be.ecam.chess.Color;

public class TurnIterator implements ITurnIterator {
    private int turnNumber = 1;
    private Color currentPlayer = Color.WHITE;

    @Override
    public void nextTurn() {
        turnNumber++;
        currentPlayer = currentPlayer == Color.WHITE ? Color.BLACK : Color.WHITE;
    }

    @Override
    public void reset() {
        turnNumber = 1;
        currentPlayer = Color.WHITE;
    }

    @Override
    public int getTurnNumber() {
        return turnNumber;
    }

    @Override
    public Color getCurrentPlayer() {
        return currentPlayer;
    }
}
