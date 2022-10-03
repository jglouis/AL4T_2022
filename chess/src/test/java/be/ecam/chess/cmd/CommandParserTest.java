package be.ecam.chess.cmd;

import be.ecam.chess.IBoard;
import be.ecam.chess.MockBoard;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandParserTest {

    private static class TestPassedException extends RuntimeException {
    }

    @Test
    void start() {
        InputStream mockInputStream = new ByteArrayInputStream("mock 1 2 3\n".getBytes(StandardCharsets.UTF_8));
        IBoard mockBoard = new MockBoard();
        Command mockCommand = new Command(null) {
            @Override
            void execute(String... args) {
                assertArrayEquals(new String[]{"1", "2", "3"}, args);
                throw new TestPassedException();
            }
        };

        CommandParser commandParser = new CommandParser.Builder(mockInputStream, mockBoard, false)
                .addCommand("mock", mockCommand)
                .build();

        // This assertion insures that the execute method command is called since it is
        // the only way to throw the TestPassedException.
        assertThrows(TestPassedException.class, commandParser::start);
    }

    @Test
    void start_Utf8() {
        InputStream mockInputStream = new ByteArrayInputStream("mock 1 2 3\n".getBytes(StandardCharsets.UTF_8));
        IBoard mockBoard = new MockBoard();
        Command mockCommand = new Command(null) {
            @Override
            void execute(String... args) {
                assertArrayEquals(new String[]{"1", "2", "3"}, args);
                throw new TestPassedException();
            }
        };

        CommandParser commandParser = new CommandParser.Builder(mockInputStream, mockBoard, true)
                .addCommand("mock", mockCommand)
                .build();

        // This assertion insures that the execute method command is called since it is
        // the only way to throw the TestPassedException.
        assertThrows(TestPassedException.class, commandParser::start);
    }

    @Test
    void start_UnknownCommand() {
        InputStream mockInputStream = new ByteArrayInputStream("mock 1 2 3\n".getBytes(StandardCharsets.UTF_8));
        IBoard mockBoard = new MockBoard();

        CommandParser commandParser = new CommandParser.Builder(mockInputStream, mockBoard, false)
                .build();

        assertThrows(RuntimeException.class, commandParser::start);
    }
}