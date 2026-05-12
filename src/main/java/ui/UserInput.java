package ui;

import java.util.Objects;
import java.util.Scanner;

public class UserInput {
    Scanner scanner = new Scanner(System.in);

    public String inputString() {
        String input = scanner.nextLine();
        if (input == null || input.isEmpty()) {
            throw new exceptionhandler.ValidationException("Input må ikke være tomt");
        } else {
            return input;
        }
    }

    public int inputInt(int limit) {
        int input = Integer.parseInt(scanner.nextLine());
        if (input > limit || input < 0) {
            throw new exceptionhandler.ValidationException("Input skal være et positivt tal under " + limit);
        }
        return input;
    }

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
