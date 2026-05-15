package controller;

import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Controller ansvarlig for håndtering af turneringer i systemet.
 * Håndterer oprettelse, opdatering og hentning af turneringsresultater.
 */
public class TournamentController {

    private final ArrayList<Tournaments> allTournaments = new ArrayList<>();

    /**
     * Opretter en ny turnering for en konkurrencespiller og tilføjer den til oversigten.
     *
     * @param member         spilleren der deltager i turneringen
     * @param tournamentName navnet på turneringen
     * @param ranking        spillerens rangering i turneringen
     * @param matchResult    kampresultatet
     * @param date           datoen for turneringen
     * @param discipline     disciplinen turneringen er i
     */
    public void createTournament(CompetitiveMember member, String tournamentName, int ranking, String matchResult, LocalDate date, Discipline discipline) {
        Tournament tournament = new Tournament(tournamentName, ranking, matchResult, date, discipline);
        getOrCreateTournaments(member).addTournament(tournament);
    }

    /**
     * Opdaterer kampresultatet for en eksisterende turnering.
     *
     * @param tournament  turneringen der skal opdateres
     * @param matchResult det nye kampresultat
     * @param ranking     den nye rangering
     */
    public void updateTournamentResult(Tournament tournament, String matchResult, int ranking) {
        tournament.setMatchResult(matchResult);
        tournament.setRanking(ranking);
    }

    /**
     * Henter alle turneringer for en specifik spiller.
     *
     * @param member spilleren hvis turneringer hentes
     * @return liste af turneringer for spilleren
     */
    public ArrayList<Tournament> getTournamentsForMember(CompetitiveMember member) {
        for (Tournaments t : allTournaments) {
            if (t.getMember().equals(member)) {
                return t.getTournaments();
            }
        }
        return new ArrayList<>();
    }

    /**
     * Henter top 5 spillere inden for en given disciplin fordelt på junior og senior.
     * Sorteret efter bedste rangering i turneringer.
     *
     * @param discipline disciplinen der sorteres efter
     * @param junior     true for juniorspillere, false for seniorspillere
     * @return liste af top 5 spillere i den givne disciplin
     */
    public ArrayList<CompetitiveMember> getTop5Players(Discipline discipline, boolean junior) {
        ArrayList<CompetitiveMember> players = new ArrayList<>();

        for (Tournaments t : allTournaments) {
            CompetitiveMember member = t.getMember();

            // Filtrer på junior/senior og disciplin
            if (member.isJunior() == junior && member.getDiscipline() == discipline) {
                players.add(member);
            }
        }

        // Sorter efter bedste rangering (laveste rangering = bedst)
        players.sort(Comparator.comparingInt(member ->
                getTournamentsForMember(member).stream()
                        .mapToInt(Tournament::getRanking)
                        .min()
                        .orElse(Integer.MAX_VALUE)));

        // Returner top 5
        return new ArrayList<>(players.subList(0, Math.min(5, players.size())));
    }

    /**
     * Henter eller opretter en turneringsoversigt for en spiller.
     * Bruges internt til at sikre hver spiller kun har én oversigt.
     *
     * @param member spilleren som oversigten tilhører
     * @return turneringsoversigten for spilleren
     */
    private Tournaments getOrCreateTournaments(CompetitiveMember member) {
        for (Tournaments t : allTournaments) {
            if (t.getMember().equals(member)) {
                return t;
            }
        }
        Tournaments newTournaments = new Tournaments(member);
        allTournaments.add(newTournaments);
        return newTournaments;
    }

    /**
     * Henter alle turneringsoversigter i systemet.
     *
     * @return liste af alle turneringsoversigter
     */
    public ArrayList<Tournaments> getAllTournaments() {
        return allTournaments;
    }
}