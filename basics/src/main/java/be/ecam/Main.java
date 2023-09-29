package be.ecam;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to War game !\n- Type 'start' to start\n- Type 'rules' to view the rules\n- Type 'exit' to exit");

        Deck gameDeck = new Deck();
        int deckSize = 52;
        int playerScore = 0;
        int computerScore = 0;

        while(true) {
            String entry = scanner.next();

            if(entry.equals("start")) {
                if (deckSize == 0) {
                    if (playerScore>computerScore) {
                        System.out.println("The deck is empty, you win!");
                        break;
                    } else if (playerScore<computerScore) {
                        System.out.println("The deck is empty, the computer wins");
                        break;
                    } else {
                        System.out.println("The deck is empty, there's no winner");
                        break;
                    }
                }
                deckSize -= 2;
                Card myCard = gameDeck.draw();
                Card computerCard = gameDeck.draw();

                int myRank = myCard.rank;
                Suit mySuit = myCard.suit;

                int dealerRank = computerCard.rank;
                Suit dealerSuit = computerCard.suit;

                System.out.printf("Your card is : %s of %s %n", myRank, mySuit);
                System.out.printf("The dealer's card is : %s of %s %n", dealerRank, dealerSuit);

                if(myRank > dealerRank){
                    playerScore++;
                    System.out.printf("You win! %n");
                    System.out.printf("Your score is %s, computer's score is %s %n", playerScore, computerScore);
                }
                if(myRank < dealerRank){
                    computerScore++;
                    System.out.printf("You lose! %n");
                    System.out.printf("Your score is %s, computer's score is %s %n", playerScore, computerScore);
                }
                if(myRank == dealerRank){
                    System.out.printf("Equality! %n");
                    System.out.printf("Your score is %s, computer's score is %s %n", playerScore, computerScore);
                }
                System.out.println("\n- Type 'start' to draw another card\n- Type 'exit' to exit");

            }else if(entry.equals("rules")) {
                System.out.println("Both players draw a card from the deck.\nThe player with the highest card rank wins the round.\nIf both players have the same rank, the round ends in an equality.\n");
                System.out.println("- Type 'start' to draw a card\n- Type 'exit' to exit");
            }
            else if(entry.equals("exit")) {
                System.out.println("Goodbye!");
                break;
            }
            else {
                System.out.println("This is not a good entry. Please use 'start', 'rules' or 'exit'.");
            }

        }
    }
}
