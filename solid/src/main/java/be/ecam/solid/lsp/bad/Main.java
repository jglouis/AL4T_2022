package be.ecam.solid.lsp.bad;

public class Main {
    public static void main(String[] args) {
        Rectangle r = new Rectangle(2, 3);
        Square s = new Square(4);

        r.setHeight(1); // makes sense
        s.setHeight(1); // makes no sense for a square!!!
    }
}
