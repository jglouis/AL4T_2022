package be.ecam.chess.rule;

import be.ecam.chess.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnIteratorTest {

    @Test
    void nextTurn() {
        TurnIterator turnIterator = new TurnIterator();
        turnIterator.nextTurn();
        assertEquals(2, turnIterator.getTurnNumber());
        assertEquals(Color.BLACK, turnIterator.getCurrentPlayer());
        turnIterator.nextTurn();
        assertEquals(3, turnIterator.getTurnNumber());
        assertEquals(Color.WHITE, turnIterator.getCurrentPlayer());
    }

    @Test
    void reset() {
        TurnIterator turnIterator = new TurnIterator();
        turnIterator.nextTurn();
        turnIterator.reset();
        assertEquals(1, turnIterator.getTurnNumber());
        assertEquals(Color.WHITE, turnIterator.getCurrentPlayer());
    }

    @Test
    void getTurnNumber() {
        assertEquals(1, new TurnIterator().getTurnNumber());
    }

    @Test
    void getCurrentPlayer() {
        assertEquals(Color.WHITE, new TurnIterator().getCurrentPlayer());
    }
}