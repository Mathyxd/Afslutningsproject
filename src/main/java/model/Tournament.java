package model;

import java.time.LocalDate;

/**
 * Repræsenterer en turnering som en konkurrencespiller har deltaget i.
 */
public class Tournament {
    private String tournamentName;
    private int ranking;
    private String matchResult;
    private LocalDate date;
    private Discipline discipline;

    /**
     * Opretter en ny turnering for en konkurrencespiller.
     *
     * @param tournamentName navnet på turneringen
     * @param ranking        spillerens rangering i turneringen
     * @param matchResult    kampresultatet
     * @param date           datoen for turneringen
     * @param discipline     disciplinen turneringen er i
     */
    public Tournament(String tournamentName, int ranking, String matchResult, LocalDate date, Discipline discipline) {
        this.tournamentName = tournamentName;
        this.ranking = ranking;
        this.matchResult = matchResult;
        this.date = date;
        this.discipline = discipline;
    }

    // Getters
    public String getTournamentName() { return tournamentName; }
    public int getRanking() { return ranking; }
    public String getMatchResult() { return matchResult; }
    public LocalDate getDate() { return date; }
    public Discipline getDiscipline() { return discipline; }

    // Setters
    public void setTournamentName(String tournamentName) { this.tournamentName = tournamentName; }
    public void setRanking(int ranking) { this.ranking = ranking; }
    public void setMatchResult(String matchResult) { this.matchResult = matchResult; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setDiscipline(Discipline discipline) { this.discipline = discipline; }
}