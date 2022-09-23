package be.ecam.card;

public class Main {
    public static void main(String[] args) {
       Deck deck = new Deck();

       for (int i =  0; i < 53; i++) {
           Card card = null;
           try {
               card = deck.draw();
               System.out.println(card.toString());
           } catch  (NullPointerException e) {
               System.out.println("uh oh");
           }
       }
    }
}
