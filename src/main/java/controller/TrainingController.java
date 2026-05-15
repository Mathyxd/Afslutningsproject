package controller;

import model.*;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Controller ansvarlig for håndtering af træningsevalueringer i systemet.
 * Håndterer oprettelse, opdatering og hentning af træningsresultater.
 */
public class TrainingController {

    private final ArrayList<TrainingResults> allTrainingResults = new ArrayList<>();

    /**
     * Opretter en ny træningsevaluering for en konkurrencespiller.
     *
     * @param member       spilleren der evalueres
     * @param discipline   disciplinen evalueringen er registreret i
     * @param score        ugentlig score mellem 1-10
     * @param date         datoen for evalueringen
     * @param coachComment coachenens kommentar til udviklingspunkter
     */
    public void createTraining(CompetitiveMember member, Discipline discipline, int score, LocalDate date, String coachComment) {
        Training training = new Training(discipline, score, date, coachComment);
        getOrCreateTrainingResults(member).addTraining(training);
    }

    /**
     * Opdaterer en spillers træningsscore og tilhørende kommentar.
     *
     * @param training     træningsevalueringen der skal opdateres
     * @param score        den nye score mellem 1-10
     * @param coachComment den nye kommentar til udviklingspunkter
     */
    public void updateScore(Training training, int score, String coachComment) {
        training.setScore(score);
        training.setCoachComment(coachComment);
    }

    /**
     * Henter alle træningsresultater for en specifik spiller.
     *
     * @param member spilleren hvis træningsresultater hentes
     * @return liste af træningsevalueringer for spilleren
     */
    public ArrayList<Training> getTrainingResultsForMember(CompetitiveMember member) {
        for (TrainingResults tr : allTrainingResults) {
            if (tr.getMember().equals(member)) {
                return tr.getTrainingResults();
            }
        }
        return new ArrayList<>();
    }

    /**
     * Henter eller opretter en træningsoversigt for en spiller.
     * Bruges internt til at sikre hver spiller kun har én oversigt.
     *
     * @param member spilleren som oversigten tilhører
     * @return træningsoversigten for spilleren
     */
    private TrainingResults getOrCreateTrainingResults(CompetitiveMember member) {
        for (TrainingResults tr : allTrainingResults) {
            if (tr.getMember().equals(member)) {
                return tr;
            }
        }
        TrainingResults newResults = new TrainingResults(member);
        allTrainingResults.add(newResults);
        return newResults;
    }

    /**
     * Henter alle træningsoversigter i systemet.
     *
     * @return liste af alle træningsoversigter
     */
    public ArrayList<TrainingResults> getAllTrainingResults() {
        return allTrainingResults;
    }
}