package be.ecam.chess;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardUtilsTest {

    @Test
    void humanChessCoordinatesToXY() {
        assertArrayEquals(new int[]{0, 0}, BoardUtils.humanChessCoordinatesToXY("a1"));
        assertArrayEquals(new int[]{7, 0}, BoardUtils.humanChessCoordinatesToXY("h1"));
        assertArrayEquals(new int[]{0, 7}, BoardUtils.humanChessCoordinatesToXY("a8"));
        assertArrayEquals(new int[]{7, 7}, BoardUtils.humanChessCoordinatesToXY("h8"));

        assertArrayEquals(new int[]{0, 0}, BoardUtils.humanChessCoordinatesToXY("A1"));
        assertArrayEquals(new int[]{7, 0}, BoardUtils.humanChessCoordinatesToXY("H1"));
        assertArrayEquals(new int[]{0, 7}, BoardUtils.humanChessCoordinatesToXY("A8"));
        assertArrayEquals(new int[]{7, 7}, BoardUtils.humanChessCoordinatesToXY("H8"));
    }

    @Test
    void humanChessCoordinatesToXY_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> BoardUtils.humanChessCoordinatesToXY("toto"));
    }

    @Test
    void humanChessCoordinatesToXY_NullPointerException() {
        //noinspection ConstantConditions
        assertThrows(NullPointerException.class, () -> BoardUtils.humanChessCoordinatesToXY(null));
    }
}