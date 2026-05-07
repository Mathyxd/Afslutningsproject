package Controller;

import Model.Member;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class MemberController {
    // ArrayListe over medlemmer ligger her
    private ArrayList<Member> memberList = new ArrayList<>();
    private static int nextID = 1000;

    // ── OPRET Medlem

    public void addMember(Member member) {
        memberList.add(member);
        System.out.println("✓ Oprettet: " + member.getName()
                + " | ID: "   + member.getMemberID()
                + " | Type: " + member.getMemberType());
    }

    // ── FJERNER VIA MedlemsID

    public void removeMemberByID(int memberID) {
        Member found = findByID(memberID);

        if (found == null) {
            System.out.println("✗ Ingen medlem fundet med ID: " + memberID);
            return;
        }

        memberList.remove(found);
        System.out.println("✓ Fjernet: " + found.getName()
                + " (ID: " + memberID + ")");
    }

    // ── FJERN VIA NAVN

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

    // ── SØG
            //søger udfra MedlemsID
    public Member findByID(int memberID) {
        for (Member m : memberList) {
            if (m.getMemberID() == memberID) {
                return m;
            }
        }
        return null;
    }
            //søger udfra navn
    public Member findByName(String name) {
        for (Member m : memberList) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    // ── SORTERING
        //Sorter efter NAVN
    public ArrayList<Member> sortByName() {
        return memberList.stream()
                .sorted(Comparator.comparing(Member::getName))
                .collect(Collectors.toCollection(ArrayList::new));
    }
        //Sorter efter AlDER
    public ArrayList<Member> sortByAge() {
        return memberList.stream()
                .sorted(Comparator.comparingInt(Member::getAge))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    // ── VIS LISTE

    public void printAllMembers() {
        if (memberList.isEmpty()) {
            System.out.println("Ingen medlemmer registreret endnu.");
            return;
        }
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

    // ── HJÆLPEMETODER

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