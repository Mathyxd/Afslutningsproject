package model;

import java.util.ArrayList;

/**
 * Repræsenterer en oversigt over turneringer for en konkurrencespiller.
 * Fungerer som en samling af Tournament objekter tilknyttet én spiller.
 */
public class Tournaments {
    private CompetitiveMember member;
    private ArrayList<Tournament> tournaments;

    /**
     * Opretter en ny turneringsoversigt for en konkurrencespiller.
     *
     * @param member spilleren som turneringsoversigten tilhører
     */
    public Tournaments(CompetitiveMember member) {
        this.member = member;
        this.tournaments = new ArrayList<>();
    }

    // Getters
    public CompetitiveMember getMember() { return member; }
    public ArrayList<Tournament> getTournaments() { return tournaments; }

    /**
     * Tilføjer en ny turnering til oversigten.
     *
     * @param tournament turneringen der skal tilføjes
     */
    public void addTournament(Tournament tournament) {
        tournaments.add(tournament);
    }

    /**
     * Fjerner en turnering fra oversigten.
     *
     * @param tournament turneringen der skal fjernes
     */
    public void removeTournament(Tournament tournament) {
        tournaments.remove(tournament);
    }
}