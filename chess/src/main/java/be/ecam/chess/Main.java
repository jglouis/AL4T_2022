package be.ecam.chess;

import be.ecam.chess.cmd.CommandParser;
import be.ecam.chess.cmd.Move;
import be.ecam.chess.cmd.Quit;
import be.ecam.chess.cmd.Start;
import be.ecam.chess.rule.TurnIterator;

import java.io.InputStream;

public class Main {

    public static void main(String[] args) {
        _main(System.in, args);
    }

    protected static void _main(InputStream in, String[] args) {
        final boolean isUtf8 = args.length >= 1 && args[0].equals("-utf8");

        Board board = new Board();
        Game game = new Game(board, new TurnIterator());
        game.start();

        CommandParser parser = new CommandParser.Builder(in, board, isUtf8)
                .addCommand("start", new Start(game))
                .addCommand("quit", new Quit())
                .addCommand("move", new Move(game))
                .build();

        parser.start();
    }
}
