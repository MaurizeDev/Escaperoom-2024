package escaperoom.riddles;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import escaperoom.accompanyingclasses.User;
import escaperoom.enums.GameStatus;
import escaperoom.interfaces.UserInteraction;
import escaperoom.interfaces.TimeMeasurement;

import java.time.LocalTime;

/**
 * In this puzzle, the user must find the way out of a labyrinth.
 */
public class Riddle2Labyrinth implements UserInteraction, TimeMeasurement {

    // ***********************************************************
    // ********************* Attributes **************************
    // ***********************************************************
    
    /**
     * Stores the current game status for evaluation at the end.
     */
    private GameStatus gameStatus;

    /**
     * The JFrame object that represents the window for the labyrinth puzzle.
     */
    private JFrame frame;

    /**
     * The JPanel object where the labyrinth and the player's position are drawn.
     */
    private JPanel panel;

    /**
     * The X-coordinate of the player in the labyrinth.
     * Initially set to 7 (starting X position).
     */
    private int playerX = 7;

    /**
     * The Y-coordinate of the player in the labyrinth.
     * Initially set to 3 (starting Y position).
     */
    private int playerY = 3;

    /**
     * A 2D array that represents the layout of the labyrinth.
     * The '#' character represents walls, and spaces ' ' represent free paths.
     */
    private final char[][] labyrinth = {
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
        {'#', ' ', '#', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#'},
        {'#', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#'},
        {'#', ' ', '#', ' ', '#', '#', '#', '#', '#', ' ', '#', ' ', '#'},
        {'#', ' ', '#', ' ', '#', ' ', ' ', ' ', '#', ' ', '#', ' ', '#'},
        {'#', ' ', '#', ' ', '#', ' ', '#', '#', '#', ' ', '#', ' ', '#'},
        {'#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#'},
        {'#', ' ', '#', '#', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#'},
        {'#', '#', '#', '#', '#', ' ', '#', '#', '#', '#', '#', '#', '#'},
    };
    
    
    
    // ***********************************************************
    // ********************* Constructors ************************
    // ***********************************************************
    
    // Default constructor is sufficient

    
    
    
    // ***********************************************************
    // *********************** Methods ***************************
    // ***********************************************************
    
    /**
     * Starts the labyrinth puzzle and measures the time the user takes to exit the labyrinth.
     * 
     * @param user The user object that stores time and score.
     * @throws InterruptedException if the thread is interrupted during a pause.
     */
    public void startLabyrinthPuzzle(User user) throws InterruptedException {
        separator("Moving on to a labyrinth puzzle");
        System.out.println("Move the yellow square with w/a/s/d to exit the labyrinth");
        
        LocalTime startTime = startTime(); // Start time for time measurement
        this.gameStatus = GameStatus.IN_PROGRESS; // Currently not used, but might be helpful for logging or debugging
        createGui(user, startTime);
        while (true) {
            if (frame.isVisible()) {
                Thread.sleep(500);
                continue;
            }
            UserInteraction.pressEnterToContinue();
            break;
        }
    }

    /**
     * Creates the graphical user interface (GUI) for the labyrinth puzzle and sets the player's movement controls via the keyboard.
     * 
     * <p>The player can move through the labyrinth using the following keys:
     * <ul>
     *   <li>'w' - Move up</li>
     *   <li>'s' - Move down</li>
     *   <li>'a' - Move left</li>
     *   <li>'d' - Move right</li>
     * </ul>
     * 
     * When the player exits the labyrinth, the time is measured and the score is calculated based on the time taken.
     * 
     * @param user The user object that stores time and score.
     * @param startTime The start time of the puzzle, used for time measurement.
     */
    @SuppressWarnings("serial")
    private void createGui(User user, LocalTime startTime) {
        frame = new JFrame("Labyrinth Puzzle"); // Issue -> red error on Mac caused by this line
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int i = 0; i < labyrinth.length; i++) {
                    for (int j = 0; j < labyrinth[i].length; j++) {
                        if (i == playerX && j == playerY) {
                            g.setColor(Color.ORANGE);
                            g.fillRect(j * 30, i * 30, 30, 30); // Player in orange
                        } else if (labyrinth[i][j] == '#') {
                            g.setColor(Color.BLACK);
                            g.fillRect(j * 30, i * 30, 30, 30); // Walls in black
                        } else {
                            g.setColor(Color.WHITE);
                            g.fillRect(j * 30, i * 30, 30, 30); // Empty spaces in white
                        }
                    }
                }
            }
        };
        panel.setPreferredSize(new Dimension(400, 400));
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        // Keyboard controls
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char move = e.getKeyChar();
                int newX = playerX;
                int newY = playerY;

                switch (move) {
                    case 'w': newX--; break; // Move up
                    case 's': newX++; break; // Move down
                    case 'a': newY--; break; // Move left
                    case 'd': newY++; break; // Move right
                    default:
                        return;
                }

                long puzzleTime = calculateDuration(startTime); // Check current time

                // Check if the time limit (e.g., 180 seconds) has been exceeded
                if (puzzleTime >= 180) {
                    gameStatus = GameStatus.NOT_COMPLETED; // Set status to "NOT_COMPLETED"
                    JOptionPane.showMessageDialog(frame, "Time limit exceeded! The puzzle has been aborted.");
                    user.saveTimeLabyrinthRiddle(puzzleTime);
                    user.setGameStatusRiddle2(gameStatus);
                    frame.dispose(); // Close GUI
                    return;
                }

                if (newX < 0 || newY < 0 || newX >= labyrinth.length || newY >= labyrinth[0].length) {
                    // Player has exited the labyrinth
                    puzzleTime = calculateDuration(startTime); // Determine end time and calculate the time difference in seconds
                    JOptionPane.showMessageDialog(frame, "Congratulations! You successfully exited the labyrinth!\n"
                            + "You took " + puzzleTime + " seconds");
                    gameStatus = GameStatus.COMPLETED;

                    // Calculate score based on time
                    int scorePuzzle2 = 100 - (int) puzzleTime; // Score based on time
                    if (scorePuzzle2 < 0) scorePuzzle2 = 0;
                    if (scorePuzzle2 > 100) scorePuzzle2 = 100;

                    // Save game progress
                    user.saveTimeLabyrinthRiddle(puzzleTime);
                    user.saveScoreLabyrinthRiddle(scorePuzzle2);
                    user.setGameStatusRiddle2(gameStatus);

                    frame.dispose(); // Close GUI
                }

                // Check if the player can move to a free space
                if (newX >= 0 && newY >= 0 && newX < labyrinth.length && newY < labyrinth[0].length && labyrinth[newX][newY] == ' ') {
                    playerX = newX;
                    playerY = newY;
                    panel.repaint(); // Redraw the labyrinth
                }
            }
        });
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
