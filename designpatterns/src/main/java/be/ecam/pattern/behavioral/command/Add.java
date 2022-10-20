package be.ecam.pattern.behavioral.command;

/**
 * Command that adds two integers together and print the result.
 */
public class Add implements Command{
    @Override
    public void execute(String[] args) {
        if (args.length < 2) throw new IllegalArgumentException("not enough arguments");
        int arg1 = Integer.parseInt(args[0]);
        int arg2 = Integer.parseInt(args[1]);
        System.out.println(arg1 + arg2);
    }
}
