package controller;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Tests for TournamentController klassen
// Dækker: oprettelse af turnering, kamptilføjelse, opdatering af resultater og hentning af top 5
class TournamentControllerTest {

    private TournamentController controller;
    private CompetitiveMember seniorMember;
    private CompetitiveMember juniorMember;

    @BeforeEach
    void setUp() {
        controller = new TournamentController();
        seniorMember = new CompetitiveMember("Anders Jensen", 25, 1, true, Discipline.SINGLE);
        juniorMember = new CompetitiveMember("Sofie Hansen", 15, 2, true, Discipline.SINGLE);
    }

    // --- createTournament ---

    @Test
    void createTournamentAddsToMemberList() {
        controller.createTournament(seniorMember, "Danish Open", 3, 2, LocalDate.now(), Discipline.SINGLE);
        assertEquals(1, controller.getTournamentsForMember(seniorMember).size());
    }

    @Test
    void createTournamentHasCorrectName() {
        controller.createTournament(seniorMember, "Danish Open", 3, 2, LocalDate.now(), Discipline.SINGLE);
        Tournament tournament = controller.getTournamentsForMember(seniorMember).get(0);
        assertEquals("Danish Open", tournament.getTournamentName());
    }

    @Test
    void createTournamentHasCorrectTournamentRanking() {
        controller.createTournament(seniorMember, "Danish Open", 3, 2, LocalDate.now(), Discipline.SINGLE);
        Tournament tournament = controller.getTournamentsForMember(seniorMember).get(0);
        assertEquals(3, tournament.getTournamentRanking());
    }

    @Test
    void createTournamentHasCorrectPlayerPlacement() {
        controller.createTournament(seniorMember, "Danish Open", 3, 2, LocalDate.now(), Discipline.SINGLE);
        Tournament tournament = controller.getTournamentsForMember(seniorMember).get(0);
        assertEquals(2, tournament.getPlayerPlacement());
    }

    @Test
    void createTournamentHasEmptyMatchListOnCreation() {
        controller.createTournament(seniorMember, "Danish Open", 3, 2, LocalDate.now(), Discipline.SINGLE);
        Tournament tournament = controller.getTournamentsForMember(seniorMember).get(0);
        assertEquals(0, tournament.getMatches().size());
    }

    // --- addMatchToTournament ---

    @Test
    void addMatchToTournamentIncreasesMatchCount() {
        controller.createTournament(seniorMember, "Danish Open", 3, 2, LocalDate.now(), Discipline.SINGLE);
        Tournament tournament = controller.getTournamentsForMember(seniorMember).get(0);

        controller.addMatchToTournament(tournament, "Kvartfinale", "Lars Nielsen", "6-3, 6-4", true);
        assertEquals(1, tournament.getMatches().size());
    }

    @Test
    void addMultipleMatchesToTournament() {
        controller.createTournament(seniorMember, "Danish Open", 3, 2, LocalDate.now(), Discipline.SINGLE);
        Tournament tournament = controller.getTournamentsForMember(seniorMember).get(0);

        controller.addMatchToTournament(tournament, "Kvartfinale", "Lars Nielsen", "6-3, 6-4", true);
        controller.addMatchToTournament(tournament, "Semifinale", "Bent Olsen", "6-2, 6-1", true);
        controller.addMatchToTournament(tournament, "Finale", "Anders Jensen", "3-6, 4-6", false);

        assertEquals(3, tournament.getMatches().size());
    }

    // --- updateMatchResult ---

    @Test
    void updateMatchResultUpdatesScoreAndWon() {
        controller.createTournament(seniorMember, "Danish Open", 3, 2, LocalDate.now(), Discipline.SINGLE);
        Tournament tournament = controller.getTournamentsForMember(seniorMember).get(0);
        controller.addMatchToTournament(tournament, "Kvartfinale", "Lars Nielsen", "6-3, 6-4", true);

        Match match = tournament.getMatches().get(0);
        controller.updateMatchResult(match, "6-1, 6-0", false);

        assertEquals("6-1, 6-0", match.getScore());
        assertFalse(match.isWon());
    }

    // --- updateTournamentResult ---

    @Test
    void updateTournamentResultUpdatesCorrectly() {
        controller.createTournament(seniorMember, "Danish Open", 3, 2, LocalDate.now(), Discipline.SINGLE);
        Tournament tournament = controller.getTournamentsForMember(seniorMember).get(0);

        controller.updateTournamentResult(tournament, 1, 5);

        assertEquals(1, tournament.getPlayerPlacement());
        assertEquals(5, tournament.getTournamentRanking());
    }

    // --- getTournamentsForMember ---

    @Test
    void getTournamentsForMemberReturnsEmptyListWhenNoTournaments() {
        assertEquals(0, controller.getTournamentsForMember(seniorMember).size());
    }

    @Test
    void multipleMembersHaveSeparateTournaments() {
        controller.createTournament(seniorMember, "Danish Open", 3, 2, LocalDate.now(), Discipline.SINGLE);
        controller.createTournament(juniorMember, "Junior Cup", 1, 1, LocalDate.now(), Discipline.SINGLE);

        assertEquals(1, controller.getTournamentsForMember(seniorMember).size());
        assertEquals(1, controller.getTournamentsForMember(juniorMember).size());
    }

    // --- getRecentTournaments ---

    @Test
    void getRecentTournamentsReturnsOnlyTournamentsAfterStartDate() {
        // Tilføjer en gammel og en nylig turnering
        controller.createTournament(seniorMember, "Old Open", 3, 2, LocalDate.now().minusMonths(2), Discipline.SINGLE);
        controller.createTournament(seniorMember, "Danish Open", 3, 2, LocalDate.now(), Discipline.SINGLE);

        ArrayList<Tournament> recent = controller.getRecentTournaments(seniorMember, LocalDate.now().minusMonths(1));
        assertEquals(1, recent.size());
        assertEquals("Danish Open", recent.get(0).getTournamentName());
    }

    @Test
    void getRecentTournamentsReturnsEmptyListWhenNoneInPeriod() {
        controller.createTournament(seniorMember, "Old Open", 3, 2, LocalDate.now().minusMonths(2), Discipline.SINGLE);

        ArrayList<Tournament> recent = controller.getRecentTournaments(seniorMember, LocalDate.now().minusMonths(1));
        assertEquals(0, recent.size());
    }

    // --- getLatestTournaments ---

    @Test
    void getLatestTournamentsReturnsCorrectAmount() {
        controller.createTournament(seniorMember, "Danish Open", 3, 2, LocalDate.now().minusDays(10), Discipline.SINGLE);
        controller.createTournament(seniorMember, "Nordic Cup", 3, 1, LocalDate.now().minusDays(5), Discipline.SINGLE);
        controller.createTournament(seniorMember, "Copenhagen Open", 3, 3, LocalDate.now(), Discipline.SINGLE);

        // Henter kun de 2 seneste
        ArrayList<Tournament> latest = controller.getLatestTournaments(seniorMember, 2);
        assertEquals(2, latest.size());
    }

    @Test
    void getLatestTournamentsReturnsNewestFirst() {
        controller.createTournament(seniorMember, "Danish Open", 3, 2, LocalDate.now().minusDays(10), Discipline.SINGLE);
        controller.createTournament(seniorMember, "Nordic Cup", 3, 1, LocalDate.now(), Discipline.SINGLE);

        ArrayList<Tournament> latest = controller.getLatestTournaments(seniorMember, 2);
        assertEquals("Nordic Cup", latest.get(0).getTournamentName());
    }

    // --- getTotalWonMatches ---

    @Test
    void getTotalWonMatchesCountsCorrectly() {
        controller.createTournament(seniorMember, "Danish Open", 3, 2, LocalDate.now(), Discipline.SINGLE);
        Tournament tournament = controller.getTournamentsForMember(seniorMember).get(0);

        controller.addMatchToTournament(tournament, "Kvartfinale", "Lars Nielsen", "6-3, 6-4", true);
        controller.addMatchToTournament(tournament, "Semifinale", "Bent Olsen", "6-2, 6-1", true);
        controller.addMatchToTournament(tournament, "Finale", "Anders Jensen", "3-6, 4-6", false);

        assertEquals(2, controller.getTotalWonMatches(seniorMember));
    }

    @Test
    void getTotalWonMatchesReturnsZeroWhenNoMatches() {
        controller.createTournament(seniorMember, "Danish Open", 3, 2, LocalDate.now(), Discipline.SINGLE);
        assertEquals(0, controller.getTotalWonMatches(seniorMember));
    }

    // --- getTop5Players ---

    @Test
    void getTop5PlayersReturnsOnlySeniors() {
        controller.createTournament(seniorMember, "Danish Open", 3, 2, LocalDate.now(), Discipline.SINGLE);
        controller.createTournament(juniorMember, "Junior Cup", 1, 1, LocalDate.now(), Discipline.SINGLE);

        ArrayList<CompetitiveMember> top5 = controller.getTop5Players(Discipline.SINGLE, false);

        assertTrue(top5.contains(seniorMember));
        assertFalse(top5.contains(juniorMember));
    }

    @Test
    void getTop5PlayersReturnsOnlyJuniors() {
        controller.createTournament(seniorMember, "Danish Open", 3, 2, LocalDate.now(), Discipline.SINGLE);
        controller.createTournament(juniorMember, "Junior Cup", 1, 1, LocalDate.now(), Discipline.SINGLE);

        ArrayList<CompetitiveMember> top5 = controller.getTop5Players(Discipline.SINGLE, true);

        assertTrue(top5.contains(juniorMember));
        assertFalse(top5.contains(seniorMember));
    }

    @Test
    void getTop5PlayersReturnsMaxFivePlayers() {
        // Tilføjer 6 seniorspillere
        for (int i = 0; i < 6; i++) {
            CompetitiveMember player = new CompetitiveMember("Player " + i, 25, i + 3, true, Discipline.SINGLE);
            controller.createTournament(player, "Danish Open", 3, i + 1, LocalDate.now(), Discipline.SINGLE);
        }

        ArrayList<CompetitiveMember> top5 = controller.getTop5Players(Discipline.SINGLE, false);
        assertEquals(5, top5.size());
    }

    @Test
    void getTop5PlayersSortedByBestPlacement() {
        // Laveste placering = bedst
        CompetitiveMember bestPlayer = new CompetitiveMember("Best Player", 25, 3, true, Discipline.SINGLE);
        CompetitiveMember worstPlayer = new CompetitiveMember("Worst Player", 25, 4, true, Discipline.SINGLE);

        controller.createTournament(worstPlayer, "Danish Open", 3, 5, LocalDate.now(), Discipline.SINGLE);
        controller.createTournament(bestPlayer, "Danish Open", 3, 1, LocalDate.now(), Discipline.SINGLE);

        ArrayList<CompetitiveMember> top5 = controller.getTop5Players(Discipline.SINGLE, false);

        assertEquals(bestPlayer, top5.get(0));
        assertEquals(worstPlayer, top5.get(1));
    }
}