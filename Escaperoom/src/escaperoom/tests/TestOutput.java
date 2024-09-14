package escaperoom.tests;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import escaperoom.accompanyingclasses.FileAccess;
import escaperoom.accompanyingclasses.EndMessage;
import escaperoom.accompanyingclasses.PlayerResult;

public class TestOutput {

    public static void main(String[] args) {
        
        EndMessage endMessage = new EndMessage();
        FileAccess james = new FileAccess();
        james.importAllGameScores(endMessage.getResults());
        
        // Get the list of PlayerResult objects
        List<PlayerResult> list = endMessage.getResults();
        
        // Sort the list by "points" in descending order
        Collections.sort(list, new Comparator<PlayerResult>() {
            @Override
            public int compare(PlayerResult p1, PlayerResult p2) {
                return Integer.compare(p2.getPoints(), p1.getPoints()); // Sort in descending order
            }
        });

        // Output the leaderboard
        System.out.println("\033[1mRank\tName\t\tDate\t\tPoints\033[0m");
        for (int i = 0; i < Math.min(10, list.size()); i++) {
            System.out.printf("%-3s \t%-15s %-10s \t%-4d \n", i + 1, list.get(i).getName(), list.get(i).getDate(), list.get(i).getPoints());
        }

        // Format: \033[<Text Color>;<Background Color>mText\033[0m

        System.out.println("\033[1;34mFirst line (Blue text)\033[0m");
        System.out.println("\033[1;32mSecond line (Green text)\033[0m");
        System.out.println("\033[1;31mThird line (Red text)\033[0m");

        // Highlight recurring elements with color
        System.out.println("Standard text \033[1;33mHighlight (Yellow)\033[0m Standard text");

        // Change background color
        System.out.println("\033[30;43mBlack text on yellow background\033[0m");
        System.out.println("\033[37;41mWhite text on red background\033[0m");

        System.out.println("\033[30;41mBlack text on red background\033[0m");
        System.out.println("\033[31;42mRed text on green background\033[0m");
        System.out.println("\033[32;43mGreen text on yellow background\033[0m");
        System.out.println("\033[33;44mYellow text on blue background\033[0m");
        System.out.println("\033[34;45mBlue text on magenta background\033[0m");
        System.out.println("\033[35;46mMagenta text on cyan background\033[0m");
        System.out.println("\033[36;47mCyan text on white background\033[0m");
        System.out.println("\033[37;40mWhite text on black background\033[0m");

        System.out.println("\033[90;101mLight gray text on light red\033[0m");
        System.out.println("\033[91;102mLight red on light green\033[0m");
        System.out.println("\033[92;103mLight green on light yellow\033[0m");
        System.out.println("\033[93;104mLight yellow on light blue\033[0m");
        System.out.println("\033[94;105mLight blue on light magenta\033[0m");
        System.out.println("\033[95;106mLight magenta on light cyan\033[0m");
        System.out.println("\033[96;107mLight cyan on white background\033[0m");
        System.out.println("\033[97;100mWhite text on light black\033[0m");

        System.out.println("\033[30;100mBlack text on light black\033[0m");
        System.out.println("\033[37;101mWhite text on light red\033[0m");
        System.out.println("\033[33;102mYellow text on light green\033[0m");
        System.out.println("\033[35;103mMagenta text on light yellow\033[0m");
        
        System.out.println("\033[30;46mBlack text on cyan background\033[0m");
        
        System.out.println("\033[30;106m Black text on light cyan background \033[0m");
        
        System.out.println("\033[90;106mDark gray text on light cyan background\033[0m");

    }

}
