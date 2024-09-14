package escaperoom.riddles;

import escaperoom.accompanyingclasses.User;
import escaperoom.enums.GameStatus;
import escaperoom.interfaces.UserInteraction;
import escaperoom.interfaces.TimeMeasurement;

import java.util.Random;
import java.util.Scanner;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Riddle5Mastermind implements UserInteraction, TimeMeasurement {

    // ***********************************************************
    // ********************* Attributes **************************
    // ***********************************************************
    
    /**
     * saves the current game status for evaluation at the end.
     */
    private GameStatus gameStatus;
    
    /**
     * ANSI escape sequence for red text.
     */
    final String ANSI_RED = "\u001B[91m";

    /**
     * ANSI escape sequence for blue text.
     */
    final String ANSI_BLUE = "\u001B[96m";

    /**
     * ANSI escape sequence for green text.
     */
    final String ANSI_GREEN = "\u001B[32m";

    /**
     * ANSI escape sequence for yellow text.
     */
    final String ANSI_YELLOW = "\u001B[93m";

    /**
     * ANSI escape sequence for black text.
     */
    final String ANSI_BLACK = "\u001B[30m";

    /**
     * ANSI escape sequence for white text.
     */
    final String ANSI_WHITE = "\u001B[97m";

    /**
     * ANSI escape sequence to reset the color display.
     */
    final String ANSI_RESET = "\u001B[0m";

    /**
     * An array containing the available colors for the color combination.
     * The colors are:
     * 'R' = Red,
     * 'B' = Blue,
     * 'G' = Green,
     * 'Y' = Yellow.
     */
    final char[] availableColors = {'R', 'B', 'G', 'Y'}; // Red, Blue, Green, Yellow

    /** List of all previous attempts and their results */
    private final List<String> attemptsHistory = new ArrayList<>();
    
    
    // ***********************************************************
    // ********************* Constructors ************************
    // ***********************************************************
    
    // Default constructor is sufficient

    
    
    // ***********************************************************
    // *********************** Methods ***************************
    // ***********************************************************

    /**
     * Starts the Mastermind riddle and tracks the time.
     * If the time limit of 180 seconds is exceeded, the riddle is aborted.
     * 
     * @param user The user object that saves time and score.
     */
    @SuppressWarnings("resource")
    public void startMastermindRiddle(User user) {
        separator("Mastermind: Guess the Color Combination");
        gameStatus = GameStatus.IN_PROGRESS; // Currently not used, but might be helpful for logging or debugging
        System.out.println("In this riddle, you must guess the correct color combination. There are 4 balls, which can be 4 colors: "
                + ANSI_RED + "● Red " + ANSI_RESET + ANSI_BLUE + "● Blue " + ANSI_RESET + ANSI_GREEN + "● Green " + ANSI_RESET
                + ANSI_YELLOW + "● Yellow" + ANSI_RESET + "\n"
                + "Colors can be repeated. You select 4 colors, and I will give feedback:\n"
                + ANSI_BLACK + "●" + ANSI_RESET + " A black pin for each correct color in the correct position\n"
                + ANSI_WHITE + "●" + ANSI_RESET + " A white pin for each correct color in the wrong position\n"
                + "(You get nothing for incorrect colors)\n"
                + "You have 10 attempts to find the correct combination using the feedback!");

        UserInteraction.pressEnterToContinue();
        LocalTime startTime = startTime(); // Start time for time measurement
        int failedAttempts; // Variable to count failed attempts
        long riddleTime = 0;

        // Generate the secret color combination
        char[] secretCode = generateSecretCode();
        System.out.println("Choose your colors! (Use abbreviations, e.g., RGBY)");

        Scanner scanner = new Scanner(System.in);
        boolean won = false;

        for (failedAttempts = 1; failedAttempts <= 10; failedAttempts++) {
            // Check time limit
            riddleTime = calculateDuration(startTime); // Calculate elapsed time
            if (riddleTime >= 180) {
                System.out.println(ANSI_RED + "\nTime limit exceeded! The riddle was aborted." + ANSI_RESET);
                System.out.println("The correct combination was: " + displayColors(secretCode));
                gameStatus = GameStatus.NOT_COMPLETED;
                failedAttempts = failedAttempts - 1;
                break;
            }

            System.out.println("\nAttempt " + failedAttempts + ": Enter your 4 colors ____");
            String input = scanner.nextLine().toUpperCase();

            // Check the length of the input
            if (input.length() != 4 || !isValidInput(input)) {
                System.out.println("Invalid input. Use only R, B, G, Y and exactly 4 characters.");
                failedAttempts--;
                continue;
            }

            char[] guess = input.toCharArray();
            int blackPins = 0;
            int whitePins = 0;

            // Check for black and white pins
            boolean[] guessedCorrectly = new boolean[4]; // Whether a position was guessed correctly
            boolean[] colorUsedInSecret = new boolean[4]; // Whether a color was used in the secret combination

            // First check for black pins (correct color and position)
            for (int i = 0; i < 4; i++) {
                if (guess[i] == secretCode[i]) {
                    blackPins++;
                    guessedCorrectly[i] = true;
                    colorUsedInSecret[i] = true;
                }
            }

            // Then check for white pins (correct color, wrong position)
            for (int i = 0; i < 4; i++) {
                if (!guessedCorrectly[i]) { // Only if the position wasn't already guessed correctly
                    for (int j = 0; j < 4; j++) {
                        if (guess[i] == secretCode[j] && !colorUsedInSecret[j]) {
                            whitePins++;
                            colorUsedInSecret[j] = true; // This color was used
                            break;
                        }
                    }
                }
            }

            // Feedback and color display for the current round
            StringBuilder currentAttempt = new StringBuilder();
            for (char c : guess) {
                switch (c) {
                    case 'R':
                        currentAttempt.append(ANSI_RED).append("● ").append(ANSI_RESET);
                        break;
                    case 'B':
                        currentAttempt.append(ANSI_BLUE).append("● ").append(ANSI_RESET);
                        break;
                    case 'G':
                        currentAttempt.append(ANSI_GREEN).append("● ").append(ANSI_RESET);
                        break;
                    case 'Y':
                        currentAttempt.append(ANSI_YELLOW).append("● ").append(ANSI_RESET);
                        break;
                }
            }
            currentAttempt.append("-> ").append(ANSI_BLACK).append("●".repeat(blackPins)).append(ANSI_WHITE).append("●".repeat(whitePins)).append(ANSI_RESET);
            
            // Save the result of the round
            attemptsHistory.add(currentAttempt.toString());

            // Display all previous attempts
            System.out.println("\nYour choice:");
            for (String attemptRecord : attemptsHistory) {
                System.out.println(attemptRecord);
            }

            // Won?
            if (blackPins == 4) {
                won = true;
                break;
            }
        }

        if (won) {
            System.out.println(ANSI_GREEN + "\nCongratulations! You found the correct color combination!" + ANSI_RESET);
            gameStatus = GameStatus.COMPLETED;
            riddleTime = calculateDuration(startTime); // Determine end time and calculate the time difference in seconds
            failedAttempts = failedAttempts - 1; // Since failed attempts start at 1 -> always 1 too many
        } else if (gameStatus != GameStatus.NOT_COMPLETED) { // If not aborted due to time limit
            System.out.println(ANSI_RED + "\nToo bad! You've used up all 10 attempts." + ANSI_RESET);
            System.out.println("The correct combination was: " + displayColors(secretCode));
            gameStatus = GameStatus.NOT_COMPLETED;
            failedAttempts = failedAttempts - 1; // Since failed attempts start at 1 -> always 1 too many
        }
        
        // Calculate score based on time and failed attempts and save it
        int scoreRiddle5 = 100 - (int) riddleTime; // Points based on time
        if (scoreRiddle5 < 0) scoreRiddle5 = 0; // Score cannot be below 0
        if (scoreRiddle5 > 100) scoreRiddle5 = 100; // Score cannot exceed 100
        if (gameStatus == GameStatus.NOT_COMPLETED) scoreRiddle5 = 0; // Account for status
        
        // Save game progress
        user.saveTimeMastermindRiddle(riddleTime);
        user.saveScoreMastermindRiddle(scoreRiddle5);
        user.setFailedAttemptsRiddle5Mastermind(failedAttempts);
        user.setGameStatusRiddle5(gameStatus);
        
        // Wait for Enter to continue
        UserInteraction.pressEnterToContinue();
    }

    /**
     * Generates the secret color combination.
     * @return An array of 4 randomly selected colors.
     */
    private char[] generateSecretCode() {
        Random random = new Random();
        char[] secretCode = new char[4];
        for (int i = 0; i < 4; i++) {
            secretCode[i] = availableColors[random.nextInt(availableColors.length)];
        }
        return secretCode;
    }

    /**
     * Checks whether the input is valid (only R, B, G, Y and exactly 4 characters).
     * @param input The user input to check.
     * @return true if the input is valid, otherwise false.
     */
    private boolean isValidInput(String input) {
        for (char character : input.toCharArray()) {
            if (character != 'R' && character != 'B' && character != 'G' && character != 'Y') {
                return false;
            }
        }
        return true;
    }

    /**
     * Displays the color combination with colored circles.
     * @param code The color combination to display.
     * @return The colored display of the color combination as a string.
     */
    private String displayColors(char[] code) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char character : code) {
            switch (character) {
                case 'R':
                    stringBuilder.append(ANSI_RED).append("● ").append(ANSI_RESET);
                    break;
                case 'B':
                    stringBuilder.append(ANSI_BLUE).append("● ").append(ANSI_RESET);
                    break;
                case 'G':
                    stringBuilder.append(ANSI_GREEN).append("● ").append(ANSI_RESET);
                    break;
                case 'Y':
                    stringBuilder.append(ANSI_YELLOW).append("● ").append(ANSI_RESET);
                    break;
            }
        }
        return stringBuilder.toString();
    }

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
