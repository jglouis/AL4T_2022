package be.ecam.chess.piece;

import be.ecam.chess.Color;
import be.ecam.chess.rule.MoveIterator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BishopTest {

    @Test
    void getMoveIterator() {
        Piece bishop = new Bishop(Color.WHITE);
        MoveIterator it = bishop.getMoveIterator(3, 3, 7, 7);
        assertArrayEquals(new int[]{4, 4}, it.nextStep());
        assertArrayEquals(new int[]{5, 5}, it.nextStep());
        assertArrayEquals(new int[]{6, 6}, it.nextStep());
        assertArrayEquals(new int[]{7, 7}, it.nextStep());
        assertNull(it.nextStep());
    }

    @Test
    void getMoveIteratorInvalid() {
        Piece bishop = new Bishop(Color.BLACK);
        MoveIterator it = bishop.getMoveIterator(3, 3, 6, 7);
        assertNull(it);
    }
}