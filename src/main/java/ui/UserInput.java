package ui;

import java.util.Objects;
import java.util.Scanner;

public class UserInput {
    Scanner scanner = new Scanner(System.in);

    // scanneren tager imod en String og tjekker, om den opfylder de krav, alle Strings fra scanneren skal
    // (indtil videre at de ikke er nuLl) og smider enten en exception eller returneer inputtet
    public String inputString() {
        String input = scanner.nextLine();
        if (input == null || input.isEmpty()) {
            throw new exceptionhandler.ValidationException("Input må ikke være tomt");
        } else {
            return input;
        }
    }

    // scanneren tager imod et input i form af en int og tjekker den mod den int limit, metoden tager imod.
    // hvis tallet er under nul, nul eller over limit, smides der en exception. Ellers returnerer den inputtet
    public int inputInt(int limit) {
        int input = Integer.parseInt(scanner.nextLine());
        if (input > limit || input < 0) {
            throw new exceptionhandler.ValidationException("Input skal være et positivt tal under " + limit);
        }
        return input;
    }

    // scanneren tager imod string'en "ja" eller "nej". Hvis den tagerimod noget andet, smides en exception.
    // Hvis der skrives ja, returneres true og hvis der skrives nej returneres false.
    public Boolean inputBool(){
        String input = scanner.nextLine().toLowerCase();
        if (Objects.equals(input, "ja")){
            return true;
        } else if (Objects.equals(input, "nej")){
            return false;
        } else {
            throw new exceptionhandler.ValidationException("Input skal være ja eller nej");
        }
    }
}
