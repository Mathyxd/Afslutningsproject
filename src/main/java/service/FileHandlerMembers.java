package service;

import model.*;
import controller.MemberController;

import java.io.*;
import java.util.ArrayList;

public class FileHandlerMembers implements FileHandler {

    private static final String FILE_PATH = "csv/members.csv";
    private static final String DELIMITER = ";";
    private MemberController memberController;

    public FileHandlerMembers(MemberController memberController) {
        this.memberController = memberController;
    }

    // Læser alle medlemmer fra CSV-filen ved opstart
    public void loadFromFile() {
        long startTime = System.nanoTime();

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("members.csv ikke fundet – starter med tom liste.");
            return;
        }

        int loaded = 0;
        int errors = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                // Spring header-linjen over
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                if (line.isBlank()) continue;

                try {
                    Member member = parseLine(line);
                    memberController.addMember(member);
                    loaded++;
                } catch (Exception e) {
                    System.out.println("Fejl på linje: \"" + line + "\" -> " + e.getMessage());
                    errors++;
                }
            }
        } catch (IOException e) {
            System.out.println("Kunne ikke indlæse members.csv: " + e.getMessage());
        }

        long elapsed = System.nanoTime() - startTime;
        System.out.printf("Loaded %d medlemmer (%d fejl) på %.2f ms (buffered)%n", loaded, errors, elapsed / 1_000_000.0);
    }

    // Omdanner én CSV-linje til et Member-objekt
    private Member parseLine(String line) {
        String[] parts = line.split(DELIMITER, -1);

        if (parts.length < 5) {
            throw new IllegalArgumentException("For få kolonner (" + parts.length + ")");
        }

        int memberID         = Integer.parseInt(parts[0].trim());
        String name          = parts[1].trim();
        int age              = Integer.parseInt(parts[2].trim());
        boolean activeMember = Boolean.parseBoolean(parts[3].trim());
        String category      = parts[4].trim().toUpperCase();

        switch (category) {
            case "COMPETITIVE": {
                if (parts.length < 6 || parts[5].isBlank()) {
                    throw new IllegalArgumentException("COMPETITIVE-medlem mangler disciplin");
                }
                Discipline discipline = Discipline.valueOf(parts[5].trim().toUpperCase());
                CompetitiveMember m = new CompetitiveMember(name, age, memberID, activeMember, discipline);
                m.setMemberID(memberID);
                return m;
            }
            case "EXERCISE": {
                ExerciseMember m = new ExerciseMember(name, age, memberID, activeMember);
                m.setMemberID(memberID);
                return m;
            }
            default:
                throw new IllegalArgumentException("Ukendt kategori: " + category);
        }
    }

    // Gemmer alle medlemmer til CSV-filen
    public void saveToFile() {
        long startTime = System.nanoTime();

        File file = new File(FILE_PATH);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        ArrayList<Member> members = memberController.getAll();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Skriv header-linjen først
            writer.write("memberID" + DELIMITER + "name" + DELIMITER + "age" + DELIMITER
                    + "activeMember" + DELIMITER + "memberCategory" + DELIMITER + "discipline");
            writer.newLine();

            // Skriv ét medlem per linje
            for (Member m : members) {
                writer.write(formatMember(m));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Kunne ikke gemme members.csv: " + e.getMessage());
        }

        long elapsed = System.nanoTime() - startTime;
        System.out.printf("Gemt %d medlemmer på %.2f ms (buffered)%n", members.size(), elapsed / 1_000_000.0);
    }

    // Omdanner et Member-objekt til en CSV-linje
    private String formatMember(Member m) {
        String category  = (m instanceof CompetitiveMember) ? "COMPETITIVE" : "EXERCISE";
        String discipline = "";

        // Kun CompetitiveMember har en discipline
        if (m instanceof CompetitiveMember) {
            discipline = ((CompetitiveMember) m).getDiscipline().name();
        }

        return m.getMemberID() + DELIMITER
                + m.getName()        + DELIMITER
                + m.getAge()         + DELIMITER
                + m.isActiveMember() + DELIMITER
                + category           + DELIMITER
                + discipline;
    }
}