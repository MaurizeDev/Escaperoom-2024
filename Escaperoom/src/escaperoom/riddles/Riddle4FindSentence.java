package escaperoom.riddles;

import java.time.LocalTime;
import java.util.Random;
import java.util.Scanner;

import escaperoom.accompanyingclasses.User;
import escaperoom.enums.GameStatus;
import escaperoom.interfaces.UserInteraction;
import escaperoom.interfaces.TimeMeasurement;

/**
 * In this riddle, the user must find 3 words in a letter matrix and form a meaningful sentence from them.
 */
public class Riddle4FindSentence implements UserInteraction, TimeMeasurement {

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
     * Starts the word search riddle where the user must find hidden words in a matrix
     * and form a meaningful sentence from them.
     */
    @SuppressWarnings("resource")
    public void startWordSearchRiddle(User user) {
        separator("Here you must find 3 words in the letter matrix");
        
        char[][] matrix = new char[15][15]; // 15x15 matrix
        Random random = new Random(); // to generate random letters for the matrix
        this.gameStatus = GameStatus.IN_PROGRESS; // Currently not used, but might be helpful for logging or debugging
        String[] words = {"PROGRAMMING", "IS", "FUN"}; // The three words hidden in the matrix

        // Fill the matrix with random uppercase letters
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                matrix[i][j] = (char) (random.nextInt(26) + 'A'); // Random uppercase letters from 'A' to 'Z'
            }
        }

        // Insert words into the matrix
        hideWordInMatrix(matrix, words[0], 5, 2, "horizontal");  // "PROGRAMMING" horizontally
        hideWordInMatrix(matrix, words[1], 9, 7, "vertical");   // "IS" vertically
        hideWordInMatrix(matrix, words[2], 9, 5, "diagonal");    // "FUN" diagonally

        // Display the matrix
        System.out.println("On the mad scientist's computer, a matrix of letters is displayed, with words hidden inside.\n"
                + "Find the hidden words and form a meaningful sentence from them!\nThe words can be read horizontally, vertically, diagonally, or backwards:\n");
        UserInteraction.pressEnterToContinue();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        // Initialize variables
        LocalTime startTime = startTime(); // Start time for time measurement
        Scanner scanner = new Scanner(System.in);
        String input = "";
        boolean correct = false; // Boolean becomes true if the riddle is solved
        int failedAttempts = 0; // Count failed attempts
        long riddleTime = 0;
        final String ANSI_RED = "\u001B[31m"; // Red text
        final String ANSI_GREEN = "\u001B[32m"; // Green text
        final String ANSI_RESET = "\u001B[0m"; // End of colored text

        // Loop until the correct input is made or the time limit is reached
        while (!correct) {
            riddleTime = calculateDuration(startTime); // Check current time

            if (riddleTime >= 180) { // If time exceeds 180 seconds, abort
                gameStatus = GameStatus.NOT_COMPLETED;
                System.out.println(ANSI_RED + "\nTime limit reached! The riddle was aborted." + ANSI_RESET);
                break; // End riddle and exit loop
            }
            
            // Check if the failed attempt limit is reached
            if (failedAttempts >= 10) {
                gameStatus = GameStatus.NOT_COMPLETED;
                System.out.println(ANSI_RED + "\nToo many failed attempts! The riddle was aborted." + ANSI_RESET);
                break; // End riddle and exit loop
            }
            
            // Ask the user for the sentence
            System.out.println("\nType the sentence here ___");
            input = scanner.nextLine().trim().toLowerCase();

            // Check if the sentence is correct. 2 versions make sense, with both ß and ss
            if (input.equals("programming is fun") || input.equals("fun is programming")) {
                correct = true;
            } else {
                failedAttempts++; // Count failed attempt
                System.out.println(ANSI_RED + "That is not the correct sentence. Try again." + ANSI_RESET);
            }
        }

        if (correct) {
            gameStatus = GameStatus.COMPLETED;
            System.out.println(ANSI_GREEN + "\nThat is correct! Congratulations\n" + ANSI_RESET);
            riddleTime = calculateDuration(startTime); // Determine end time and calculate the time difference in seconds
        }

        // Calculate score based on time and failed attempts and save it
        int scoreRiddle4 = 100 - (int) riddleTime - (failedAttempts * 5); // Points based on time and failed attempts
        if (scoreRiddle4 < 0) scoreRiddle4 = 0; // Score cannot be below 0
        if (scoreRiddle4 > 100) scoreRiddle4 = 100; // Score cannot exceed 100
        if (gameStatus == GameStatus.NOT_COMPLETED) scoreRiddle4 = 0; // Account for status
        
        // Save game progress
        user.saveTimeFindSentenceRiddle(riddleTime);
        user.setGameStatusRiddle4(gameStatus); // save game status in the user object
        user.saveScoreFindSentenceRiddle(scoreRiddle4);
        user.setFailedAttemptsRiddle4FindSentence(failedAttempts);

        // Wait for Enter to continue
        UserInteraction.pressEnterToContinue();
    }

    /**
     *  Method to hide words in the matrix
     * @param matrix
     * @param word
     * @param startRow
     * @param startCol
     * @param direction
     */
    private void hideWordInMatrix(char[][] matrix, String word, int startRow, int startCol, String direction) {
        int len = word.length();

        switch (direction) {
            case "horizontal":
                for (int i = 0; i < len; i++) {
                    matrix[startRow][startCol + i] = word.charAt(i);
                }
                break;

            case "vertical":
                for (int i = 0; len > 0 && i < len; i++) {
                    matrix[startRow + i][startCol] = word.charAt(i);
                }
                break;

            case "diagonal":
                for (int i = 0; len > 0 && i < len; i++) {
                    matrix[startRow + i][startCol + i] = word.charAt(i);
                }
                break;
        }
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
