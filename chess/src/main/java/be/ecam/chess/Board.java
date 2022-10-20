package be.ecam.chess;

import be.ecam.chess.piece.Piece;

/**
 * A 8x8 chess board. It acts like a simple container for {@link Piece}s.
 * One cell can only contain 0 or 1 {@link Piece}. It does not implement other chess rules.
 */
public class Board implements IBoard {
    private final Piece[][] board;

    public Board() {
        board = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }
    }

    @Override
    public void addPiece(Piece piece, int x, int y) throws CellIsNotEmptyException, OutOfBoundException {
        assertCellInbound(x, y);
        if (board[x][y] != null) {
            throw new CellIsNotEmptyException(x, y);
        }
        board[x][y] = piece;
    }

    @Override
    public Piece getPiece(int x, int y) throws OutOfBoundException {
        assertCellInbound(x, y);
        return board[x][y];
    }

    @Override
    public void move(int fromX, int fromY, int toX, int toY) throws CellIsEmptyException, CellIsNotEmptyException, OutOfBoundException {
        assertCellInbound(fromX, fromY);
        assertCellInbound(toX, toY);
        if (board[fromX][fromY] == null) {
            throw new CellIsEmptyException(fromX, fromY);
        } else if (board[toX][toY] != null) {
            throw new CellIsNotEmptyException(toX, toY);
        }
        board[toX][toY] = board[fromX][fromY];
        board[fromX][fromY] = null;
    }

    @Override
    public Piece remove(int x, int y) throws OutOfBoundException {
        assertCellInbound(x, y);
        Piece piece = board[x][y];
        board[x][y] = null;
        return piece;
    }

    @Override
    public void clear() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }
    }

    private void assertCellInbound(int x, int y) throws OutOfBoundException {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            throw new OutOfBoundException(x, y);
        }
    }

    @Override
    public String toEmoticon() {
        return str(true);
    }

    @Override
    public String toString() {
        return str(false);
    }

    private String str(boolean isUtf8) {
        StringBuilder sb = new StringBuilder();
        sb.append("  a b c d e f g h\n");
        for (int j = 7; j >= 0; j--) {
            sb.append(j + 1).append(" ");
            for (int i = 0; i < 8; i++) {
                if (board[i][j] == null) {
                    sb.append(".");
                } else {
                    String str;
                    if (isUtf8) {
                        str = board[i][j].toEmoticon();
                    } else {
                        str = board[i][j].toString();
                    }
                    sb.append(str);
                }
                if (i != 7) {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }
        sb.append("  a b c d e f g h\n");
        return sb.toString();
    }
}
