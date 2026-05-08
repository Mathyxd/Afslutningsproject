package controller;

import model.Member;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class MemberController {
    // ArrayListe over medlemmer ligger her
    private ArrayList<Member> memberList = new ArrayList<>();
    private static int nextID = 1000;

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

    // SORTERING
        //Sorter efter navn
    public ArrayList<Member> sortByName() {
        return memberList.stream()
                .sorted(Comparator.comparing(Member::getName))
                .collect(Collectors.toCollection(ArrayList::new));
    }
        //Sorter efter alder
    public ArrayList<Member> sortByAge() {
        return memberList.stream()
                .sorted(Comparator.comparingInt(Member::getAge))
                .collect(Collectors.toCollection(ArrayList::new));
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