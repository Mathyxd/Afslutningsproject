package model;

import java.util.ArrayList;
import java.util.EnumSet;

/**
 * Repræsenterer en oversigt over turneringer for en konkurrencespiller.
 * Fungerer som en samling af Tournament objekter tilknyttet én spiller.
 * Indeholder metoder til at hente placeringer og turneringshistorik.
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

    /**
     * Henter turneringer for en specifik disciplin.
     *
     * @param discipline disciplinen der filtreres på
     * @return liste af turneringer i den givne disciplin
     */
    public ArrayList<Tournament> getTournamentsByDiscipline(Discipline discipline) {
        ArrayList<Tournament> result = new ArrayList<>();
        for (Tournament tournament : tournaments) {
            if (tournament.getDiscipline() == discipline) {
                result.add(tournament);
            }
        }
        return result;
    }

    /**
     * Henter spillerens bedste placering på tværs af alle turneringer.
     * Laveste placering = bedst (1. plads er bedre end 3. plads)
     *
     * @return bedste placering eller 0 hvis ingen turneringer findes
     */
    public int getBestPlacement() {
        return tournaments.stream()
                .mapToInt(Tournament::getPlayerPlacement)
                .min()
                .orElse(0);
    }

     /**
     * Henter spillerens bedste placering inden for en specifik disciplin.*
     * @param discipline disciplinen der filtreres på
     * @return bedste placering i disciplinen eller 0 hvis ingen turneringer findes
     **/
    public int getBestPlacementByDiscipline(Discipline discipline) {
        return getTournamentsByDiscipline(discipline).stream()
                .mapToInt(Tournament::getPlayerPlacement)
                .min()
                .orElse(0);
    }


    // Getters
    public CompetitiveMember getMember() { return member; }
    public ArrayList<Tournament> getTournaments() { return tournaments; }
}