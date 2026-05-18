package model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Repræsenterer en turnering som en konkurrencespiller har deltaget i.
 * Indeholder turneringsinfo, spillerens placering og alle kampe i turneringen.
 */
public class Tournament {
    private String tournamentName;
    private int tournamentRanking;  // turneringens prestige/niveau
    private int playerPlacement;    // spillerens endelige placering
    private LocalDate date;
    private Discipline discipline;
    private ArrayList<Match> matches;

    /**
     * Opretter en ny turnering for en konkurrencespiller.
     *
     * @param tournamentName     navnet på turneringen
     * @param tournamentRanking  turneringens prestige/niveau
     * @param playerPlacement    spillerens endelige placering i turneringen
     * @param date               datoen for turneringen
     * @param discipline         disciplinen turneringen er i
     */
    public Tournament(String tournamentName, int tournamentRanking, int playerPlacement, LocalDate date, Discipline discipline) {
        this.tournamentName = tournamentName;
        this.tournamentRanking = tournamentRanking;
        this.playerPlacement = playerPlacement;
        this.date = date;
        this.discipline = discipline;
        this.matches = new ArrayList<>();
    }

    /**
     * Tilføjer en kamp til turneringen.
     *
     * @param match kampen der skal tilføjes
     */
    public void addMatch(Match match) {
        matches.add(match);
    }

    /**
     * Fjerner en kamp fra turneringen.
     *
     * @param match kampen der skal fjernes
     */
    public void removeMatch(Match match) {
        matches.remove(match);
    }

    /**
     * Henter antal vundne kampe i turneringen.
     *
     * @return antal vundne kampe
     */
    public int getWonMatches() {
        int count = 0;
        for (Match match : matches) {
            if (match.isWon()) {
                count++;
            }
        }
        return count;
    }

    // Getters
    public String getTournamentName() { return tournamentName; }
    public int getTournamentRanking() { return tournamentRanking; }
    public int getPlayerPlacement() { return playerPlacement; }
    public LocalDate getDate() { return date; }
    public Discipline getDiscipline() { return discipline; }
    public ArrayList<Match> getMatches() { return matches; }

    // Setters
    public void setTournamentName(String tournamentName) { this.tournamentName = tournamentName; }
    public void setTournamentRanking(int tournamentRanking) { this.tournamentRanking = tournamentRanking; }
    public void setPlayerPlacement(int playerPlacement) { this.playerPlacement = playerPlacement; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setDiscipline(Discipline discipline) { this.discipline = discipline; }
}