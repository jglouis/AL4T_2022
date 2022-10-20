package be.ecam.pattern.behavioral.command;

public class SayHello implements Command{
    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            System.out.println("Hello " + args[0]);
        }
    }
}
