package app;

import ui.*;

import java.util.Scanner;

public class SmashApp {
    static void main() {

        System.out.println("Vil du se mulighederne for 1. formanden, 2. kassereren eller 3. træneren?");
        Scanner scanner = new Scanner(System.in);
        int input = Integer.parseInt(scanner.nextLine());

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
