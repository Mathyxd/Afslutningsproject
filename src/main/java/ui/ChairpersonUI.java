package ui;

import controller.MemberController;
import model.*;
import service.*;

import java.util.*;

import static validation.InputValidator.*;

public class ChairpersonUI implements InterfaceUI {
    static UserInput userInput = new UserInput();
    static MemberController memberController = new MemberController();
    static FileHandlerMembers fileHandlerMembers = new FileHandlerMembers(memberController);

    public static void runProgram() {
        fileHandlerMembers.loadFromFile();
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Vælg en mulighed. 1. Vis alle medlemmer. 2. Tilføj et medlem. 3. Ændr et medlem.");

            /* bruger UserInput klassen til at tage imod input. Tallet det får med er det højeste tal,
            der i denne kontekst kan bruges til noget. Så pt 4, fordi der er 4 menupunkter */

            int input = userInput.inputInt(4);

            switch (input) {
                case 1: // Vis medlemmer
                    memberController.printAllMembers();
                    break;
                case 2: // tilføj medlem

                /* For at tilføje et medlem, kaldes først createMember() hvor bruger input hentes og ligges i
                   et member objekt. Herefter kaldes addMember() i MemberController klassen, hvor member objektet
                   tilføjes til en ArrayList<Member> */

                    Member member = createMember();
                    memberController.addMember(member);
                    fileHandlerMembers.saveToFile();
                    break;
                case 3: // Ændr et medlem
                    changeMember();
                    break;
                case 4: // lukker program
                    isRunning = false;
                    break;

            }

        }
    }

    /* metode, der opretter et medlem. Input fra brugeren tages imod af UserInput klassen, sådan at vi kun
       skal lave den pågældende type exception handling et enkelt sted. */

    public static Member createMember() {

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

        if (isCompetitve) {
            System.out.println("Hvilken disciplin spiller konkurrencespilleren? Single, double eller mix double.");
            Discipline discipline = Discipline.DOUBLE; // TEMPORARY! Det skal ÆNDRES!

            /* Oprettelse af konkurrencespiller. */
            return new CompetitiveMember(name, age, MemberController.generateID(), isActive, discipline);
        } else {
            // oprettelse af motionist.
            return new ExerciseMember(name, age, MemberController.generateID(), isActive);
        }


    }

    public static void changeMember() {
        System.out.println("Hvilket medlem vil du gerne ændre?");
        int memberID = userInput.inputInt(2000); // UserInput håndterer at tage et input fra scanneren
        validatID(memberID); // tjekker om ID'et er et, der potentielt kunne eksisterer (ie mindst 1000)
        Member member = memberController.findByID(memberID); // Søger efter et medlem med det givne ID

        if (member == null) {
            System.out.println("Der kunne ikke findes et medlem med ID: " + memberID);
        } else {
            boolean running = true;
            while (running) {
                System.out.println("Hvad vil du ændre ved " + member + " ?");
                System.out.println("1. Navn" +
                        " 2. Alder" +
                        " 3. Status (passiv/aktiv)" +
                        " 4. Type (Motionist/Konkurrencespiller)" +
                        " 5. Disciplin (Single/Double/Mix Double)" +
                        " 6. Forlad menu");

                int input = userInput.inputInt(6);
                switch (input) {
                    case 1:
                        memberController.changeName(member);
                        break;
                    case 2:
                        memberController.changeAge(member);
                        break;
                    case 3:
                        memberController.changeStatus(member);
                        break;
                    case 4:
                        memberController.changeType(member);
                        break;
                    case 5:
                        // discipline
                    case 6:
                        running = false;
                }
            }
        }
    }
}
