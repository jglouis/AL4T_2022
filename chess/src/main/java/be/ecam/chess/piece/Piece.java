package be.ecam.chess.piece;

import be.ecam.chess.Color;
import be.ecam.chess.rule.MoveIterator;

public abstract class Piece {

    private final Color color;

    public Piece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    /**
     * Returns a {@link MoveIterator} for the given origin and destination.
     *
     * @param fromX x origin coordinate
     * @param fromY y origin coordinate
     * @param toX   x destination coordinate
     * @param toY   y destination coordinate
     * @return a {@link MoveIterator} or null if move is impossible
     */
    public abstract MoveIterator getMoveIterator(int fromX, int fromY, int toX, int toY);

    /**
     * Returns a UTF-8 {@link String} representation of the piece.
     *
     * @return a UTF-8 {@link String} representation of the piece
     */
    public abstract String toEmoticon();

    /**
     * Returns a {@link MoveIterator} for the given origin and destination. The move corresponds to a move for taking an
     * adverse piece. This method exist because of pawns distinct aggressive move. Other pieces have this method
     * implemented in the {@link #getMoveIterator} class.
     *
     * @param fromX x origin coordinate
     * @param fromY y origin coordinate
     * @param toX   x destination coordinate
     * @param toY   y destination coordinate
     * @return a {@link MoveIterator} or null if move is impossible
     */
    public MoveIterator getAggressiveMoveIterator(int fromX, int fromY, int toX, int toY) {
        return getMoveIterator(fromX, fromY, toX, toY);
    }
}
