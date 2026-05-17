package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

// Tests for Tournament klassen
// Dækker: korrekt attribut registrering, kamptilføjelse og opdatering
class TournamentTest {

    private Tournament tournament;

    @BeforeEach
    void setUp() {
        tournament = new Tournament("Danish Open", 3, 2, LocalDate.of(2026, 3, 15), Discipline.SINGLE);
    }

    // --- Oprettelse ---

    @Test
    void tournamentNameIsSetCorrectlyOnCreation() {
        assertEquals("Danish Open", tournament.getTournamentName());
    }

    @Test
    void tournamentRankingIsSetCorrectlyOnCreation() {
        assertEquals(3, tournament.getTournamentRanking());
    }

    @Test
    void playerPlacementIsSetCorrectlyOnCreation() {
        assertEquals(2, tournament.getPlayerPlacement());
    }

    @Test
    void dateIsSetCorrectlyOnCreation() {
        assertEquals(LocalDate.of(2026, 3, 15), tournament.getDate());
    }

    @Test
    void disciplineIsSetCorrectlyOnCreation() {
        assertEquals(Discipline.SINGLE, tournament.getDiscipline());
    }

    @Test
    void matchesIsEmptyOnCreation() {
        assertEquals(0, tournament.getMatches().size());
    }

    // --- Setters ---

    @Test
    void setTournamentRankingUpdatesCorrectly() {
        tournament.setTournamentRanking(1);
        assertEquals(1, tournament.getTournamentRanking());
    }

    @Test
    void setPlayerPlacementUpdatesCorrectly() {
        tournament.setPlayerPlacement(1);
        assertEquals(1, tournament.getPlayerPlacement());
    }

    // --- Kampe ---

    @Test
    void addMatchIncreasesMatchCount() {
        tournament.addMatch(new Match("Kvartfinale", "Lars Nielsen", "6-3, 6-4", true));
        assertEquals(1, tournament.getMatches().size());
    }

    @Test
    void removeMatchDecreasesMatchCount() {
        Match match = new Match("Kvartfinale", "Lars Nielsen", "6-3, 6-4", true);
        tournament.addMatch(match);
        tournament.removeMatch(match);
        assertEquals(0, tournament.getMatches().size());
    }

    @Test
    void getWonMatchesCountsCorrectly() {
        // Tilføjer 2 vundne og 1 tabt kamp
        tournament.addMatch(new Match("Kvartfinale", "Lars Nielsen", "6-3, 6-4", true));
        tournament.addMatch(new Match("Semifinale", "Anders Jensen", "6-2, 6-1", true));
        tournament.addMatch(new Match("Finale", "Bent Olsen", "3-6, 4-6", false));

        assertEquals(2, tournament.getWonMatches());
    }

    @Test
    void getWonMatchesReturnsZeroWhenNoMatches() {
        assertEquals(0, tournament.getWonMatches());
    }

    @Test
    void getWonMatchesReturnsZeroWhenAllLost() {
        tournament.addMatch(new Match("Kvartfinale", "Lars Nielsen", "3-6, 4-6", false));
        assertEquals(0, tournament.getWonMatches());
    }
}