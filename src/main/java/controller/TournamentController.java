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
     * @param member              spilleren der deltager i turneringen
     * @param tournamentName      navnet på turneringen
     * @param tournamentRanking   turneringens prestige/niveau
     * @param playerPlacement     spillerens endelige placering i turneringen
     * @param date                datoen for turneringen
     * @param discipline          disciplinen turneringen er i
     */
    public void createTournament(CompetitiveMember member, String tournamentName, int tournamentRanking, int playerPlacement, LocalDate date, Discipline discipline) {
        Tournament tournament = new Tournament(tournamentName, tournamentRanking, playerPlacement, date, discipline);
        getOrCreateTournaments(member).addTournament(tournament);
    }

    /**
     * Tilføjer en kamp til en eksisterende turnering.
     *
     * @param tournament turneringen kampen tilføjes til
     * @param round      runden kampen blev spillet i (fx "Kvartfinale")
     * @param opponent   modstanderens navn
     * @param score      kampens score (fx "6-3, 6-4")
     * @param won        true hvis kampen blev vundet, false hvis tabt
     */
    public void addMatchToTournament(Tournament tournament, String round, String opponent, String score, boolean won) {
        Match match = new Match(round, opponent, score, won);
        tournament.addMatch(match);
    }

    /**
     * Opdaterer en eksisterende kamps resultat.
     *
     * @param match    kampen der skal opdateres
     * @param score    det nye kampresultat
     * @param won      true hvis kampen blev vundet, false hvis tabt
     */
    public void updateMatchResult(Match match, String score, boolean won) {
        match.setScore(score);
        match.setWon(won);
    }

    /**
     * Opdaterer en turneringens placering og rangering.
     *
     * @param tournament       turneringen der skal opdateres
     * @param playerPlacement  den nye spillerplacering
     * @param tournamentRanking den nye turneringsrangering
     */
    public void updateTournamentResult(Tournament tournament, int playerPlacement, int tournamentRanking) {
        tournament.setPlayerPlacement(playerPlacement);
        tournament.setTournamentRanking(tournamentRanking);
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
     * Henter turneringer for en spiller inden for en given periode.
     * Standard brug: LocalDate.now().minusMonths(1) for sidste måned.
     *
     * @param member    spilleren hvis turneringer hentes
     * @param startDate startdatoen for perioden
     * @return liste af turneringer inden for perioden
     */
    public ArrayList<Tournament> getRecentTournaments(CompetitiveMember member, LocalDate startDate) {
        ArrayList<Tournament> recentTournaments = new ArrayList<>();
        for (Tournament tournament : getTournamentsForMember(member)) {
            if (!tournament.getDate().isBefore(startDate)) {
                recentTournaments.add(tournament);
            }
        }
        return recentTournaments;
    }

    /**
     * Henter de seneste turneringer for en spiller baseret på antal.
     * Standard brug: antal = 2 for de 2 seneste turneringer.
     *
     * @param member spilleren hvis turneringer hentes
     * @param antal  antallet af seneste turneringer der hentes
     * @return liste af de seneste turneringer
     */
    public ArrayList<Tournament> getLatestTournaments(CompetitiveMember member, int antal) {
        ArrayList<Tournament> memberTournaments = getTournamentsForMember(member);

        // Sorter efter dato så nyeste kommer først
        memberTournaments.sort(Comparator.comparing(Tournament::getDate).reversed());

        // Returner kun det ønskede antal
        return new ArrayList<>(memberTournaments.subList(0, Math.min(antal, memberTournaments.size())));
    }

    /**
     * Henter top 5 spillere inden for en given disciplin fordelt på junior og senior.
     * Sorteret efter bedste placering i turneringer.
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

        // Sorter efter bedste placering (laveste placering = bedst)
        players.sort(Comparator.comparingInt(member ->
                getOrCreateTournaments(member).getBestPlacementByDiscipline(discipline)));

        // Returner top 5
        return new ArrayList<>(players.subList(0, Math.min(5, players.size())));
    }

    /**
     * Henter antal vundne kampe for en spiller på tværs af alle turneringer.
     *
     * @param member spilleren hvis vundne kampe tælles
     * @return antal vundne kampe
     */
    public int getTotalWonMatches(CompetitiveMember member) {
        int total = 0;
        for (Tournament tournament : getTournamentsForMember(member)) {
            total += tournament.getWonMatches();
        }
        return total;
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