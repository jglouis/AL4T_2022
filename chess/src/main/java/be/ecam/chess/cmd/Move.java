package be.ecam.chess.cmd;

import be.ecam.chess.BoardUtils;
import be.ecam.chess.IGame;

public class Move extends Command {
    public Move(IGame game) {
        super(game);
    }

    @Override
    public void execute(String... args) {
        if (args.length != 2) {
            System.out.println("Usage: move <from> <to>");
            return;
        }
        int[] from = BoardUtils.humanChessCoordinatesToXY(args[0]);
        int[] to = BoardUtils.humanChessCoordinatesToXY(args[1]);
        int fromX = from[0];
        int fromY = from[1];
        int toX = to[0];
        int toY = to[1];
        try {
            game.move(fromX, fromY, toX, toY);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
