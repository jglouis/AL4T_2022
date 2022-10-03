package be.ecam.chess.cmd;

import be.ecam.chess.ExitException;
import be.ecam.chess.NoExitSecurityManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuitTest {

    @Test
    void execute() {
        Quit quit = new Quit();
        System.setSecurityManager(new NoExitSecurityManager());
        assertThrows(ExitException.class, quit::execute);
    }
}