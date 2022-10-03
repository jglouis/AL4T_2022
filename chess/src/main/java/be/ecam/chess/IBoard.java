package be.ecam.chess;

import be.ecam.chess.piece.Piece;

public interface IBoard {

    /**
     * Add a {@link Piece} to the board.
     *
     * @param piece the {@link Piece} to add
     * @param x     x coordinate
     * @param y     y coordinate
     * @throws CellIsNotEmptyException if the cell is not empty
     * @throws OutOfBoundException     if the cell is out of bound
     */
    void addPiece(Piece piece, int x, int y) throws CellIsNotEmptyException, OutOfBoundException;

    /**
     * Return the piece at given coordinates.
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return the Piece or null if cell is empty
     */
    Piece getPiece(int x, int y) throws OutOfBoundException;

    /**
     * Move a {@link Piece} from one cell to another.
     *
     * @param fromX origin x coordinate
     * @param fromY origin y coordinate
     * @param toX   destination x coordinate
     * @param toY   destination y coordinate
     * @throws CellIsEmptyException    if the origin cell is empty
     * @throws CellIsNotEmptyException if the destination cell is not empty
     * @throws OutOfBoundException     if either of the coordinate set is out of bound
     */
    void move(int fromX, int fromY, int toX, int toY) throws CellIsEmptyException, CellIsNotEmptyException, OutOfBoundException;

    /**
     * Remove {@link Piece} from the board.
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return the removed {@link Piece} or null if cell was empty
     * @throws OutOfBoundException if the cell is out of bound
     */
    Piece remove(int x, int y) throws OutOfBoundException;

    /**
     * Return an emoticon version of the board.
     * @return the emoticon version of the board
     */
    String toEmoticon();

    /**
     * Remove all pieces from the board.
     */
    void clear();

    class CellException extends Exception {
        protected final int x;
        protected final int y;

        public CellException(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    class CellIsEmptyException extends CellException {
        public CellIsEmptyException(int x, int y) {
            super(x, y);
        }

        @Override
        public String getMessage() {
            return String.format("Cell (%d, %d) is empty", x, y);
        }
    }

    class CellIsNotEmptyException extends CellException {
        public CellIsNotEmptyException(int x, int y) {
            super(x, y);
        }

        @Override
        public String getMessage() {
            return String.format("Cell (%d, %d) is not empty", x, y);
        }
    }

    class OutOfBoundException extends CellException {
        public OutOfBoundException(int x, int y) {
            super(x, y);
        }

        @Override
        public String getMessage() {
            return String.format("Cell (%d, %d) is out of bound", x, y);
        }

    }
}
