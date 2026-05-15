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
}