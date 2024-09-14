package escaperoom.tests;

import java.io.IOException;

import escaperoom.accompanyingclasses.FileAccess;
import escaperoom.accompanyingclasses.EndMessage;
import escaperoom.accompanyingclasses.User;
import escaperoom.riddles.Riddle4FindSentence;
import escaperoom.riddles.Riddle5Mastermind;

public class Tests {

    public static void main(String[] args) throws InterruptedException, IOException {

        User user = new User();
        EndMessage endMessage = new EndMessage();
        FileAccess james = new FileAccess();
        
//        endMessage.showEndMessage(user);
        james.importAllGameScores(endMessage.getResults());
        endMessage.showTop10(endMessage);
        
//        Riddle4FindSentence riddle4 = new Riddle4FindSentence();
//        riddle4.startWordSearchRiddle(user);
        

    }
}
