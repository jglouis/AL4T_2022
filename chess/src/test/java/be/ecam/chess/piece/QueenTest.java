package be.ecam.chess.piece;

import be.ecam.chess.Color;
import be.ecam.chess.rule.MoveIterator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueenTest {

    @Test
    void getMoveIteratorOrthogonal() {
        Piece queen = new Queen(Color.WHITE);
        MoveIterator it = queen.getMoveIterator(2, 2, 5, 2);
        assertArrayEquals(new int[]{3, 2}, it.nextStep());
        assertArrayEquals(new int[]{4, 2}, it.nextStep());
        assertArrayEquals(new int[]{5, 2}, it.nextStep());
        assertNull(it.nextStep());
    }

    @Test
    void getMoveIteratorDiagonal() {
        Piece queen = new Queen(Color.WHITE);
        MoveIterator it = queen.getMoveIterator(2, 2, 5, 5);
        assertArrayEquals(new int[]{3, 3}, it.nextStep());
        assertArrayEquals(new int[]{4, 4}, it.nextStep());
        assertArrayEquals(new int[]{5, 5}, it.nextStep());
        assertNull(it.nextStep());
    }

    @Test
    void getMoveIteratorInvalid() {
        Piece queen = new Queen(Color.WHITE);
        MoveIterator it = queen.getMoveIterator(2, 2, 4, 5);
        assertNull(it);
    }
}