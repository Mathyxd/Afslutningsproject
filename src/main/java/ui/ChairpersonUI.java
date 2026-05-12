package ui;

import controller.MemberController;
import model.*;
import java.util.*;
import static validation.InputValidator.*;

public class ChairpersonUI implements InterfaceUI{
    static UserInput userInput = new UserInput();
    static MemberController memberController = new MemberController();

    public static void runProgram() {

        System.out.println("Vælg en mulighed. 1. Vis alle medlemmer. 2. Tilføj et medlem. 3. Ændr et medlem.");

        int limit = 3; // højeste tal, der er legitimt at indtaste ift hvilke cases switchen har.
        int input = userInput.inputInt(limit); // bruger UserInput klassen til at tage imod input

        switch (input){
            case 1: // Vis medlemmer
                memberController.printAllMembers();
                break;
            case 2: // tilføj medlem

                /* For at tilføje et medlem, kaldes først createMember() hvor bruger input hentes og ligges i
                   et member objekt. Herefter kaldes addMember() i MemberController klassen, hvor member objektet
                   tilføjes til en ArrayList<Member> */

                Member member = createMember();
                memberController.addMember(member);
                break;
            case 3: // Ændr et medlem
                break;


        }
    }

    /* metode, der opretter et medlem. Input fra brugeren tages imod af UserInput klassen, sådan at vi kun
       skal lave den pågældende type exception handling et enkelt sted. */

    public static Member createMember(){

        System.out.println("Navn?");
        String name = userInput.inputString();
        validateName(name);

        System.out.println("Alder?");
        int limit = 120;
        int age = userInput.inputInt(limit);
        validateAge(age);

        System.out.println("Har medlemmet et aktivt medlemskab, ja eller nej?");
        boolean isActive = userInput.inputBool();

        System.out.println("Er medlemmet en konkurrencespiller?");
        boolean isCompetitve = userInput.inputBool();

        if (isCompetitve){
            System.out.println("Hvilken disciplin spiller konkurrencespilleren? Single, double eller mix double.");
            Discipline discipline = Discipline.DOUBLE; // TEMPORARY! Det skal ÆNDRES!

            /* Oprettelse af konkurrencespiller. ID bør oprettes automatisk af konstruktøren i stedet for at
            specificeres. Det skal også ÆNDRES! */
            return new CompetitiveMember(name, age, 123, isActive, discipline);
        } else {

            // oprettelse af motionist. ID bør oprettes af konstruktøren. Det her skal ÆNDRES
            return new ExerciseMember(name, age, 123, isActive);
        }




    }
}
