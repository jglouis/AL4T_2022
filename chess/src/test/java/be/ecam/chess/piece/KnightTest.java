package be.ecam.chess.piece;

import be.ecam.chess.Color;
import be.ecam.chess.rule.MoveIterator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KnightTest {

    @Test
    void getMoveIterator() {
        Knight knight = new Knight(Color.WHITE);
        MoveIterator it = knight.getMoveIterator(1, 0, 2, 2);
        assertArrayEquals(new int[]{2, 2}, it.nextStep());
        assertNull(it.nextStep());
    }

    @Test
    void getMoveIteratorInvalid() {
        Knight knight = new Knight(Color.WHITE);
        MoveIterator it = knight.getMoveIterator(1, 0, 1, 2);
        assertNull(it);
    }
}