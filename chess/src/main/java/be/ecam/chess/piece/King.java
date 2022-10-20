package be.ecam.chess.piece;

import be.ecam.chess.Color;
import be.ecam.chess.rule.MoveIterator;

public class King extends Piece {
    public King(Color color) {
        super(color);
    }

    @Override
    public MoveIterator getMoveIterator(int fromX, int fromY, int toX, int toY) {
        if (Math.abs(fromX - toX) > 1) return null;
        if (Math.abs(fromY - toY) > 1) return null;
        return new MoveIterator() {
            int currentStep = 0;
            final int[][] steps = new int[][]{{fromX, fromY}, {toX, toY}};

            @Override
            public int[] nextStep() {
                if (this.currentStep >= 1) return null;
                return this.steps[++this.currentStep];
            }
        };
    }

    @Override
    public String toEmoticon() {
        return getColor() == Color.WHITE ? "♔" : "♚";
    }

    @Override
    public String toString() {
        return getColor() == Color.WHITE ? "K" : "k";
    }
}
