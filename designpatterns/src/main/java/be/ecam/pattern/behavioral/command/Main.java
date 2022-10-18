package be.ecam.pattern.behavioral.command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Command COMMAND_EXIT = new Exit();
    private static final Command COMMAND_HELLO = new SayHello();
    private static final Command COMMAND_ADD = new Add();

    private static final Map<String, Command> COMMAND_MAP = new HashMap<>();

    static {
        COMMAND_MAP.put("exit", COMMAND_EXIT);
        COMMAND_MAP.put("hello", COMMAND_HELLO);
        COMMAND_MAP.put("add", COMMAND_ADD);
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String line = scanner.nextLine();
                String[] split = line.split(" ");
                if (split.length > 0) {
                    String commandName = split[0];
                    if (!COMMAND_MAP.containsKey(commandName)) {
                        throw new RuntimeException("Unknown Command " + commandName);
                    }
                    COMMAND_MAP.get(commandName).execute(Arrays.copyOfRange(split, 1, split.length));
                }
            }
        }
    }
}
