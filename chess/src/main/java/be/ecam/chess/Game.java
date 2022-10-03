package be.ecam.chess;

import be.ecam.chess.piece.*;
import be.ecam.chess.rule.ITurnIterator;
import be.ecam.chess.rule.MoveIterator;

/**
 * the {@link Game} class is responsible for enforcing games rules like
 * - Game Setup
 * - {@link Piece} movement
 * - player turn alternance.
 */
public class Game implements IGame {
    private final IBoard board;
    private final ITurnIterator turnIterator;

    public Game(IBoard board, ITurnIterator turnIterator) {
        this.board = board;
        this.turnIterator = turnIterator;
    }

    @Override
    public void start() {
        turnIterator.reset();
        board.clear();
        try {
            board.addPiece(new King(Color.WHITE), 4, 0);
            board.addPiece(new King(Color.BLACK), 4, 7);
            board.addPiece(new Queen(Color.WHITE), 3, 0);
            board.addPiece(new Queen(Color.BLACK), 3, 7);
            board.addPiece(new Rook(Color.WHITE), 0, 0);
            board.addPiece(new Rook(Color.BLACK), 0, 7);
            board.addPiece(new Rook(Color.WHITE), 7, 0);
            board.addPiece(new Rook(Color.BLACK), 7, 7);
            board.addPiece(new Bishop(Color.WHITE), 2, 0);
            board.addPiece(new Bishop(Color.BLACK), 2, 7);
            board.addPiece(new Bishop(Color.WHITE), 5, 0);
            board.addPiece(new Bishop(Color.BLACK), 5, 7);
            board.addPiece(new Knight(Color.WHITE), 1, 0);
            board.addPiece(new Knight(Color.BLACK), 1, 7);
            board.addPiece(new Knight(Color.WHITE), 6, 0);
            board.addPiece(new Knight(Color.BLACK), 6, 7);
            for (int i = 0; i < 8; i++) {
                board.addPiece(new Pawn(Color.WHITE), i, 1);
                board.addPiece(new Pawn(Color.BLACK), i, 6);
            }
        } catch (Board.CellException ignored) {
        }
    }

    @Override
    public void move(int fromX, int fromY, int toX, int toY) throws Board.CellException {
        Piece piece = board.getPiece(fromX, fromY);
        if (piece == null) {
            throw new RuntimeException("Invalid move: No piece at (" + fromX + ", " + fromY + ").");
        }
        if (!turnIterator.getCurrentPlayer().equals(piece.getColor())) {
            return;
        }
        // Is there an enemy piece at destination?
        final boolean isAggressiveMove = board.getPiece(toX, toY) != null && !board.getPiece(toX, toY).getColor().equals(piece.getColor());
        final MoveIterator moveIterator;
        if (isAggressiveMove) {
            moveIterator = piece.getAggressiveMoveIterator(fromX, fromY, toX, toY);
        } else {
            moveIterator = piece.getMoveIterator(fromX, fromY, toX, toY);
        }
        if (moveIterator != null) {
            // Check for each step if the move is valid (no piece in-between)
            while (true) {
                int[] step = moveIterator.nextStep();
                if (step == null) break;
                Piece pieceAtStep = board.getPiece(step[0], step[1]);
                if (pieceAtStep != null) {
                    boolean wasItAnEnemy = false;
                    if (step[0] == toX && step[1] == toY) {
                        // remove enemy piece, if any
                        wasItAnEnemy = pieceAtStep.getColor() != piece.getColor();
                        if (wasItAnEnemy) {
                            board.remove(step[0], step[1]);
                        }
                    }
                    if (!wasItAnEnemy) {
                        String errorMsg =
                                String.format(
                                        "Invalid move: %s piece can't move to (%d,%d). There is a %s piece there.",
                                        piece.getColor(), step[0], step[1], pieceAtStep.getColor());
                        throw new RuntimeException(errorMsg);
                    }
                }
            }
            board.move(fromX, fromY, toX, toY);
            turnIterator.nextTurn();
        } else throw new RuntimeException("Illegal move. No valid move iterator found.");
    }
}
