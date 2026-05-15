package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

// Dækker: score validering, dato og kommentar registrering
class TrainingTest {

    private Training training;

    @BeforeEach
    void setUp() {
        training = new Training(Discipline.SINGLE, 7, LocalDate.of(2026, 1, 1), "Good footwork");
    }

    @Test
    void scoreIsSetCorrectlyOnCreation() {
        assertEquals(7, training.getScore());
    }

    @Test
    void disciplineIsSetCorrectlyOnCreation() {
        assertEquals(Discipline.SINGLE, training.getDiscipline());
    }

    @Test
    void dateIsSetCorrectlyOnCreation() {
        assertEquals(LocalDate.of(2026, 1, 1), training.getDate());
    }

    @Test
    void coachCommentIsSetCorrectlyOnCreation() {
        assertEquals("Good footwork", training.getCoachComment());
    }

    @Test
    void setScoreUpdatesCorrectly() {
        training.setScore(9);
        assertEquals(9, training.getScore());
    }

    @Test
    void setCoachCommentUpdatesCorrectly() {
        training.setCoachComment("Improve backhand");
        assertEquals("Improve backhand", training.getCoachComment());
    }
}