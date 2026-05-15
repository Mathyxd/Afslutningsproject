package controller;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Dækker: oprettelse af turnering, opdatering af resultater og hentning af top 5
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

    @Test
    void createTournamentAddsToMemberList() {
        controller.createTournament(seniorMember, "Danish Open", 3, "6-3, 6-4", LocalDate.now(), Discipline.SINGLE);
        assertEquals(1, controller.getTournamentsForMember(seniorMember).size());
    }

    @Test
    void createTournamentHasCorrectName() {
        controller.createTournament(seniorMember, "Danish Open", 3, "6-3, 6-4", LocalDate.now(), Discipline.SINGLE);
        Tournament tournament = controller.getTournamentsForMember(seniorMember).get(0);
        assertEquals("Danish Open", tournament.getTournamentName());
    }

    @Test
    void createTournamentHasCorrectRanking() {
        controller.createTournament(seniorMember, "Danish Open", 3, "6-3, 6-4", LocalDate.now(), Discipline.SINGLE);
        Tournament tournament = controller.getTournamentsForMember(seniorMember).get(0);
        assertEquals(3, tournament.getRanking());
    }

    @Test
    void updateTournamentResultUpdatesCorrectly() {
        controller.createTournament(seniorMember, "Danish Open", 3, "6-3, 6-4", LocalDate.now(), Discipline.SINGLE);
        Tournament tournament = controller.getTournamentsForMember(seniorMember).get(0);

        controller.updateTournamentResult(tournament, "6-1, 6-0", 1);

        assertEquals("6-1, 6-0", tournament.getMatchResult());
        assertEquals(1, tournament.getRanking());
    }

    @Test
    void getTournamentsForMemberReturnsEmptyListWhenNoTournaments() {
        assertEquals(0, controller.getTournamentsForMember(seniorMember).size());
    }

    @Test
    void multipleMembersHaveSeparateTournaments() {
        controller.createTournament(seniorMember, "Danish Open", 3, "6-3, 6-4", LocalDate.now(), Discipline.SINGLE);
        controller.createTournament(juniorMember, "Junior Cup", 1, "6-2, 6-1", LocalDate.now(), Discipline.SINGLE);

        assertEquals(1, controller.getTournamentsForMember(seniorMember).size());
        assertEquals(1, controller.getTournamentsForMember(juniorMember).size());
    }

    @Test
    void getTop5PlayersReturnsOnlySeniors() {
        controller.createTournament(seniorMember, "Danish Open", 3, "6-3, 6-4", LocalDate.now(), Discipline.SINGLE);
        controller.createTournament(juniorMember, "Junior Cup", 1, "6-2, 6-1", LocalDate.now(), Discipline.SINGLE);

        ArrayList<CompetitiveMember> top5 = controller.getTop5Players(Discipline.SINGLE, false);

        assertTrue(top5.contains(seniorMember));
        assertFalse(top5.contains(juniorMember));
    }

    @Test
    void getTop5PlayersReturnsOnlyJuniors() {
        controller.createTournament(seniorMember, "Danish Open", 3, "6-3, 6-4", LocalDate.now(), Discipline.SINGLE);
        controller.createTournament(juniorMember, "Junior Cup", 1, "6-2, 6-1", LocalDate.now(), Discipline.SINGLE);

        ArrayList<CompetitiveMember> top5 = controller.getTop5Players(Discipline.SINGLE, true);

        assertTrue(top5.contains(juniorMember));
        assertFalse(top5.contains(seniorMember));
    }

    @Test
    void getTop5PlayersReturnsMaxFivePlayers() {
        // Tilføj 6 seniorspillere
        for (int i = 0; i < 6; i++) {
            CompetitiveMember player = new CompetitiveMember("Player " + i, 25, i + 3, true, Discipline.SINGLE);
            controller.createTournament(player, "Danish Open", i + 1, "6-3, 6-4", LocalDate.now(), Discipline.SINGLE);
        }

        ArrayList<CompetitiveMember> top5 = controller.getTop5Players(Discipline.SINGLE, false);
        assertEquals(5, top5.size());
    }

    @Test
    void getTop5PlayersSortedByBestRanking() {
        CompetitiveMember bestPlayer = new CompetitiveMember("Best Player", 25, 3, true, Discipline.SINGLE);
        CompetitiveMember worstPlayer = new CompetitiveMember("Worst Player", 25, 4, true, Discipline.SINGLE);

        controller.createTournament(worstPlayer, "Danish Open", 5, "6-3, 6-4", LocalDate.now(), Discipline.SINGLE);
        controller.createTournament(bestPlayer, "Danish Open", 1, "6-0, 6-0", LocalDate.now(), Discipline.SINGLE);

        ArrayList<CompetitiveMember> top5 = controller.getTop5Players(Discipline.SINGLE, false);

        assertEquals(bestPlayer, top5.get(0));
        assertEquals(worstPlayer, top5.get(1));
    }
}