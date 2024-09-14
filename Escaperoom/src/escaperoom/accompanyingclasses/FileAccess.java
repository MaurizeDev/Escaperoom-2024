package escaperoom.accompanyingclasses;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * This class should only handle access to saved games, nothing else.
 */
public class FileAccess {
    
	/**
	 * Saves the current game progress to a file, including the player's statistics and the current date and time.
	 * The file is saved in the "src/Escaperoom/Scores/" directory with a filename formatted as "username_date_time.txt".
	 * The method also formats the player's username by replacing spaces with hyphens in the filename. If the username 
	 * is "Test", the method will not perform any saving actions.
	 * 
	 * @param user The {@link User} object representing the player, containing the statistics and the username.
	 * 
	 * @throws IOException if an error occurs while writing to the file.
	 */

	void saveGame(User user) {
		if (!user.getUserName().equalsIgnoreCase("Test")) {
			// Retrieve the current date and time for saving
			LocalDate today = LocalDate.now();
			LocalTime time = LocalTime.now();

			// Formatter for date and time
			DateTimeFormatter dateFormatterForFileName = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			DateTimeFormatter dateFormatterScore = DateTimeFormatter.ofPattern("dd MMM yyyy");
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH-mm"); // Time in HH-mm-ss format

			String dateFileName = today.format(dateFormatterForFileName);
			String dateScore = today.format(dateFormatterScore);
			String currentTime = time.format(timeFormatter);

			// Format the username to replace spaces with hyphens
			String formattedUserName = user.getUserName().replaceAll(" ", "-");

			// Directory for saved games
			String directory = "src/Escaperoom/Scores/";

			// File name based on the username, date, and time
			String fileName = formattedUserName + "_" + dateFileName + "_" + currentTime + ".txt";

			// Complete path to the file
			String completePath = Paths.get(directory, fileName).toString();

			try (FileWriter fileWriter = new FileWriter(completePath)) {
				// Display (and write) user statistics to the file
				user.showUserStatistics(fileWriter);

				// Save date and time below the game progress
				fileWriter.write("\nGame progress saved on: " + dateScore);

				// Output success message to the console
				System.out.println("\nYour game progress has been saved in the file " + fileName + ".\n");

			} catch (IOException exception) {
				System.out.println("Error saving game progress: " + exception.getMessage());
			}
		}
	}
    
    /**
     * This method imports all saved game progress from a predefined directory.
     * 
     * <p>The directory where the saved games are stored is fixed to
     * "src/Escaperoom/SavedGames/". The method loops through all files in this 
     * directory and calls the method createPlayerResultObject(File savedGame)
     * for each file to create a corresponding PlayerResult object.</p>
     *
     * <p>The method takes no parameters and returns no values. There is no check, as the directory
     * always contains at least one file when this method is called. No error can occur. It also
     * assumes that all files in the given directory are valid saved games.</p>
     */
    public void importAllGameScores(List<PlayerResult> results) {
        String directory = "src/Escaperoom/Scores/";
        File folder = new File(directory);
        File[] allSavedGames = folder.listFiles();
        for (File savedGame : allSavedGames) {
            createPlayerResultObject(savedGame, results);
        }
    }
    
    
    /**
     * Creates a {@code PlayerResult} object from a file containing saved game data
     * and adds it to the list of results.
     *
     * <p>This method reads the file passed as a {@code File} object {@code savedGame} 
     * line by line, extracts the player's name, total points, and the date when the 
     * game progress was saved. These data are then stored in a new {@code PlayerResult} 
     * object, which is added to the list of results.</p>
     *
     * <p>The method uses a {@code BufferedReader} to efficiently read the content of the file, 
     * and it automatically closes the reader using a try-with-resources block to ensure 
     * resources are released properly.</p>
     *
     * <p>The method searches for the following specific strings in the file:
     * <ul>
     *   <li><b>"Name"</b>: Extracts the player's name and saves it in the {@code PlayerResult}.</li>
     *   <li><b>"Total"</b>: Extracts the player's total points and saves them in the {@code PlayerResult}.</li>
     *   <li><b>"Game progress saved on"</b>: Extracts the date when the game progress was saved and stores it in the {@code PlayerResult}.</li>
     * </ul>
     * </p>
     *
     * <p>If an {@code IOException} occurs during reading, the stack trace of the exception is printed.</p>
     *
     * @param savedGame The file containing the game progress from which data should be read.
     * @throws IOException if an error occurs while accessing the file.
     */
    private void createPlayerResultObject(File savedGame, List<PlayerResult> results) {
        PlayerResult result = new PlayerResult();
        try (BufferedReader reader = new BufferedReader(new FileReader(savedGame))) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                if (line.contains("Name")) {
                    String trimmed = line.trim();
                    String name = trimmed.replace("Name:", "").trim();
                    result.setName(name);
                }
                if (line.contains("Total")) {
                    String trimmed = line.trim();
                    String points = trimmed.replace("Total:", "").trim().replace("points", "").trim();
                    int pointValue = Integer.valueOf(points);
                    result.setPoints(pointValue);
                }
                if (line.contains("Game progress saved on")) {
                    String trimmed = line.trim();
                    String date = trimmed.replace("Game progress saved on:", "").trim();
                    result.setDate(date);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        results.add(result);
    }
}
