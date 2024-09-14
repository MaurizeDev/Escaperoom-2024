package escaperoom.accompanyingclasses;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import escaperoom.enums.GameStatus;
import escaperoom.interfaces.UserInteraction;

/**
 * Displays the end of the game. This includes an ASCII art with the text
 * "Success", a countdown for self-destruction, and the user stats.
 */
public class EndMessage implements UserInteraction {
    
    // ***********************************************************
    // ********************* Attributes **************************
    // ***********************************************************
    
    /**
     * Creates a list of PlayerResult objects, which is filled by importing the .txt files.
     */
    private List<PlayerResult> results = new ArrayList<>();
    
    /**
     * Our reliable butler James executes the methods of the FileAccess class. He writes the game progress
     * into a .txt file and reads the saved game progress from the .txt files for the Top 10 leaderboard.
     */
    private FileAccess james = new FileAccess();
    
    
    // ***********************************************************
    // ********************* Constructors ************************
    // ***********************************************************
    
    // No constructor needed
    
    
    
    // ***********************************************************
    // *********************** Methods ***************************
    // ***********************************************************

    /**
     * Calls the methods for the end message, ASCII art, countdown, and
     * saves the game progress into a file. The last little "task" is to
     * exit the escape room. Otherwise, the player loses all points and
     * remains trapped.
     * 
     * @param user
     * @throws InterruptedException
     * @throws IOException
     */
    public void showEndMessage(User user) throws InterruptedException, IOException {
        Scanner scanner = new Scanner(System.in);
        
        // Display message
        separator("♫♪ Click Clack ♪♫");
        if (user.getGameStatusRiddle1() == GameStatus.COMPLETED 
                && user.getGameStatusRiddle2() == GameStatus.COMPLETED
                && user.getGameStatusRiddle3() == GameStatus.COMPLETED
                && user.getGameStatusRiddle4() == GameStatus.COMPLETED
                && user.getGameStatusRiddle5() == GameStatus.COMPLETED) {
                System.out.println(
                    "Did you hear the click? You've solved all the Riddles! The lock of the door has popped open.\n");
            } else {
                System.out.println(
                    "Did you hear the click? It looks like you've solved (or at least worked on) all the Riddles!\n"
                    + "The lock of the door has popped open.\n");
            }
        String input = ""; // Initialize variable

        // Loop to repeat input until Y or N is entered
        while (true) {
            input = UserInteraction.askUserInput("Do you want to step out of the room? (Y/N)", scanner).trim().toUpperCase(); // Ask input again and format

            if (input.equals("Y")) {
                escapeRoomCompleted(user); // J
                break; // Exit loop
            } else if (input.equals("N")) {
                escapeRoomNotCompleted(user); // N
                break; // Exit loop
            } else {
                System.out.println("\nInvalid input. Please enter Y or N."); // For invalid input
            }
        }
        
//        displayPoints(user); // For testing if all points are calculated correctly -> after adding new Riddles
        
        james.saveGame(user);
        EndMessage endMessage = new EndMessage();
        
        // User is asked "Y" or "N"
        String userTop10Choice = UserInteraction.askUserInput("Do you want to see the Top 10? (Y/N)", scanner);
        
        // If the user enters "Y", the Top 10 is displayed. If the user enters "N" or something else, nothing happens and the program continues.
        if (userTop10Choice.equalsIgnoreCase("Y")) {
            james.importAllGameScores(endMessage.results);
            showTop10(endMessage);
        }
    }
    
    /**
     * Helper method to test if all points are calculated correctly. No longer used in the finished program.
     * @param user
     */
    public void displayPoints(User user) {
        System.out.println("\nRiddle 1: " + user.getPointsRiddle1() + " Riddle 2: " + user.getPointsRiddle2() + " Riddle 3: " + 
        user.getPointsRiddle3() + " Riddle 4: " + user.getPointsRiddle4() + " Riddle 5: " + user.getPointsRiddle5());
    }
    
    /**
     * Displays the top 10 player results, sorted by points in descending order.
     * 
     * <p>This method retrieves the list of player results, sorts them by points in descending order,
     * and then displays the top 10 results. Each player is shown only once with their highest score.
     * If a player's name is longer than 18 characters, it is truncated and appended with "..".
     * Additionally, players without a name or date are skipped.</p>
     * 
     * <p>Key features of this method include:
     * <ul>
     *   <li>Sorting the player results based on points in descending order.</li>
     *   <li>Ensuring that each player appears only once in the top 10, based on their highest score.</li>
     *   <li>If a player's name exceeds 18 characters, it is shortened, and ".." is appended.</li>
     *   <li>Only players with both a valid name and date are displayed.</li>
     * </ul>
     * </p>
     * 
     * @param endMessage The {@code EndMessage} object containing the list of player results.
     */
    public void showTop10(EndMessage endMessage) {
        // Get list of PlayerResult objects
        List<PlayerResult> list = endMessage.getResults();

        // Sort the list by "points" in descending order
        Collections.sort(list, (s1, s2) -> Integer.compare(s2.getPoints(), s1.getPoints())); // Sort in descending order
        
        // A Set to track players who have already been added to the Top 10, so no player is shown twice in Top 10
        Set<String> addedPlayers = new HashSet<>();

        // Display the leaderboard
        System.out.println();
        System.out.println("\033[30;106m                                                      ");
        System.out.println("                     Hall of Fame                     ");
        System.out.println("                                                      \033[0m");
        System.out.println("\n\033[1mRank\tName\t\t\tDate\t\tPoints\033[0m");

        int rank = 1;
        for (int i = 0; i < list.size() && rank <= 10; i++) {
            PlayerResult result = list.get(i);
            String name = result.getName();

            // Skip if the name or date is null (if no file available Top 10 remains empty and does not show results with "null")
            if (name == null || result.getDate() == null) {
                continue;
            }

            // Ensure only the highest score for each player is shown
            if (addedPlayers.contains(name)) {
                continue;
            }

            // If the name is longer than 19 characters, truncate to 19 characters and add ".."
            if (name.length() > 19) {
                name = name.substring(0, 19) + "..";
            }

            // Add the player to the set to prevent duplicates
            addedPlayers.add(name);

            // Display the formatted output
            System.out.printf("%-3s \t%-20s \t%-13s \t%-4d \n", rank, name, result.getDate(), result.getPoints());

            rank++;
        }
    }
    
    /**
     * Called if the user chooses (for whatever reason) not
     * to leave the escape room once the door opens. The message appears
     * that the user is now locked in, showing the countdown to self-destruction.
     * The game is not completed.
     * 
     * @param user
     * @throws InterruptedException
     * @throws IOException
     */
    private void escapeRoomNotCompleted(User user) throws InterruptedException, IOException {
        user.setEscapeRoomExited(false);
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        System.out.println(ANSI_RED
                + "\n\nOh no, that was the wrong choice :( You are now locked in the escape room, and no one knows where you are.\n\n"
                + ANSI_RESET + "Additionally, this program will self-destruct in 10 seconds!\n");
        showCountdown();
        Thread.sleep(4000);
        user.showUserStatistics(new OutputStreamWriter(System.out));
    }

    /**
     * Called when the player exits the escape room. It shows
     * a "Success" message, the countdown to self-destruction, and the game progress.
     * 
     * @param user
     * @throws InterruptedException
     * @throws IOException
     */
    private void escapeRoomCompleted(User user) throws InterruptedException, IOException {
        user.setEscapeRoomExited(true);
        showASCIIMessage(user);
        showCountdown();
        Thread.sleep(2000);
        user.showUserStatistics(new OutputStreamWriter(System.out));
    }

    /**
     * Displays an ASCII art as the end screen with the message "Success,
     * Congratulations."
     * https://patorjk.com/software/taag (Font Name: Big)
     * 
     * @param user
     * @throws InterruptedException
     */
    private void showASCIIMessage(User user) throws InterruptedException {
        final String ANSI_GREEN = "\u001B[32m"; // Green text
        final String ANSI_RESET = "\u001B[0m"; // End of colored text
        System.out.println(ANSI_GREEN + ""); // Temporary, to start green text before the separator. TODO: Clean up
        separator("Right choice!");
        Thread.sleep(500);
        System.out.println(" __     __               _ _     _   _ _   ");
        Thread.sleep(500);
        System.out.println(" \\ \\   / /              | (_)   | | (_) |  ");
        Thread.sleep(500);
        System.out.println("  \\ \\_/ /__  _   _    __| |_  __| |  _| |_ ");
        Thread.sleep(500);
        System.out.println("   \\   / _ \\| | | |  / _` | |/ _` | | | __|");
        Thread.sleep(500);
        System.out.println("    | | (_) | |_| | | (_| | | (_| | | | |_ ");
        Thread.sleep(500);
        System.out.println("    |_|\\___/ \\__,_|  \\__,_|_|\\__,_| |_|\\__|" + ANSI_RESET);
        Thread.sleep(500);
        System.out.println("   _____                            _         _       _   _                 ");
        Thread.sleep(500);
        System.out.println("  / ____|                          | |       | |     | | (_)                ");
        Thread.sleep(500);
        System.out.println(" | |     ___  _ __   __ _ _ __ __ _| |_ _   _| | __ _| |_ _  ___  _ __  ___ ");
        Thread.sleep(500);
        System.out.println(" | |    / _ \\| '_ \\ / _` | '__/ _` | __| | | | |/ _` | __| |/ _ \\| '_ \\/ __|");
        Thread.sleep(500);
        System.out.println(" | |___| (_) | | | | (_| | | | (_| | |_| |_| | | (_| | |_| | (_) | | | \\__ \\");
        Thread.sleep(500);
        System.out.println("  \\_____\\___/|_| |_|\\__, |_|  \\__,_|\\__|\\__,_|_|\\__,_|\\__|_|\\___/|_| |_|___/");
        Thread.sleep(500);
        System.out.println("                     __/ |                                                  ");
        Thread.sleep(500);
        System.out.println("                    |___/                                                   \n");
        Thread.sleep(2000);
        System.out.println(user.getUserName() + ", you have completed the secret mission!!");
        Thread.sleep(5000);
        System.out.println(
                "\nTo prevent the painstakingly collected data from falling into the wrong hands, this program"
                        + " will self-destruct in 10 seconds\n");
        Thread.sleep(2000);
    }

    /**
     * A countdown after which the program self-destructs.
     * 
     * @throws InterruptedException
     */
    private void showCountdown() throws InterruptedException {
        for (int i = 10; i > 0; i--) {
            System.out.println(i + "...");
            Thread.sleep(1000); // Pause for 1 second
        }
        System.out.println("BOOOOOMMM (Computer exploded)\n");
    }

    
    
    // ***********************************************************
    // ******************* getters & setters *********************
    // ***********************************************************
    
    /**
     * @return the results
     */
    public List<PlayerResult> getResults() {
        return results;
    }
    
    
    
    // ***********************************************************
    // ************************ Other ****************************
    // ***********************************************************
    
    /**
     * Method with separator line and custom headline.
     */
    public static void separator(String headline) {
        System.out.println("\n" + "─".repeat(105));
        System.out.println("\t" + headline);
        System.out.println("─".repeat(105) + "\n");
    }

}
