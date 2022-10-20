package be.ecam.chess.piece;

import be.ecam.chess.Color;
import be.ecam.chess.rule.MoveIterator;

public class Bishop extends Piece {
    public Bishop(Color color) {
        super(color);
    }

    @Override
    public MoveIterator getMoveIterator(int fromX, int fromY, int toX, int toY) {
        int deltaX = toX - fromX;
        int deltaY = toY - fromY;
        if (Math.abs(deltaX) != Math.abs(deltaY)) return null;
        return new MoveIterator() {
            private int currentX = fromX;
            private int currentY = fromY;
            private final int xIncr = deltaX > 0 ? 1 : -1;
            private final int yIncr = deltaY > 0 ? 1 : -1;

            @Override
            public int[] nextStep() {
                if (currentX == toX && currentY == toY) return null;
                currentX = currentX + xIncr;
                currentY = currentY + yIncr;
                return new int[]{currentX, currentY};
            }
        };
    }

    @Override
    public String toEmoticon() {
        return getColor() == Color.WHITE ? "♗" : "♝";
    }

    @Override
    public String toString() {
        return getColor() == Color.WHITE ? "B" : "b";
    }
}
