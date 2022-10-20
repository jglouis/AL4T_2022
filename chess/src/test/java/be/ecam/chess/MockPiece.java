package be.ecam.chess;

import be.ecam.chess.piece.Piece;
import be.ecam.chess.rule.MoveIterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Mock piece for testing.
 * Accepts any move.
 */
public class MockPiece extends Piece {
    private int[][] intermediatesSteps;
    private final boolean isMoveIteratorNull;

    public MockPiece(Color color) {
        super(color);
        this.isMoveIteratorNull = false;
    }

    public MockPiece(Color color, boolean isMoveIteratorNull) {
        super(color);
        this.isMoveIteratorNull = isMoveIteratorNull;
    }

    /**
     * Set intermediates mov steps for the {@link MoveIterator} returned by {@link #getMoveIterator(int, int, int, int)}.
     *
     * @param intermediatesSteps the intermediates steps between start and destination
     */
    public void setIntermediatesSteps(int[]... intermediatesSteps) {
        Arrays.stream(intermediatesSteps).filter(step -> step.length != 2).findAny().ifPresent(step -> {
            throw new IllegalArgumentException("Intermediates steps must be of length 2");
        });
        this.intermediatesSteps = intermediatesSteps;
    }

    @Override
    public MoveIterator getMoveIterator(int fromX, int fromY, int toX, int toY) {
        if (isMoveIteratorNull) { return null; }
        final int[][] steps;
        if (intermediatesSteps != null) {
            List<int[]> stepsList = new ArrayList<>(intermediatesSteps.length + 2);
            stepsList.add(new int[]{fromX, fromY});
            stepsList.addAll(List.of(intermediatesSteps));
            stepsList.add(new int[]{toX, toY});
            steps = stepsList.toArray(new int[stepsList.size()][]);
        } else {
            steps = new int[][]{{fromX, fromY}, {toX, toY}};
        }
        return new MoveIterator() {
            int currentStep = 0;

            @Override
            public int[] nextStep() {
                if (this.currentStep >= 1) return null;
                return steps[++this.currentStep];
            }
        };
    }

    @Override
    public String toEmoticon() {
        return "e";
    }

    @Override
    public String toString() {
        return "s";
    }
}
