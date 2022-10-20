package be.ecam.chess.piece;

import be.ecam.chess.Color;
import be.ecam.chess.rule.MoveIterator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PawnTest {

    @Test
    void getMOveIteratorWhite() {
        Piece whitePawn = new Pawn(Color.WHITE);
        MoveIterator it = whitePawn.getMoveIterator(0, 1, 0, 2);
        assertArrayEquals(new int[]{0, 2}, it.nextStep());
        assertNull(it.nextStep());
    }

    @Test
    void getMoveIteratorBlack() {
        Piece blackPawn = new Pawn(Color.BLACK);
        MoveIterator it = blackPawn.getMoveIterator(0, 2, 0, 1);
        assertArrayEquals(new int[]{0, 1}, it.nextStep());
        assertNull(it.nextStep());
    }

    @Test
    void getMoveIteratorWhite2() {
        Piece whitePawn = new Pawn(Color.WHITE);
        MoveIterator it = whitePawn.getMoveIterator(0, 1, 0, 3);
        assertArrayEquals(new int[]{0, 2}, it.nextStep());
        assertArrayEquals(new int[]{0, 3}, it.nextStep());
        assertNull(it.nextStep());
    }

    @Test
    void getMoveIteratorBlack2() {
        Piece blackPawn = new Pawn(Color.BLACK);
        MoveIterator it = blackPawn.getMoveIterator(0, 6, 0, 4);
        assertArrayEquals(new int[]{0, 5}, it.nextStep());
        assertArrayEquals(new int[]{0, 4}, it.nextStep());
        assertNull(it.nextStep());
    }

    @Test
    void getAggressiveMoveIteratorWhite() {
        Piece whitePawn = new Pawn(Color.WHITE);
        MoveIterator it = whitePawn.getAggressiveMoveIterator(0, 1, 1, 2);
        assertArrayEquals(new int[]{1, 2}, it.nextStep());
        assertNull(it.nextStep());
    }

    @Test
    void getAggressiveMoveIteratorBlack() {
        Piece blackPawn = new Pawn(Color.BLACK);
        MoveIterator it = blackPawn.getAggressiveMoveIterator(0, 6, 1, 5);
        assertArrayEquals(new int[]{1, 5}, it.nextStep());
        assertNull(it.nextStep());
    }

    @Test
    void getAggressiveMOveIteratorInvalid() {
        Piece pawn = new Pawn(Color.WHITE);
        MoveIterator it = pawn.getAggressiveMoveIterator(0, 1, 7, 0);
        assertNull(it);
    }

    @Test
    void getMoveIteratorInvalid() {
        Piece pawn = new Pawn(Color.WHITE);
        MoveIterator it = pawn.getMoveIterator(0, 1, 7, 0);
        assertNull(it);
    }

    @Test
    void getMoveIteratorInvalid2() {
        Piece pawn = new Pawn(Color.WHITE);
        MoveIterator it = pawn.getMoveIterator(0, 3, 0, 5);
        assertNull(it);
    }
}