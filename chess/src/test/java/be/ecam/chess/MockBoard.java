package be.ecam.chess;

import be.ecam.chess.piece.Piece;

import java.util.HashMap;

/**
 * A mock of the board that throws configurable exception
 */
public class MockBoard implements IBoard {

    private static class Coordinate {
        private final int x;
        private final int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Coordinate that = (Coordinate) o;

            if (x != that.x) return false;
            return y == that.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }

    private final boolean shouldThrowOutOfBoundException;
    private final HashMap<Coordinate, Piece> pieces = new HashMap<>();

    /**
     * Construct a {@link MockBoard} that does not throw any {@link be.ecam.chess.IBoard.OutOfBoundException}.
     */
    public MockBoard() {
        shouldThrowOutOfBoundException = false;
    }

    /**
     * Construct a customizable mock of a Board that will return the configured exceptions.
     *
     * @param shouldThrowOutOfBoundException <code>true</code> if methods should throw a {@link OutOfBoundException}
     */
    public MockBoard(boolean shouldThrowOutOfBoundException) {
        this.shouldThrowOutOfBoundException = shouldThrowOutOfBoundException;
    }

    private void throwOutOfBound(int x, int y) throws OutOfBoundException {
        if (shouldThrowOutOfBoundException) throw new OutOfBoundException(x, y);
    }

    @Override
    public void addPiece(Piece piece, int x, int y) throws CellIsNotEmptyException, OutOfBoundException {
        throwOutOfBound(x, y);
        if (pieces.get(new Coordinate(x, y)) != null) {
            throw new CellIsNotEmptyException(x, y);
        }
        pieces.put(new Coordinate(x, y), piece);
    }

    @Override
    public Piece getPiece(int x, int y) throws OutOfBoundException {
        throwOutOfBound(x, y);
        return pieces.get(new Coordinate(x, y));
    }

    @Override
    public void move(int fromX, int fromY, int toX, int toY) throws CellIsEmptyException, CellIsNotEmptyException, OutOfBoundException {
        throwOutOfBound(fromX, fromY);
        throwOutOfBound(toX, toY);
        Piece removedPiece = pieces.remove(new Coordinate(fromX, fromY));
        if (removedPiece == null) {
            throw new CellIsEmptyException(fromX, fromY);
        }
        if (pieces.get(new Coordinate(toX, toY)) != null) {
            throw new CellIsNotEmptyException(toX, toY);
        }
        pieces.put(new Coordinate(toX, toY), removedPiece);
    }

    @Override
    public Piece remove(int x, int y) throws OutOfBoundException {
        throwOutOfBound(x, y);
        return pieces.remove(new Coordinate(x, y));
    }

    @Override
    public String toEmoticon() {
        return null;
    }

    @Override
    public void clear() {
        pieces.clear();
    }
}
