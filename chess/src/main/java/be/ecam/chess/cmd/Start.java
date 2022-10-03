package be.ecam.chess.cmd;

import be.ecam.chess.IGame;

public class Start extends Command {
    public Start(IGame game) {
        super(game);
    }

    @Override
    public void execute(String... args) {
        game.start();
    }
}
