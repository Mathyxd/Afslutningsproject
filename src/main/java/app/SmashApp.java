package app;

import ui.*;

public class SmashApp {
    static UserInput userInput = new UserInput();

    static void main() {

        // menu kan i fremtiden skrives pænere ud
        System.out.println("Vil du se mulighederne for 1. formanden, 2. kassereren eller 3. træneren?");

        int limit = 3;
        int input = userInput.inputInt(limit);

        switch (input){
            case 1:
                ChairpersonUI.runProgram();
                break;
            case 2:
                TreasureUI.runProgram();
                break;
            case 3:
                CoachUI.runProgram();
                break;
        }

    }
}
