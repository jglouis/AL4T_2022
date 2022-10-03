package be.ecam.chess.piece;

import be.ecam.chess.Color;
import be.ecam.chess.rule.MoveIterator;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class RookTest {

    @Test
    void getMoveIterator() {
        Rook rook = new Rook(Color.WHITE);
        MoveIterator it = rook.getMoveIterator(2, 1, 4, 1);
        assertArrayEquals(new int[]{3, 1}, it.nextStep());
        assertArrayEquals(new int[]{4, 1}, it.nextStep());
        assertNull(it.nextStep());
    }

    @Test
    void getMoveIteratorInvalid() {
        Rook rook = new Rook(Color.WHITE);
        MoveIterator it = rook.getMoveIterator(2, 1, 4, 2);
        assertNull(it);
    }
}