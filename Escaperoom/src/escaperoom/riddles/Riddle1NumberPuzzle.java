package escaperoom.riddles;

import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Scanner;

import escaperoom.accompanyingclasses.User;
import escaperoom.enums.GameStatus;
import escaperoom.interfaces.UserInteraction;
import escaperoom.interfaces.TimeMeasurement;

/**
 * In this puzzle, the player has to continue a number sequence.
 */
public class Riddle1NumberPuzzle implements UserInteraction, TimeMeasurement {

    // ***********************************************************
    // ********************* Attributes **************************
    // ***********************************************************
    
    /**
     * Stores the current game status for evaluation at the end.
     */
    private GameStatus gameStatus;
    
    
    
    // ***********************************************************
    // ********************* Constructors ************************
    // ***********************************************************
    
    // Default constructor is sufficient

    
    
    
    // ***********************************************************
    // ************************ Methods **************************
    // ***********************************************************
    
    /**
     * Starts the number puzzle, where the player must continue a number sequence.
     * 
     * <p>The puzzle begins with a brief introduction and a countdown. The player is 
     * then asked to enter the next number in the series "1, 4, 9, 16, ___" (the correct answer 
     * is 25). The time taken to solve the puzzle and the number of failed attempts 
     * are recorded and stored in the {@code User} object.</p>
     * 
     * <p>Score calculation:
     * <ul>
     *   <li>The score starts at 100 and is reduced based on the time taken and the number of failed attempts.</li>
     *   <li>For each second over the base time and each failed attempt, points are deducted.</li>
     *   <li>The score is stored in the {@code User} object and can be a maximum of 100 and a minimum of 0.</li>
     *   <li>If there are more than 5 failed attempts, the score is set to 0.</li>
     * </ul>
     * </p>
     */
    @SuppressWarnings("resource")
    public void startNumberPuzzle(User user) throws InterruptedException {
        
        Scanner scanner = new Scanner(System.in);
        Thread.sleep(4000);
        separator("Let's start with the first puzzle");
        Thread.sleep(2000);
        final String ANSI_RED = "\u001B[31m"; // Red text
        final String ANSI_GREEN = "\u001B[32m"; // Green text
        final String ANSI_RESET = "\u001B[0m"; // End colored text
        
        System.out.println("You are in the elevator to the escape room. It requires a specific combination of numbers to start.\n"
                + "You must calculate this number! (Continue the number sequence)\n"
                + ANSI_RED + "(WARNING: Time taken is measured for each riddle. You have 3 minutes each.)" + ANSI_RESET);

        // Wait for Enter to continue
        UserInteraction.pressEnterToContinue();
        
        // Countdown
        System.out.println("Starting in:");
        for (int i = 3; i > 0; i--) {
            System.out.println(i + "...");
            Thread.sleep(1000); // 1 second pause
        }
        
        // Number sequence
        int input; // User input for the sought number in the sequence
        int failedAttempts = 0; // Variable to count failed attempts
        long puzzleTime = 0;
        LocalTime startTime = startTime(); // Start time for time measurement
        this.gameStatus = GameStatus.IN_PROGRESS; // Currently not used, but might be helpful for logging or debugging

        // Loop for repeated input if the answer is incorrect or the time limit is reached
        while (true) {
            puzzleTime = calculateDuration(startTime); // Check current time

            // Check if the time limit has been exceeded
            if (puzzleTime >= 180) {
                this.gameStatus = GameStatus.NOT_COMPLETED; // Set status to "NOT_COMPLETED"
                System.out.println(ANSI_RED + "\nTime limit exceeded! The puzzle has been aborted.\n" + ANSI_RESET);
                break; // End loop if the time limit is reached
            }

            System.out.println("\n>> Continue the sequence of numbers: 1, 4, 9, 16, ___");
            try {
                input = scanner.nextInt();
                scanner.nextLine(); // Clear buffer

                if (input == 25) {
                    puzzleTime = calculateDuration(startTime); // Determine end time and calculate the difference in seconds
                    this.gameStatus = GameStatus.COMPLETED; // Set status to "COMPLETED"
                    
                    if (puzzleTime < 30 && failedAttempts == 0) {
                        System.out.println(ANSI_GREEN + "\n25 is correct! Wow, your time of " + puzzleTime
                                + " seconds (with no failed attempts) is fantastic.\n" + ANSI_RESET +
                                "You enter the code into the elevator... It takes you to the desired floor.");
                    } else if (puzzleTime < 30 && failedAttempts > 0) {
                        System.out.println("\nThat was quick (" + puzzleTime + " seconds), but you had " + failedAttempts 
                                + " failed attempt(s).\nYou enter the code into the elevator... It takes you to the desired floor.");
                    } else {
                        System.out.println("\n25 is correct! But it took quite a long time (" + puzzleTime + " seconds).\n"
                                        + "You enter the code into the elevator... It takes you to the desired floor.");
                    }
                    break; // End loop if the correct answer is given
                } else {
                    failedAttempts++; // Count failed attempt
                    if (failedAttempts >= 5) {
                        this.gameStatus = GameStatus.NOT_COMPLETED; // Set status to "NOT_COMPLETED"
                        System.out.println(ANSI_RED + "\nOops, already 5 failed attempts. It seems the puzzle is too difficult for you.\n" + ANSI_RESET +
                                "Luckily, the elevator malfunctions and takes you to the desired floor.\n" +
                                "But you can't always rely on your luck!\n"  +
                                "(Hint: The sequence shown was a series of square numbers from 1 to 4)");
                        break; // End loop after 5 failed attempts
                    } else {
                        System.out.println(ANSI_RED + input + " is incorrect, try again." + ANSI_RESET);
                    }
                }
            } catch (InputMismatchException exception) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine(); // Reset scanner to capture the next input
            }
        }
        
        // Calculate and store score based on time and failed attempts
        int scorePuzzle1 = 100 + 15 - (int) puzzleTime - (failedAttempts * 5); // Score based on time and failed attempts + 15s grace period
        if (scorePuzzle1 < 0) scorePuzzle1 = 0; // Score cannot be below 0
        if (scorePuzzle1 > 100) scorePuzzle1 = 100; // Score cannot be above 100
        if (failedAttempts >= 5 || gameStatus == GameStatus.NOT_COMPLETED) scorePuzzle1 = 0;
        
        // Save game progress
        user.saveTimeNumberPuzzle(puzzleTime);
        user.saveScoreNumberPuzzle(scorePuzzle1);
        user.setFailedAttemptsRiddle1NumberPuzzle(failedAttempts);
        user.setGameStatusRiddle1(gameStatus);

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
     * Method with separator line and custom heading.
     */
    public static void separator(String heading) {
        System.out.println("\n" + "─".repeat(105));
        System.out.println("\t" + heading);
        System.out.println("─".repeat(105) + "\n");
    }
}
