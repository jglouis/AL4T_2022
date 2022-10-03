package be.ecam.chess.piece;

import be.ecam.chess.Color;
import be.ecam.chess.rule.MoveIterator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KingTest {

    @Test
    void getMoveIterator() {
        Piece king = new King(Color.WHITE);
        MoveIterator it = king.getMoveIterator(0, 0, 0, 1);
        assertArrayEquals(new int[]{0, 1}, it.nextStep());
        assertNull(it.nextStep());
    }

    @Test
    void getMoveIteratorInvalid() {
        Piece king = new King(Color.BLACK);
        MoveIterator it = king.getMoveIterator(0, 0, 0, 7);
        assertNull(it);
    }
}