package escaperoom.riddles;

import java.time.LocalTime;
import java.util.Random;
import java.util.Scanner;

import escaperoom.accompanyingclasses.User;
import escaperoom.enums.GameStatus;
import escaperoom.interfaces.UserInteraction;
import escaperoom.interfaces.TimeMeasurement;

/**
 * In this riddle, the user must guess a random number.
 */
public class Riddle3GuessNumber implements UserInteraction, TimeMeasurement {

    // ***********************************************************
    // ********************* Attributes **************************
    // ***********************************************************
    
    /**
     * saves the current game status for evaluation at the end.
     */
    private GameStatus gameStatus;

    
    
    // ***********************************************************
    // ********************* Constructors ************************
    // ***********************************************************
    
    // Default constructor is sufficient

    
    
    
    // ***********************************************************
    // *********************** Methods ***************************
    // ***********************************************************
    
    /**
     * Starts a number riddle where the user must guess a number between 1 and 1000.
     * 
     * <p>The user repeatedly makes guesses until the correct number is guessed. After each guess,
     * the user is told whether the number to guess is higher or lower. The number of failed attempts
     * and the time taken are recorded and saved in the {@code User} object.</p>
     * 
     * <p>Score Calculation:
     * <ul>
     *   <li>The score starts at 100 and is reduced based on the time taken and the number of failed attempts.</li>
     *   <li>For each second spent solving the riddle and each failed attempt, a point is deducted.</li>
     *   <li>The score cannot fall below 0 and is capped at a maximum of 100.</li>
     * </ul>
     * </p>
     * 
     * <p>Game Flow:
     * <ul>
     *   <li>The user must guess a number between 1 and 1000.</li>
     *   <li>If the guess is wrong, the user is told whether the number to guess is higher or lower.</li>
     *   <li>The game continues until the correct number is guessed, or the number of failed attempts reaches 50, or the time limit is exceeded.</li>
     *   <li>The number to guess will never be 500, to improve the player experience.</li>
     * </ul>
     * </p>
     * 
     * <p>Abort Conditions:
     * <ul>
     *   <li>If the user reaches 50 failed attempts, the riddle is aborted, and the status is set to "NOT_COMPLETED".</li>
     *   <li>If the user takes longer than 180 seconds, the riddle is also aborted, and the status is set to "NOT_COMPLETED".</li>
     * </ul>
     * </p>
     * 
     * @param user The user object that saves time and score.
     */
    @SuppressWarnings("resource")
    public void startGuessNumber(User user) {
        separator("Here's another number riddle");
        System.out.println("There's a combination lock on the cabinet. You need to guess the combination!\nThe number is between 1-1000. "
                + "Enter your guess, and I'll tell you if it's higher or lower.\n");
        
        this.gameStatus = GameStatus.IN_PROGRESS; // Currently not used, but might be helpful for logging or debugging
        UserInteraction.pressEnterToContinue();
        
        LocalTime startTime = startTime(); // Start time for time measurement
        System.out.println("Enter a guess ___");
        
        final String ANSI_RED = "\u001B[31m"; // Red text
        final String ANSI_GREEN = "\u001B[32m"; // Green text
        final String ANSI_RESET = "\u001B[0m"; // End colored text
        
        Scanner scanner = new Scanner(System.in);
        int input = 0; // User input
        int failedAttempts = 0; // Variable to count failed attempts
        Random random = new Random(); // Random generator to create the target number
        int targetNumber;
        
        // Generate a number between 1 and 1000, but not 500
        do {
            targetNumber = random.nextInt(1000) + 1;
        } while (targetNumber == 500); // If the number is 500, generate a new one (because 500 is often guessed first, which could lead to a very high score only by luck)

        // Loop until the user guesses the correct number, exceeds 25 failed attempts, or the 180-second time limit is reached
        while (input != targetNumber) {
            // Abort if 25 failed attempts
            if (failedAttempts >= 25) {
                System.out.println(ANSI_RED + "\nToo many failed attempts! The riddle has been aborted." + ANSI_RESET);
                gameStatus = GameStatus.NOT_COMPLETED; // Set status to "NOT_COMPLETED"
                break; // End loop
            }
            
            // Abort if the time limit of 180 seconds (3 minutes) is exceeded
            long riddleTime = calculateDuration(startTime); // Check current time
            if (riddleTime >= 180) { // If the time limit is exceeded
                System.out.println(ANSI_RED + "\nTime limit reached! The riddle has been aborted." + ANSI_RESET);
                gameStatus = GameStatus.NOT_COMPLETED; // Set status to "NOT_COMPLETED"
                break; // End loop
            }
            
            input = scanner.nextInt();
            if (input == targetNumber) {
                // The player has guessed the correct number
                System.out.println(ANSI_GREEN + "Correct! Good job! The cabinet is now open\n" + ANSI_RESET);
                gameStatus = GameStatus.COMPLETED; // Set status to "COMPLETED"
            } else if (input > targetNumber) {
                System.out.println("\nLower! Enter a new guess ___\n");
                failedAttempts++; // Increment failed attempts
            } else if (input < targetNumber) {
                System.out.println("\nHigher! Enter a new guess ___\n");
                failedAttempts++; // Increment failed attempts
            }
        }

        long riddleTime = calculateDuration(startTime); // Determine the end time and calculate the time difference in seconds
        
        // Calculate score based on time and failed attempts
        int scoreRiddle3 = 100 - (int) riddleTime - failedAttempts; // Points based on time and failed attempts
        if (scoreRiddle3 < 0) scoreRiddle3 = 0; // Score cannot be less than 0
        if (scoreRiddle3 > 100) scoreRiddle3 = 100; // Score cannot exceed 100

        // Save game progress
        user.saveScoreGuessNumberRiddle(scoreRiddle3);
        user.saveTimeGuessNumberRiddle(riddleTime);
        user.setFailedAttemptsRiddle3GuessNumber(failedAttempts);
        user.setGameStatusRiddle3(gameStatus);

        // Wait for Enter to continue
        UserInteraction.pressEnterToContinue();
    }
    
    
    
    // ***********************************************************
    // ******************* Getters & Setters *********************
    // ***********************************************************
    
    
    
    
    // ***********************************************************
    // ************************* Other ***************************
    // ***********************************************************
    
    /**
     * Method with a separator line and custom heading.
     */
    public static void separator(String heading) {
        System.out.println("\n" + "─".repeat(105));
        System.out.println("\t" + heading);
        System.out.println("─".repeat(105) + "\n");
    }
}
