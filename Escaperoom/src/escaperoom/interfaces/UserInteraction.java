package escaperoom.interfaces;

import java.util.Scanner;

/**
 * Not a template, just a reference type. The interface provides methods that are repeatedly used in puzzles and other classes.
 */
public interface UserInteraction {
    
    /**
     *  Method to wait for Enter. The program pauses until the user presses Enter to continue.
     */
    @SuppressWarnings("resource")
    public static void pressEnterToContinue() {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_GRAY_BACKGROUND = "\u001B[47m";  // Gray background
        final String ANSI_BLACK_TEXT = "\u001B[30m";       // Black text
        
        // Create scanner
        Scanner scanner = new Scanner(System.in);
        
        // Display message and wait for Enter
        System.out.println("\n" + ANSI_GRAY_BACKGROUND + ANSI_BLACK_TEXT + ">> Press Enter to continue..." + ANSI_RESET);
        scanner.nextLine(); // Wait for input (Enter key)
    }
    
    /**
     * Displays a message to the user and waits for their input.
     * 
     * <p>This method displays the provided message with a gray background and black text, then waits for the user's input.
     * The input is read from the provided Scanner object and returned as a string.</p>
     * 
     * @param message  The message displayed to the user to prompt for input.
     * @param scanner  The Scanner object used to capture the user's input.
     * @return The user's input as a string.
     */
    public static String askUserInput(String message, Scanner scanner) {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_GRAY_BACKGROUND = "\u001B[47m";  // Gray background
        final String ANSI_BLACK_TEXT = "\u001B[30m";       // Black text
        
        // Display message
        System.out.println("\n" + ANSI_GRAY_BACKGROUND + ANSI_BLACK_TEXT + message + ANSI_RESET);
        
        // Capture and return the user's input
        return scanner.nextLine();
    }    
}
