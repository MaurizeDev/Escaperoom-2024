package escaperoom.accompanyingclasses;

/**
 * Class for storing player results (for loading the Top 10 leaderboard). A .txt file with the saved game progress is loaded,
 * and the results are stored in a "PlayerResult" object so that they can be sorted and displayed.
 */
public class PlayerResult {
    
    // ***********************************************************
    // ********************* Attributes **************************
    // ***********************************************************
    
    /**
     * Contains the player's name.
     */
    private String name;
    
    /**
     * Contains the date of the game progress.
     */
    private String date;
    
    /**
     * Contains the achieved score.
     */
    private int points;
    
    
    
    // ***********************************************************
    // ********************* Constructors ************************
    // ***********************************************************

    /**
     * Parameterless constructor to create PlayerResult objects, which are then filled with the data read from the .txt file.
     */
    public PlayerResult() {
    }
    
    
    
    // ***********************************************************
    // ********************** Methods ****************************
    // ***********************************************************
    
    /**
     * Returns the PlayerResult object as a string. I used this for testing. Might be needed again for testing.
     * Theoretically, it can be deleted in the final version (as of today).
     */
    @Override
    public String toString() {
        return "PlayerResult [Name: " + name + ", Date: " + date + ", Points: " + points + "]";
    }


    
    // ***********************************************************
    // ******************* Getters & Setters *********************
    // ***********************************************************
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the points
     */
    public int getPoints() {
        return points;
    }

    /**
     * @param points the points to set
     */
    public void setPoints(int points) {
        this.points = points;
    }
    
}
