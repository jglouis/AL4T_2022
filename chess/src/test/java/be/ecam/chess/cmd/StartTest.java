package be.ecam.chess.cmd;

import be.ecam.chess.IGame;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertTrue;

class StartTest {

    @Test
    void execute() {
        final AtomicBoolean hasStartBeenCalled = new AtomicBoolean(false);
        IGame mockGame = new IGame() {
            @Override
            public void start() {
                hasStartBeenCalled.set(true);
            }

            @Override
            public void move(int fromX, int fromY, int toX, int toY) {

            }
        };
        Start start = new Start(mockGame);
        start.execute();
        assertTrue(hasStartBeenCalled.get());
    }
}