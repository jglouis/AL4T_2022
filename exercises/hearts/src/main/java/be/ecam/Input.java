package be.ecam;
import java.util.Scanner;

public class Input {

    private final Scanner scanner;
    public Input() {
        this.scanner = new Scanner(System.in);
    }
    public String nextLine() {
        return scanner.nextLine();
    }

}
