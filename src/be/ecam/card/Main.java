package be.ecam.card;

public class Main {
    public static void main(String[] args) {
        Card card1 = new Card(3, Suit.CLUB);
        Card card2 = new Card(3, Suit.CLUB);

        Object dummyObject = new Object();

        System.out.println(card1.equals(dummyObject));
    }
}
