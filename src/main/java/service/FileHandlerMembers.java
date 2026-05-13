package service;

import model.*;
import controller.MemberController;

import java.io.*;
import java.util.ArrayList;

public class FileHandlerMembers implements FileHandler {
    private static final String FILE_PATH = "src/main/java/csv/members/members.csv";
    private static final String DELIMITER = ";";
    private MemberController memberController;


    public FileHandlerMembers(MemberController memberController) {
        this.memberController = memberController;
    }

    public void loadFromFile() throws IOException {
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
                    System.out.println("Fejl på linje: \"" + line + "\" ->" + e.getMessage());
                    errors++;
                }
            }
        }
        long elapsed = System.nanoTime() - startTime;
        System.out.printf("loaded %d medlemmer (%d fejl) på %.2f ms (buffered)%n", loaded, errors, elapsed / 1_000_000.0);
    }

    private Member parseLine(String line) {
        String[] parts = line.split(DELIMITER, -1);

        if (parts.length < 5) {
            throw new IllegalArgumentException("For få kolonner (" + parts.length + ")");
        }
        int    memberID      = Integer.parseInt(parts[0].trim());
        String name          = parts[1].trim();
        int    age           = Integer.parseInt(parts[2].trim());
        boolean activeMember = Boolean.parseBoolean(parts[3].trim());
        String category      = parts[4].trim().toUpperCase();

        switch (category) {
            case "COMPETITIVE": {
                if (parts.length < 6 || parts[5].isBlank()) {
                    throw new IllegalArgumentException("COMPETITIVE-medlem mangler disciplin");
                }
                Discipline discipline = Discipline.valueOf(parts[5].trim().toUpperCase());
                return new CompetitiveMember(name, age, memberID, activeMember, discipline);
            }
            case "EXERCISE": {
                return new ExerciseMember(name, age, memberID, activeMember);
            }
            default:
                throw new IllegalArgumentException("Ukendt kategori: " + category);
        }
    }
}