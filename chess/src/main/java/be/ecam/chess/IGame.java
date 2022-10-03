package be.ecam.chess;

public interface IGame {
    /**
     * Reset the game to its initial state.
     */
    void start();

    /**
     * Move a Piece from one cell to another.
     *
     * @param fromX the x coordinate of the source cell
     * @param fromY the y coordinate of the source cell
     * @param toX   the x coordinate of the destination cell
     * @param toY   the y coordinate of the destination cell
     * @throws Board.CellException if the move is not valid
     */
    void move(int fromX, int fromY, int toX, int toY) throws Board.CellException;
}
