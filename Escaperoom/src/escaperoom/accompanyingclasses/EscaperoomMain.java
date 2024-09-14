package escaperoom.accompanyingclasses;

import java.io.IOException;

import escaperoom.riddles.Riddle1NumberPuzzle;
import escaperoom.riddles.Riddle2Labyrinth;
import escaperoom.riddles.Riddle3GuessNumber;
import escaperoom.riddles.Riddle4FindSentence;
import escaperoom.riddles.Riddle5Mastermind;

/**
 * This class contains the main method that controls the program flow.
 */
public class EscaperoomMain {

    /**
     * This class controls the program flow:
     * The main method creates Riddle objects and then starts the respective Riddle.
     * At the beginning and end, there is a welcome and farewell message.
     * 
     * @param args
     * @throws InterruptedException
     * @throws IOException
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        
        User user = new User();
        
        // Create WelcomeMessage object to call the welcome() method
        WelcomeMessage message = new WelcomeMessage();
        message.welcome(user);

        // Number series Riddle
        Riddle1NumberPuzzle numberPuzzle = new Riddle1NumberPuzzle();
        numberPuzzle.startNumberPuzzle(user);

        // Labyrinth Riddle
        Riddle2Labyrinth labyrinthRiddle = new Riddle2Labyrinth();
        labyrinthRiddle.startLabyrinthPuzzle(user);
        
        // Guess the number Riddle
        Riddle3GuessNumber guessNumberRiddle = new Riddle3GuessNumber();
        guessNumberRiddle.startGuessNumber(user);
        
        // Find the sentence Riddle
        Riddle4FindSentence findSentenceRiddle = new Riddle4FindSentence();
        findSentenceRiddle.startWordSearchRiddle(user);
        
        // Superbrain (guess the colors) Riddle
        Riddle5Mastermind mastermindRiddle = new Riddle5Mastermind();
        mastermindRiddle.startMastermindRiddle(user);
        
        
        // TODO: Many more Riddles
       
        
        // End
        EndMessage endMessage = new EndMessage();
        endMessage.showEndMessage(user);
        
        
        
        // TODO: Improve storytelling -> a better, more detailed story like in a real escape room
        // TODO: Possibly allow selection of Riddles -> solve in your own order
        // TODO: Possibly add "simulated" interfaces for the relation to the physical world like in a real escape room
        // TODO: Possibly save game progress right after passing the final challenge, so you can skip the end sequence        
    }
}
