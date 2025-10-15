import java.util.Scanner;
import java.util.Random;

public class GuessTheNumberGame {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        int totalScore = 0;
        int rounds = 3; // You can change this number

        System.out.println("🎯 Welcome to the Guess The Number Game!");
        System.out.println("You have " + rounds + " rounds to play.\n");

        for (int round = 1; round <= rounds; round++) {
            int randomNumber = rand.nextInt(100) + 1; // Random number between 1–100
            int attempts = 0;
            int maxAttempts = 7; // You can adjust difficulty here
            boolean guessedCorrectly = false;

            System.out.println("🔹 Round " + round + " — Guess a number between 1 and 100:");

            while (attempts < maxAttempts) {
                System.out.print("Enter your guess: ");
                int userGuess = sc.nextInt();
                attempts++;

                if (userGuess == randomNumber) {
                    System.out.println("🎉 Correct! You guessed the number in " + attempts + " attempts!");
                    guessedCorrectly = true;

                    // Points decrease with more attempts
                    int roundScore = (maxAttempts - attempts + 1) * 10;
                    totalScore += roundScore;
                    System.out.println("You earned " + roundScore + " points this round.\n");
                    break;
                } else if (userGuess < randomNumber) {
                    System.out.println("📉 Too low! Try again.");
                } else {
                    System.out.println("📈 Too high! Try again.");
                }
            }

            if (!guessedCorrectly) {
                System.out.println("❌ Out of attempts! The correct number was: " + randomNumber + "\n");
            }
        }

        System.out.println("🏁 Game Over!");
        System.out.println("Your total score: " + totalScore);
        System.out.println("Thanks for playing! 😎");

        sc.close();
    }
}
