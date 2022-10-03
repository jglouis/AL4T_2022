package be.ecam.chess.cmd;

public class Quit extends Command {
    public Quit() {
        super(null); // don't need the ref to game here
    }

    @Override
    void execute(String... args) {
        System.exit(0);
    }
}
