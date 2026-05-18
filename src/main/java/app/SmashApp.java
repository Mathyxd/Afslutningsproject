package app;

import ui.*;

public class SmashApp {
    static UserInput userInput = new UserInput();

    static void main() {

        // menu kan i fremtiden skrives pænere ud
        System.out.println("Vil du se mulighederne for 1. formanden, 2. kassereren eller 3. træneren?");

        int input = userInput.inputInt(3);

        InterfaceUI ui = null;

        while (ui == null) {

            switch (input) {
                case 1:
                    ui = new ChairpersonUI();
                    break;
                case 2:
                    ui = new TreasureUI();
                    break;
                case 3:
                    ui = new CoachUI();
                    break;
            }
        }

            ui.runProgram();

    }
}