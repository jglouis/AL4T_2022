package be.ecam.pattern.behavioral.command;

public interface Command {
    /**
     * Execute command.
     *
     * @param args the user input
     */
    void execute(String[] args);
}
