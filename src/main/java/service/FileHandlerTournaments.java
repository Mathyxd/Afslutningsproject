package service;
import controller.MemberController;
import controller.TournamentController;
import model.*;

import java.io.*;
import java.time.LocalDate;

public class FileHandlerTournaments implements FileHandler {

    private static final String FILE_PATH = "csv/tournaments.csv";
    private static final String DELIMITER = ";";
    private TournamentController tournamentController;
    private MemberController memberController;

    public FileHandlerTournaments(TournamentController tournamentController, MemberController memberController) {
        this.tournamentController = tournamentController;
        this.memberController = memberController;
    }

    public void loadFromFile() {
        long startTime = System.nanoTime();

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("tournaments.csv ikke fundet – starter med tom liste.");
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
                    parseLine(line);
                    loaded++;
                } catch (Exception e) {
                    System.out.println("Fejl på linje: \"" + line + "\" -> " + e.getMessage());
                    errors++;
                }
            }
        } catch (IOException e) {
            System.out.println("Kunne ikke indlæse tournaments.csv: " + e.getMessage());
        }
        long elapsed = System.nanoTime() - startTime;
        System.out.printf("Loaded %d turneringer (%d fejl) på %.2f ms (buffered)%n", loaded, errors, elapsed / 1_000_000.0);
    }

    private void parseLine(String line) {
        String[] parts = line.split(DELIMITER, -1);

        if (parts.length < 6) {
            throw new IllegalArgumentException("For få kolonner (" + parts.length + ")");
        }
        int memberID = Integer.parseInt(parts[0].trim());
        String tournamentName = parts[1].trim();
        int tournamentRanking = Integer.parseInt(parts[2].trim());
        int playerPlacement = Integer.parseInt(parts[3].trim());
        LocalDate date = LocalDate.parse(parts[4].trim());
        Discipline discipline = Discipline.valueOf(parts[5].trim().toUpperCase());

        Member member = memberController.findByID(memberID);
        if (member == null) {
            throw new IllegalArgumentException("Ingen medlem fundet med ID: " + memberID);
        }
        if (!(member instanceof CompetitiveMember)) {
            throw new IllegalArgumentException("Medlem med ID " + memberID + " er ikke en konkurrencespiller");
        }
        tournamentController.createTournament((CompetitiveMember) member, tournamentName, tournamentRanking, playerPlacement, date, discipline);
    }

    public void saveToFile() {
        long startTime = System.nanoTime();

        File file = new File(FILE_PATH);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        int saved = 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            writer.write("memberID" + DELIMITER + "tournamentName" + DELIMITER + "ranking"
                    + DELIMITER + "matchResult" + DELIMITER + "date" + DELIMITER + "discipline");
            writer.newLine();

            for (Tournaments tournaments : tournamentController.getAllTournaments()) {
                int memberID = tournaments.getMember().getMemberID();
                for (Tournament t : tournaments.getTournaments()) {
                    writer.write(formatTournament(memberID, t));
                    writer.newLine();
                    saved++;
                }
            }
        } catch (IOException e) {
            System.out.println("Kunne ikke gemme tournaments.csv: " + e.getMessage());
        }
        long elapsed = System.nanoTime() - startTime;
        System.out.printf("Gemt %d turneringer på %.2f ms (buffered)%n", saved, elapsed / 1_000_000.0);
    }

    private String formatTournament(int memberID, Tournament t) {
        return memberID + DELIMITER
                + t.getTournamentName() + DELIMITER
                + t.getTournamentRanking() + DELIMITER
                + t.getPlayerPlacement() + DELIMITER
                + t.getDate() + DELIMITER
                + t.getDiscipline().name();
    }
}