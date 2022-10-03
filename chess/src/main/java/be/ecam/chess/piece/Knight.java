package be.ecam.chess.piece;

import be.ecam.chess.Color;
import be.ecam.chess.rule.MoveIterator;

public class Knight extends Piece {
    public Knight(Color color) {
        super(color);
    }

    @Override
    public MoveIterator getMoveIterator(int fromX, int fromY, int toX, int toY) {

        if ((Math.abs(fromX - toX) == 2 && Math.abs(fromY - toY) == 1) ||
                (Math.abs(fromX - toX) == 1 && Math.abs(fromY - toY) == 2)) {

            return new MoveIterator() {
                int currentStep = 0;
                final int[][] steps = new int[][]{{fromX, fromY}, {toX, toY}};

                @Override
                public int[] nextStep() {
                    if (this.currentStep >= 1) return null;
                    return this.steps[++this.currentStep];
                }
            };
        } else return null;
    }

    @Override
    public String toEmoticon() {
        return getColor() == Color.WHITE ? "♘" : "♞";
    }

    @Override
    public String toString() {
        return getColor() == Color.WHITE ? "N" : "n";
    }
}
