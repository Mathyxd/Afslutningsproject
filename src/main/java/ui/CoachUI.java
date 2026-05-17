package ui;

import controller.MemberController;
import controller.TournamentController;
import controller.TrainingController;
import model.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class CoachUI implements InterfaceUI {
    static UserInput userInput = new UserInput();
    static TournamentController tournamentController = new TournamentController();
    static TrainingController trainingController = new TrainingController();
    static MemberController memberController = new MemberController();

    public static void runProgram() {

        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\n=== COACH MENU ===");
            System.out.println("1. Se top 5 spillere");
            System.out.println("2. Se træningsresultater for spiller");
            System.out.println("3. Se turneringsresultater for spiller");
            System.out.println("4. Tilføj træningsevaluering");
            System.out.println("5. Opdater træningsevaluering");
            System.out.println("6. Tilføj turnering");
            System.out.println("7. Tilføj kamp til turnering");
            System.out.println("8. Forlad menu");

            int input = userInput.inputInt(8);

            switch (input) {
                case 1:
                    showTop5Players();
                    break;
                case 2:
                    showTrainingResults();
                    break;
                case 3:
                    showTournamentResults();
                    break;
                case 4:
                    addTrainingEvaluation();
                    break;
                case 5:
                    updateTrainingEvaluation();
                    break;
                case 6:
                    addTournament();
                    break;
                case 7:
                    addMatchToTournament();
                    break;
                case 8:
                    isRunning = false;
                    break;
            }
        }
    }

    /* Viser top 5 spillere inden for en disciplin fordelt på junior og senior */
    public static void showTop5Players() {
        System.out.println("\nVælg disciplin: 1. Single  2. Double  3. Mix Double");
        int disciplineInput = userInput.inputInt(3);
        Discipline discipline = getDiscipline(disciplineInput);

        System.out.println("\n=== TOP 5 JUNIORSPILLERE - " + discipline + " ===");
        ArrayList<CompetitiveMember> juniorTop5 = tournamentController.getTop5Players(discipline, true);
        if (juniorTop5.isEmpty()) {
            System.out.println("Ingen juniorspillere fundet.");
        } else {
            for (int i = 0; i < juniorTop5.size(); i++) {
                CompetitiveMember member = juniorTop5.get(i);
                ArrayList<Tournament> latest = tournamentController.getLatestTournaments(member, 1);

                // Sikrer mod tom liste hvis spilleren ikke har turneringer
                String placement = latest.isEmpty() ? "Ingen turneringer" :
                        String.valueOf(latest.get(0).getPlayerPlacement());

                System.out.println((i + 1) + ". " + member.getName() +
                        " - Bedste placering: " + placement);
            }
        }

        System.out.println("\n=== TOP 5 SENIORSPILLERE - " + discipline + " ===");
        ArrayList<CompetitiveMember> seniorTop5 = tournamentController.getTop5Players(discipline, false);
        if (seniorTop5.isEmpty()) {
            System.out.println("Ingen seniorspillere fundet.");
        } else {
            for (int i = 0; i < seniorTop5.size(); i++) {
                CompetitiveMember member = seniorTop5.get(i);
                ArrayList<Tournament> latest = tournamentController.getLatestTournaments(member, 1);

                // Sikrer mod tom liste hvis spilleren ikke har turneringer
                String placement = latest.isEmpty() ? "Ingen turneringer" :
                        String.valueOf(latest.get(0).getPlayerPlacement());

                System.out.println((i + 1) + ". " + member.getName() +
                        " - Bedste placering: " + placement);
            }
        }
    }

    /* Viser træningsresultater for en spiller de sidste 2 uger */
    public static void showTrainingResults() {
        CompetitiveMember member = findCompetitiveMember();
        if (member == null) return;

        System.out.println("\n=== TRÆNINGSRESULTATER - " + member.getName() + " ===");
        System.out.println("1. Sidste 2 uger");
        System.out.println("2. Selvvalgt periode");
        int input = userInput.inputInt(2);

        LocalDate startDate;
        if (input == 1) {
            startDate = LocalDate.now().minusWeeks(2);
        } else {
            System.out.println("Indtast startdato (antal dage tilbage):");
            int days = userInput.inputInt(365);
            startDate = LocalDate.now().minusDays(days);
        }

        ArrayList<Training> results = trainingController.getRecentTrainingScores(member, startDate);

        if (results.isEmpty()) {
            System.out.println("Ingen træningsresultater fundet i perioden.");
        } else {
            for (Training training : results) {
                System.out.println("Dato: " + training.getDate() +
                        " | Disciplin: " + training.getDiscipline() +
                        " | Score: " + training.getScore() + "/10" +
                        " | Kommentar: " + training.getCoachComment());
            }
            double avg = trainingController.getAverageScoreSince(member, startDate);
            System.out.printf("%nGennemsnitlig score: %.1f/10%n", avg);
        }
    }

    /* Viser turneringsresultater for en spiller den sidste måned */
    public static void showTournamentResults() {
        CompetitiveMember member = findCompetitiveMember();
        if (member == null) return;

        System.out.println("\n=== TURNERINGSRESULTATER - " + member.getName() + " ===");
        System.out.println("1. Sidste måned");
        System.out.println("2. De 2 seneste turneringer");
        System.out.println("3. Selvvalgt periode");
        int input = userInput.inputInt(3);

        switch (input) {
            case 1:
                printTournaments(tournamentController
                        .getRecentTournaments(member, LocalDate.now().minusMonths(1)));
                break;
            case 2:
                printTournaments(tournamentController
                        .getLatestTournaments(member, 2));
                break;
            case 3:
                System.out.println("Indtast startdato (antal dage tilbage):");
                int days = userInput.inputInt(365);
                printTournaments(tournamentController
                        .getRecentTournaments(member, LocalDate.now().minusDays(days)));
                break;
        }
    }

    /* Tilføjer en ny træningsevaluering for en spiller */
    public static void addTrainingEvaluation() {
        CompetitiveMember member = findCompetitiveMember();
        if (member == null) return;

        System.out.println("Vælg disciplin: 1. Single  2. Double  3. Mix Double");
        Discipline discipline = getDiscipline(userInput.inputInt(3));

        System.out.println("Indtast score (1-10):");
        int score = userInput.inputInt(10);

        // Validering af score
        if (score < 1) {
            System.out.println("Score skal være mellem 1 og 10.");
            return;
        }

        System.out.println("Indtast kommentar til udviklingspunkter:");
        String comment = userInput.inputString();

        trainingController.createTraining(member, discipline, score, LocalDate.now(), comment);
        System.out.println("Træningsevaluering tilføjet for " + member.getName());
    }

    /* Opdaterer en eksisterende træningsevaluering */
    public static void updateTrainingEvaluation() {
        CompetitiveMember member = findCompetitiveMember();
        if (member == null) return;

        ArrayList<Training> results = trainingController.getTrainingResultsForMember(member);

        if (results.isEmpty()) {
            System.out.println("Ingen træningsevalueringer fundet for " + member.getName());
            return;
        }

        System.out.println("\nVælg evaluering der skal opdateres:");
        for (int i = 0; i < results.size(); i++) {
            Training t = results.get(i);
            System.out.println((i + 1) + ". Dato: " + t.getDate() +
                    " | Score: " + t.getScore() + "/10" +
                    " | Kommentar: " + t.getCoachComment());
        }

        int choice = userInput.inputInt(results.size());
        Training training = results.get(choice - 1);

        System.out.println("Ny score (1-10):");
        int newScore = userInput.inputInt(10);

        // Validering af score
        if (newScore < 1) {
            System.out.println("Score skal være mellem 1 og 10.");
            return;
        }

        System.out.println("Ny kommentar:");
        String newComment = userInput.inputString();

        trainingController.updateScore(training, newScore, newComment);
        System.out.println("Træningsevaluering opdateret!");
    }

    /* Tilføjer en ny turnering for en spiller */
    public static void addTournament() {
        CompetitiveMember member = findCompetitiveMember();
        if (member == null) return;

        System.out.println("Turneringens navn:");
        String name = userInput.inputString();

        System.out.println("Turneringens rangering (prestige/niveau):");
        int tournamentRanking = userInput.inputInt(1000);

        System.out.println("Spillerens endelige placering:");
        int playerPlacement = userInput.inputInt(1000);

        System.out.println("Vælg disciplin: 1. Single  2. Double  3. Mix Double");
        Discipline discipline = getDiscipline(userInput.inputInt(3));

        tournamentController.createTournament(member, name, tournamentRanking,
                playerPlacement, LocalDate.now(), discipline);
        System.out.println("Turnering tilføjet for " + member.getName());
    }

    /* Tilføjer en kamp til en eksisterende turnering */
    public static void addMatchToTournament() {
        CompetitiveMember member = findCompetitiveMember();
        if (member == null) return;

        ArrayList<Tournament> tournaments = tournamentController.getTournamentsForMember(member);

        if (tournaments.isEmpty()) {
            System.out.println("Ingen turneringer fundet for " + member.getName());
            return;
        }

        System.out.println("\nVælg turnering:");
        for (int i = 0; i < tournaments.size(); i++) {
            System.out.println((i + 1) + ". " + tournaments.get(i).getTournamentName());
        }

        int choice = userInput.inputInt(tournaments.size());
        Tournament tournament = tournaments.get(choice - 1);

        System.out.println("Runde (fx Kvartfinale, Semifinale, Finale):");
        String round = userInput.inputString();

        System.out.println("Modstanderens navn:");
        String opponent = userInput.inputString();

        System.out.println("Score (fx 6-3, 6-4):");
        String score = userInput.inputString();

        System.out.println("Vandt spilleren?");
        boolean won = userInput.inputBool();

        tournamentController.addMatchToTournament(tournament, round, opponent, score, won);
        System.out.println("Kamp tilføjet til " + tournament.getTournamentName());
    }

    /* Hjælpemetode til at printe turneringer */
    private static void printTournaments(ArrayList<Tournament> tournaments) {
        if (tournaments.isEmpty()) {
            System.out.println("Ingen turneringer fundet.");
            return;
        }
        for (Tournament t : tournaments) {
            System.out.println("\nTurnering: " + t.getTournamentName() +
                    " | Disciplin: " + t.getDiscipline() +
                    " | Placering: " + t.getPlayerPlacement() +
                    " | Rangering: " + t.getTournamentRanking() +
                    " | Dato: " + t.getDate() +
                    " | Vundne kampe: " + t.getWonMatches() + "/" + t.getMatches().size());
        }
    }

    /* Hjælpemetode til at konvertere int til Discipline */
    private static Discipline getDiscipline(int input) {
        switch (input) {
            case 1: return Discipline.SINGLE;
            case 2: return Discipline.DOUBLE;
            default: return Discipline.MIX_DOUBLE;
        }
    }

    /* Hjælpemetode til at finde en konkurrencespiller via ID eller navn
       Returnerer null hvis spilleren ikke findes eller ikke er en konkurrencespiller */
    private static CompetitiveMember findCompetitiveMember() {
        System.out.println("\nSøg via: 1. ID  2. Navn");
        int input = userInput.inputInt(2);

        Member member;

        if (input == 1) {
            System.out.println("Indtast medlems-ID:");
            int id = userInput.inputInt(9999);
            member = memberController.findByID(id);
        } else {
            System.out.println("Indtast navn:");
            String name = userInput.inputString();
            member = memberController.findByName(name);
        }

        // Tjek om medlem findes
        if (member == null) {
            System.out.println("Ingen medlem fundet.");
            return null;
        }

        // Tjek om det er en konkurrencespiller
        if (!(member instanceof CompetitiveMember)) {
            System.out.println(member.getName() + " er ikke en konkurrencespiller.");
            return null;
        }

        return (CompetitiveMember) member;
    }
}