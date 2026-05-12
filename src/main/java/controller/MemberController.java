package controller;

import model.CompetitiveMember;
import model.Discipline;
import model.ExerciseMember;
import model.Member;
import ui.UserInput;

import java.util.ArrayList;

import static validation.InputValidator.validateAge;
import static validation.InputValidator.validateName;

public class MemberController {
    // ArrayListe over medlemmer ligger her
    private ArrayList<Member> memberList = new ArrayList<>();
    private static int nextID = 1000;
    UserInput userInput;

    // Opret Medlem

    public void addMember(Member member) {
        memberList.add(member);
        System.out.println(" Oprettet: " + member.getName()
                + " | ID: "   + member.getMemberID()
                + " | Type: " + member.getMemberType());
    }

    // Fjerner via MedlemsID

    public void removeMemberByID(int memberID) {
        Member found = findByID(memberID);

        if (found == null) {
            System.out.println(" Ingen medlem fundet med ID: " + memberID);
            return;
        }

        memberList.remove(found);
        System.out.println(" Fjernet: " + found.getName()
                + " (ID: " + memberID + ")");
    }

    // Fjerner via medlemmetsnavn

    public void removeMemberByName(String name) {
        Member found = findByName(name);

        if (found == null) {
            System.out.println("Ingen medlem fundet med navn: " + name);
            return;
        }

        memberList.remove(found);
        System.out.println("Fjernet: " + found.getName()
                + " (ID: " + found.getMemberID() + ")");
    }

    // Søgning
            //søgning udfra MedlemsID
    public Member findByID(int memberID) {
        for (Member m : memberList) {
            if (m.getMemberID() == memberID) {
                return m;
            }
        }
        return null;
    }
            // Søgning udfra navn
    public Member findByName(String name) {
        for (Member m : memberList) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    // Ændring af et eksisterende medlem
    public void changeName(Member member){
        String name = userInput.inputString();
        validateName(name);
        System.out.println("Vil du ændrer navnet fra " + member.getName() + " til " + name + " ?");
        boolean confirm = userInput.inputBool();
        if (confirm) {
            member.setName(name);
            System.out.println(member.getMemberID() + " hedder nu " + member.getName());
        } else {
            System.out.println("Navnet blev ikke ændret.");
        }
    }

    public void changeAge(Member member){
        int age = userInput.inputInt(120);
        validateAge(age);
        System.out.println("Vil du gerne ændre alder fra " + member.getAge() + " til " + age + " ?");
        boolean confirm = userInput.inputBool();
        if (confirm) {
            member.setAge(age);
            System.out.println(member.getMemberID() + "s alder er nu " + member.getAge());
        } else {
            System.out.println("Alderen blev ikke ændret.");
        }
    }

    public void changeStatus(Member member){
        boolean status = member.isActiveMember();
        member.setActiveMember(!status); // sætter boolean til det omvendte af hvad den var før.
        System.out.println(member.getName() + "s status ændret til " + member.isActiveMember());
    }

    // opretter et nyt Member, med de samme værdier som det eksisterende. Herefter fjernes det gamel og det
    // nye tilføjes. Ved konkurrencespiller skal disciplin også tilføjes.

    public void changeType(Member member){
        if (member instanceof ExerciseMember){

            System.out.println("Hvilken disciplin spiller konkurrencespilleren? Single, double eller mix double.");
            Discipline discipline = Discipline.DOUBLE; // TEMPORARY! Det skal ÆNDRES!

            Member changedMember = new CompetitiveMember(member.getName(), member.getAge(),
                    member.getMemberID(), member.isActiveMember(), discipline);

            removeMemberByID(member.getMemberID());
            addMember(changedMember);
            System.out.println("Medlemstype ændret til konkurrencespiller.");

        } else if (member instanceof CompetitiveMember){

            Member changedMember = new ExerciseMember(member.getName(), member.getAge(),
                                         member.getMemberID(), member.isActiveMember());

            removeMemberByID(member.getMemberID());
            addMember(changedMember);
            System.out.println("Medlemstype ændret til motionist.");
        }
    }

    // ── Vis Medlemsliste

    public void printAllMembers() {
        if (memberList.isEmpty()) {
            System.out.println("Ingen medlemmer registreret endnu.");
            return;
        } else {

            System.out.println("\n── Medlemsliste (" + memberList.size() + " medlemmer) ──");
            System.out.printf("%-6s %-20s %-6s %-15s %-8s%n",
                    "ID", "Navn", "Alder", "Type", "Aktiv");
            System.out.println("─".repeat(58));
            for (Member m : memberList) {
                System.out.printf("%-6d %-20s %-6d %-15s %-8s%n",
                        m.getMemberID(),
                        m.getName(),
                        m.getAge(),
                        m.getMemberType(),
                        m.isActiveMember() ? "Ja" : "Nej");
            }
        }
        // Brugen af ("%-6s %-20s %-6s %-15s %-8s%n". får medlemslisten til at se ud som følgende,
        /* ID     Navn               Alder      Type        Aktiv
──────────────────────────────────────────────────────────
          1000   Anna Jensen          16     JUNIOR_ACTIVE   Ja
          1001   Peter Madsen         34     SENIOR_ACTIVE   Ja
          1002   Lone Christensen     67     PASSIVE         Nej

         */
    }

    // Hjælpemetoder

    public static int generateID() {
        return nextID++;
    }

    public ArrayList<Member> getAll() {
        return memberList;
    }

    public int size() {
        return memberList.size();
    }
}