package be.ecam.chess.cmd;

import be.ecam.chess.IGame;

public abstract class Command {
    protected final IGame game;

    protected Command(IGame game) {
        this.game = game;
    }

    abstract void execute(String... args);
}
