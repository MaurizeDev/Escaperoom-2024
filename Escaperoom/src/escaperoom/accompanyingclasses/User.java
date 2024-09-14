package escaperoom.accompanyingclasses;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import escaperoom.enums.GameStatus;

/**
 * A User object is created for the player of the game. The player's performance in the Riddles is saved in the User object,
 * so it can be displayed or saved in a file/database at the end.
 */
public class User {

    // ***********************************************************
    // ********************* Attributes **************************
    // ***********************************************************
    
    /** saves the username of the player */
    private String userName;
    
    
    // ********** Performance in the Riddles ***************
    
    /** saves the time taken for the first Riddle. Data type "long" because the method "Duration.between(startTime, endTime).getSeconds()" 
     * returns a long value */
    private long timeRiddle1NumberPuzzle;
    
    /** saves the time taken for the second Riddle */
    private long timeRiddle2Labyrinth;
    
    /** saves the time taken for the third Riddle */
    private long timeRiddle3GuessNumber;
    
    /** saves the time taken for the fourth Riddle */
    private long timeRiddle4FindSentence;
    
    /** saves the time taken for the fifth Riddle */
    private long timeRiddle5Mastermind;
    
    /** saves the number of failed attempts in the first Riddle */
    private int failedAttemptsRiddle1NumberPuzzle;
    
    /** saves the number of failed attempts in the third Riddle */
    private int failedAttemptsRiddle3GuessNumber;
    
    /** saves the number of failed attempts in the fourth Riddle */
    private int failedAttemptsRiddle4FindSentence;
    
    /** saves the number of failed attempts in the fifth Riddle */
    private int failedAttemptsRiddle5Mastermind;
    
    
    // ********** Points ***************

    /** saves the score for the first Riddle (continue the number series) */
    private int pointsRiddle1;
    
    /** saves the score for the second Riddle (Labyrinth) */
    private int pointsRiddle2;
    
    /** saves the score for the third Riddle (Guess the number) */
    private int pointsRiddle3;
    
    /** saves the score for the fourth Riddle (Find the sentence) */
    private int pointsRiddle4;
    
    /** saves the score for the fifth Riddle (Mastermind) */
    private int pointsRiddle5;
    
    /** Attribute to calculate the total score at the end. -> Sum of all points achieved in the Riddles. Calculated at the very end. */
    private int totalPoints;
    
    
    // ********** Other ***************
    
    /** saves whether the user has escaped from the escape room at the end */
    private boolean escapeRoomExited;
    
    /** saves whether the user completed Riddle 1 or not */
    private GameStatus gameStatusRiddle1;

    /** saves whether the user completed Riddle 2 or not */
    private GameStatus gameStatusRiddle2;

    /** saves whether the user completed Riddle 3 or not */
    private GameStatus gameStatusRiddle3;
    
    /** saves whether the user completed Riddle 4 or not */
    private GameStatus gameStatusRiddle4;
    
    /** saves whether the user completed Riddle 5 or not */
    private GameStatus gameStatusRiddle5;
    
    
    
    // ***********************************************************
    // ******************* Constructors **************************
    // ***********************************************************
    
    /**
     * Default username is set to "Player", so that in case of incorrect input or during testing, "null" doesn't appear in the name.
     * The remaining attributes don't need to be initialized, as they are automatically set to 0.
     */
    public User() {
        userName = "Player";
        escapeRoomExited = false; // Initialize to false just in case, as the logic of some methods might change later. Not strictly necessary at the moment.
    }
    
    
    
    // ***********************************************************
    // ********************** Methods ****************************
    // ***********************************************************
    
    /**
     * saves the time for the number Riddle in the User object.
     * @param timeRiddle1
     */
    public void saveTimeNumberPuzzle(long timeRiddle1) {
        this.timeRiddle1NumberPuzzle = timeRiddle1;
    }
    
    /**
     * saves the score for the number Riddle in the User object.
     * @param scoreRiddle1
     */
    public void saveScoreNumberPuzzle(int scoreRiddle1) {
        this.pointsRiddle1 = scoreRiddle1;
    }
    
    /**
     * saves the time for the labyrinth Riddle in the User object.
     * @param timeRiddle2
     */
    public void saveTimeLabyrinthRiddle(long timeRiddle2) {
        this.timeRiddle2Labyrinth = timeRiddle2;
    }
    
    /**
     * saves the score for the labyrinth Riddle in the User object.
     * @param scoreRiddle2
     */
    public void saveScoreLabyrinthRiddle(int scoreRiddle2) {
        this.pointsRiddle2 = scoreRiddle2;
    }
    
    /**
     * saves the time for the guess number Riddle in the User object.
     * @param timeRiddle3
     */
    public void saveTimeGuessNumberRiddle(long timeRiddle3) {
        this.timeRiddle3GuessNumber = timeRiddle3;
    }
    
    /**
     * saves the score for the guess number Riddle in the User object.
     * @param scoreRiddle3
     */
    public void saveScoreGuessNumberRiddle(int scoreRiddle3) {
        this.pointsRiddle3 = scoreRiddle3;
    }
    
    /**
     * saves the time for the find sentence Riddle in the User object.
     * @param timeRiddle4
     */
    public void saveTimeFindSentenceRiddle(long timeRiddle4) {
        this.timeRiddle4FindSentence = timeRiddle4;
    }
    
    /**
     * saves the score for the find sentence Riddle in the User object.
     * @param scoreRiddle4
     */
    public void saveScoreFindSentenceRiddle(int scoreRiddle4) {
        this.pointsRiddle4 = scoreRiddle4;
    }
    
    /**
     * saves the time for the Mastermind Riddle in the User object.
     * @param timeRiddle5
     */
    public void saveTimeMastermindRiddle(long timeRiddle5) {
        this.timeRiddle5Mastermind = timeRiddle5;
    }
    
    /**
     * saves the score for the Mastermind Riddle in the User object.
     * @param scoreRiddle5
     */
    public void saveScoreMastermindRiddle(int scoreRiddle5) {
        this.pointsRiddle5 = scoreRiddle5;
    }
    
    /**
     * Calculates the total points and outputs the player's performance as a string.
     * This method is also used to save the game progress to a .txt file. It works dynamically, so every time new content 
     * (e.g., a new Riddle) is added, it will be saved as game progress in the .txt file.
     */
    public void showUserStatistics(Writer writer) throws IOException {
        PrintWriter printWriter = new PrintWriter(writer);
        totalPoints = pointsRiddle1 + pointsRiddle2 + pointsRiddle3 + pointsRiddle4 + pointsRiddle5; // Dynamic calculation of total points at the end
        
        // Check if escapeRoomExited is true or false and display "Yes" or "No" accordingly (convert Boolean true or false to yes or no)
        String escapeRoomDoorOpened = escapeRoomExited ? "Yes (exited the escape room)" : "No (trapped in escape room -> 0 points)";
        
        // If escapeRoomExited is false, set the points to 0
        if (!escapeRoomExited) {
            totalPoints = 0;
        }

        printWriter.println("User Stats:\n" +
            "\tName:\t\t" + userName + "\n" +
            "\tRiddle 1:\t" + timeRiddle1NumberPuzzle + " seconds\t" + failedAttemptsRiddle1NumberPuzzle + " failed attempt(s)  \tStatus: " + gameStatusRiddle1 + "\n" +
            "\tRiddle 2:\t" + timeRiddle2Labyrinth + " seconds  \t\t\t\tStatus: " + gameStatusRiddle2 + "\n" +
            "\tRiddle 3:\t" + timeRiddle3GuessNumber + " seconds\t" + failedAttemptsRiddle3GuessNumber + " failed attempt(s)  \tStatus: " + gameStatusRiddle3 + "\n" +
            "\tRiddle 4:\t" + timeRiddle4FindSentence + " seconds\t" + failedAttemptsRiddle4FindSentence + " failed attempt(s)  \tStatus: " + gameStatusRiddle4 + "\n" +
            "\tRiddle 5:\t" + timeRiddle5Mastermind + " seconds\t" + failedAttemptsRiddle5Mastermind + " failed attempt(s)  \tStatus: " + gameStatusRiddle5 + "\n" +
            "\tRiddle 6:\t" + escapeRoomDoorOpened + "\n" +
            "\tTotal:\t\t" + totalPoints + " points");

        printWriter.flush(); // Ensure data is written when output is to a file
    }
    
    
    
    // ***********************************************************
    // ******************* Getters & Setters *********************
    // ***********************************************************
    
    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username, if the user's input is not empty. The input can be empty if the user doesn't want to provide a name.
     * If the input is empty, the user retains the default name "Player".
     * @param userName the username to set
     */
    public void setUserName(String userName) {
        if (userName != null && !userName.trim().isEmpty()) {
            this.userName = userName;
        }
    }
    
    /**
     * @return the timeRiddle1NumberPuzzle
     */
    public long getTimeRiddle1NumberPuzzle() {
        return timeRiddle1NumberPuzzle;
    }
    
    /**
     * @return the failedAttemptsRiddle1NumberPuzzle
     */
    public int getFailedAttemptsRiddle1NumberPuzzle() {
        return failedAttemptsRiddle1NumberPuzzle;
    }
    
    /**
     * @param failedAttempts the number of failed attempts for the first Riddle
     */
    public void setFailedAttemptsRiddle1NumberPuzzle(int failedAttempts) {
        this.failedAttemptsRiddle1NumberPuzzle = failedAttempts;
    }
    
    /**
     * @param escapeRoomExited the escapeRoomExited to set
     */
    public void setEscapeRoomExited(boolean escapeRoomExited) {
        this.escapeRoomExited = escapeRoomExited;
    }
    
    /**
     * @return the failedAttemptsRiddle3GuessNumber
     */
    public int getFailedAttemptsRiddle3GuessNumber() {
        return failedAttemptsRiddle3GuessNumber;
    }
    
    /**
     * @param failedAttemptsRiddle3GuessNumber the failedAttemptsRiddle3GuessNumber to set
     */
    public void setFailedAttemptsRiddle3GuessNumber(int failedAttempts) {
        this.failedAttemptsRiddle3GuessNumber = failedAttempts;
    }
    
    /**
     * @return the failedAttemptsRiddle4FindSentence
     */
    public int getFailedAttemptsRiddle4FindSentence() {
        return failedAttemptsRiddle4FindSentence;
    }

    /**
     * @param failedAttemptsRiddle4FindSentence the failedAttemptsRiddle4FindSentence to set
     */
    public void setFailedAttemptsRiddle4FindSentence(int failedAttemptsRiddle4FindSentence) {
        this.failedAttemptsRiddle4FindSentence = failedAttemptsRiddle4FindSentence;
    }
    
    /**
     * @return the failedAttemptsRiddle5Mastermind
     */
    public int getFailedAttemptsRiddle5Mastermind() {
        return failedAttemptsRiddle5Mastermind;
    }
    
    /**
     * @param failedAttemptsRiddle5Mastermind the failedAttemptsRiddle5Mastermind to set
     */
    public void setFailedAttemptsRiddle5Mastermind(int failedAttemptsRiddle5Mastermind) {
        this.failedAttemptsRiddle5Mastermind = failedAttemptsRiddle5Mastermind;
    }
    
    /**
     * Sets the game status for Riddle 1.
     *
     * @param status The new game status for Riddle 1 (e.g., IN_PROGRESS, WON, LOST)
     */
    public void setGameStatusRiddle1(GameStatus status) {
        this.gameStatusRiddle1 = status;
    }

    /**
     * Sets the game status for Riddle 2.
     *
     * @param status The new game status for Riddle 2 (e.g., IN_PROGRESS, WON, LOST)
     */
    public void setGameStatusRiddle2(GameStatus status) {
        this.gameStatusRiddle2 = status;
    }

    /**
     * Sets the game status for Riddle 3.
     *
     * @param status The new game status for Riddle 3 (e.g., IN_PROGRESS, WON, LOST)
     */
    public void setGameStatusRiddle3(GameStatus status) {
        this.gameStatusRiddle3 = status;
    }

    /**
     * Sets the game status for Riddle 4.
     *
     * @param status The new game status for Riddle 4 (e.g., IN_PROGRESS, WON, LOST)
     */
    public void setGameStatusRiddle4(GameStatus status) {
        this.gameStatusRiddle4 = status;
    }
    
    /**
     * @param gameStatusRiddle5 the gameStatusRiddle5 to set
     */
    public void setGameStatusRiddle5(GameStatus gameStatusRiddle5) {
        this.gameStatusRiddle5 = gameStatusRiddle5;
    }

    /**
     * @return the gameStatusRiddle1
     */
    public GameStatus getGameStatusRiddle1() {
        return gameStatusRiddle1;
    }

    /**
     * @return the gameStatusRiddle2
     */
    public GameStatus getGameStatusRiddle2() {
        return gameStatusRiddle2;
    }

    /**
     * @return the gameStatusRiddle3
     */
    public GameStatus getGameStatusRiddle3() {
        return gameStatusRiddle3;
    }

    /**
     * @return the gameStatusRiddle4
     */
    public GameStatus getGameStatusRiddle4() {
        return gameStatusRiddle4;
    }
    
    /**
     * @return the gameStatusRiddle5
     */
    public GameStatus getGameStatusRiddle5() {
        return gameStatusRiddle5;
    }
    
    /**
     * @return the pointsRiddle1
     */
    public int getPointsRiddle1() {
        return pointsRiddle1;
    }

    /**
     * @return the pointsRiddle2
     */
    public int getPointsRiddle2() {
        return pointsRiddle2;
    }

    /**
     * @return the pointsRiddle3
     */
    public int getPointsRiddle3() {
        return pointsRiddle3;
    }

    /**
     * @return the pointsRiddle4
     */
    public int getPointsRiddle4() {
        return pointsRiddle4;
    }

    /**
     * @return the pointsRiddle5
     */
    public int getPointsRiddle5() {
        return pointsRiddle5;
    }
    
    
    
    // ***********************************************************
    // ************************* Other ***************************
    // ***********************************************************
    
    /**
     * Method with separator line and custom heading.
     */
    public static void separator(String heading) {
        System.out.println("─".repeat(105));
        System.out.println("\t" + heading);
        System.out.println("─".repeat(105) + "\n");
    }

    /**
     * Outputs all attributes. For testing, debugging, etc.
     */
    @Override
    public String toString() {
        return "User [userName=" + userName + ", timeRiddle1NumberPuzzle=" + timeRiddle1NumberPuzzle
                + ", timeRiddle2Labyrinth=" + timeRiddle2Labyrinth + ", timeRiddle3GuessNumber="
                + timeRiddle3GuessNumber + ", timeRiddle4FindSentence=" + timeRiddle4FindSentence
                + ", timeRiddle5Mastermind=" + timeRiddle5Mastermind + ", failedAttemptsRiddle1NumberPuzzle="
                + failedAttemptsRiddle1NumberPuzzle + ", failedAttemptsRiddle3GuessNumber="
                + failedAttemptsRiddle3GuessNumber + ", failedAttemptsRiddle4FindSentence="
                + failedAttemptsRiddle4FindSentence + ", failedAttemptsRiddle5Mastermind="
                + failedAttemptsRiddle5Mastermind + ", pointsRiddle1=" + pointsRiddle1 + ", pointsRiddle2="
                + pointsRiddle2 + ", pointsRiddle3=" + pointsRiddle3 + ", pointsRiddle4=" + pointsRiddle4
                + ", pointsRiddle5=" + pointsRiddle5 + ", totalPoints=" + totalPoints + ", escapeRoomExited="
                + escapeRoomExited + ", gameStatusRiddle1=" + gameStatusRiddle1 + ", gameStatusRiddle2="
                + gameStatusRiddle2 + ", gameStatusRiddle3=" + gameStatusRiddle3 + ", gameStatusRiddle4="
                + gameStatusRiddle4 + ", gameStatusRiddle5=" + gameStatusRiddle5 + "]";
    }
    
}
