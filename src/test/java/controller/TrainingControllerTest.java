package controller;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Dækker: oprettelse af træning, opdatering af score og hentning af resultater
class TrainingControllerTest {

    private TrainingController controller;
    private CompetitiveMember member;

    @BeforeEach
    void setUp() {
        controller = new TrainingController();
        member = new CompetitiveMember("Anders Jensen", 25, 1, true, Discipline.SINGLE);
    }

    @Test
    void createTrainingAddsToMemberResults() {
        controller.createTraining(member, Discipline.SINGLE, 7, LocalDate.now(), "Good footwork");
        assertEquals(1, controller.getTrainingResultsForMember(member).size());
    }

    @Test
    void createTrainingHasCorrectScore() {
        controller.createTraining(member, Discipline.SINGLE, 7, LocalDate.now(), "Good footwork");
        Training training = controller.getTrainingResultsForMember(member).get(0);
        assertEquals(7, training.getScore());
    }

    @Test
    void createTrainingHasCorrectDiscipline() {
        controller.createTraining(member, Discipline.SINGLE, 7, LocalDate.now(), "Good footwork");
        Training training = controller.getTrainingResultsForMember(member).get(0);
        assertEquals(Discipline.SINGLE, training.getDiscipline());
    }

    @Test
    void updateScoreUpdatesCorrectly() {
        controller.createTraining(member, Discipline.SINGLE, 7, LocalDate.now(), "Good footwork");
        Training training = controller.getTrainingResultsForMember(member).get(0);

        controller.updateScore(training, 9, "Improved backhand");

        assertEquals(9, training.getScore());
        assertEquals("Improved backhand", training.getCoachComment());
    }

    @Test
    void getTrainingResultsForMemberReturnsEmptyListWhenNoResults() {
        assertEquals(0, controller.getTrainingResultsForMember(member).size());
    }

    @Test
    void multipleMembersHaveSeparateTrainingResults() {
        CompetitiveMember otherMember = new CompetitiveMember("Sofie Hansen", 15, 2, true, Discipline.DOUBLE);

        controller.createTraining(member, Discipline.SINGLE, 7, LocalDate.now(), "Good footwork");
        controller.createTraining(otherMember, Discipline.DOUBLE, 5, LocalDate.now(), "Work on serve");

        assertEquals(1, controller.getTrainingResultsForMember(member).size());
        assertEquals(1, controller.getTrainingResultsForMember(otherMember).size());
    }
}