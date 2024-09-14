package escaperoom.accompanyingclasses;

import java.util.Scanner;

/**
 * A nice welcome message with ASCII art.
 */
public class WelcomeMessage {

    // ***********************************************************
    // ********************* Attributes **************************
    // ***********************************************************
    
    
    
    // ***********************************************************
    // ******************** Constructors *************************
    // ***********************************************************
    
    
    
    
    // ***********************************************************
    // ********************** Methods ****************************
    // ***********************************************************
    
    /**
     * Calls the methods for the banner and name input, preparing the user for the game.
     * @param user
     * @throws InterruptedException
     */
    public void welcome(User user) throws InterruptedException {
        welcomeBanner();
        user.setUserName(getFormattedUserName(userNameInput())); // Assign the formatted name
        System.out.println("\nHello " + getFormattedUserName(user.getUserName()) + ", you must complete a secret mission, and you will face some tricky tasks along the way!");
    }
    
    /**
     * Displays a banner in ASCII art: "Welcome to the Escape Room".
     * https://patorjk.com/software/taag (Font Name: Calvin S)
     * @throws InterruptedException 
     */
    public void welcomeBanner() throws InterruptedException {
        String banner = "╦ ╦┌─┐┬  ┌─┐┌─┐┌┬┐┌─┐  ┌┬┐┌─┐  ┌┬┐┬ ┬┌─┐  ╔═╗┌─┐┌─┐┌─┐┌─┐┌─┐┬─┐┌─┐┌─┐┌┬┐\n"
        		+ "║║║├┤ │  │  │ ││││├┤    │ │ │   │ ├─┤├┤   ║╣ └─┐│  ├─┤├─┘├┤ ├┬┘│ ││ ││││\n"
        		+ "╚╩╝└─┘┴─┘└─┘└─┘┴ ┴└─┘   ┴ └─┘   ┴ ┴ ┴└─┘  ╚═╝└─┘└─┘┴ ┴┴  └─┘┴└─└─┘└─┘┴ ┴\n"
        		+ "";

        // Loop that prints the banner 10 times
        for (int i = 0; i < 10; i++) {
            System.out.println(banner);
            Thread.sleep(70);  // Pause between prints
        }
    }

    
    /**
     * Asks the user for their name.
     * @return
     * @throws InterruptedException
     */
    @SuppressWarnings("resource")
    public String userNameInput() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("What's your name?");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    
    /**
     * Formats the given username by capitalizing the first letter of each word.
     * Words are considered to be separated by either spaces or hyphens.
     * Already capitalized letters within words will remain unchanged.
     * For example, "leonardo diCaprio" will be formatted as "Leonardo DiCaprio",
     * and "neil deGrasse tyson" as "Neil DeGrasse Tyson".
     * 
     * <p>If the username contains multiple words separated by spaces or hyphens,
     * each word will have its first letter capitalized. Subsequent letters within
     * each word will remain as they are in the original input (i.e., maintaining
     * existing capitalization).</p>
     * 
     * @param userName The original username input by the user.
     * @return The formatted username where each word starts with a capital letter,
     * or the original input if it is null or empty.
     */
    public String getFormattedUserName(String userName) {
        if (userName == null || userName.isEmpty()) {
            return userName; // If the name is empty, just return it
        }

        // Split by both spaces and hyphens, and keep the delimiter in the result
        String[] words = userName.split("(?<=\\b)(?=[ -])|(?<=[ -])(?=\\b)");
        StringBuilder formattedName = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                // Check if the word is a space or hyphen and handle it
                if (word.equals(" ") || word.equals("-")) {
                    formattedName.append(word); // Add the space or hyphen as it is
                } else {
                    // Capitalize the first letter and leave the rest unchanged
                    formattedName.append(word.substring(0, 1).toUpperCase()) // Capitalize the first letter
                                  .append(word.substring(1)); // Leave the rest as is
                }
            }
        }

        return formattedName.toString().trim(); // Remove trailing space
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
        System.out.println("─".repeat(105));
        System.out.println("\t" + heading);
        System.out.println("─".repeat(105) + "\n");
    }

}
