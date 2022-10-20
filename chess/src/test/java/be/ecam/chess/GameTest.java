package be.ecam.chess;

import be.ecam.chess.rule.TurnIterator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void start() {
        Game game = new Game(new MockBoard(), new MockTurnIterator());
        game.start();
        // We should probably verify that all pieces are in place.
    }

    @Test
    void start_IgnoredException() {
        // This test is only for getting coverage.
        Game game = new Game(new MockBoard(true), new MockTurnIterator());
        game.start();
    }

    @Test
    void move() throws IBoard.CellIsNotEmptyException, IBoard.OutOfBoundException {
        MockBoard board = new MockBoard();
        board.addPiece(new MockPiece(Color.WHITE), 0, 0);
        Game game = new Game(board, new TurnIterator());

        assertDoesNotThrow(() -> game.move(0, 0, 1, 1));
    }

    @Test
    void move_NoPiece() {
        MockBoard board = new MockBoard();
        Game game = new Game(board, new TurnIterator());

        assertThrows(RuntimeException.class, () -> game.move(0, 0, 1, 1));
    }

    @Test
    void move_Obstructed() throws IBoard.CellIsNotEmptyException, IBoard.OutOfBoundException {
        MockBoard board = new MockBoard();
        MockPiece piece = new MockPiece(Color.WHITE);
        piece.setIntermediatesSteps(new int[]{1, 1});
        board.addPiece(piece, 0, 0);
        board.addPiece(new MockPiece(Color.WHITE), 1, 1);
        Game game = new Game(board, new TurnIterator());

        assertThrows(RuntimeException.class, () -> game.move(0, 0, 2, 2));
    }

    @Test
    void move_Attack() throws IBoard.CellIsNotEmptyException, IBoard.OutOfBoundException {
        MockBoard board = new MockBoard();
        MockPiece piece = new MockPiece(Color.WHITE);
        board.addPiece(piece, 0, 0);
        board.addPiece(new MockPiece(Color.BLACK), 1, 1);
        Game game = new Game(board, new TurnIterator());

        assertDoesNotThrow(() -> game.move(0, 0, 1, 1));
        assertEquals(piece, board.getPiece(1, 1));
    }

    @Test
    void move_AttackFriendlyPiece() throws IBoard.CellIsNotEmptyException, IBoard.OutOfBoundException {
        MockBoard board = new MockBoard();
        board.addPiece(new MockPiece(Color.WHITE), 0, 0);
        MockPiece secondPiece = new MockPiece(Color.WHITE);
        board.addPiece(secondPiece, 1, 1);
        Game game = new Game(board, new TurnIterator());

        assertThrows(RuntimeException.class, () -> game.move(0, 0, 1, 1));
        assertEquals(secondPiece, board.getPiece(1, 1));
    }

    @Test
    void move_NoValidMoveIteratorFound() throws IBoard.CellIsNotEmptyException, IBoard.OutOfBoundException {
        MockBoard board = new MockBoard();
        board.addPiece(new MockPiece(Color.WHITE, true), 0, 0);
        Game game = new Game(board, new TurnIterator());

        assertThrows(RuntimeException.class, () -> game.move(0, 0, 1, 1));
    }
}