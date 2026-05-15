package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

// Dækker: korrekt attribut registrering og opdatering
class TournamentTest {

    private Tournament tournament;

    @BeforeEach
    void setUp() {
        tournament = new Tournament("Danish Open", 3, "6-3, 6-4", LocalDate.of(2026, 3, 15), Discipline.SINGLE);
    }

    @Test
    void tournamentNameIsSetCorrectlyOnCreation() {
        assertEquals("Danish Open", tournament.getTournamentName());
    }

    @Test
    void rankingIsSetCorrectlyOnCreation() {
        assertEquals(3, tournament.getRanking());
    }

    @Test
    void matchResultIsSetCorrectlyOnCreation() {
        assertEquals("6-3, 6-4", tournament.getMatchResult());
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
    void setRankingUpdatesCorrectly() {
        tournament.setRanking(1);
        assertEquals(1, tournament.getRanking());
    }

    @Test
    void setMatchResultUpdatesCorrectly() {
        tournament.setMatchResult("6-1, 6-2");
        assertEquals("6-1, 6-2", tournament.getMatchResult());
    }
}