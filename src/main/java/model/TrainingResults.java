package model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Repræsenterer en oversigt over træningsevalueringer for en konkurrencespiller.
 * Fungerer som en samling af Training objekter tilknyttet én spiller.
 */
public class TrainingResults {
    private CompetitiveMember member;
    private ArrayList<Training> trainingResults;

    /**
     * Opretter en ny træningsoversigt for en konkurrencespiller.
     *
     * @param member spilleren som træningsoversigten tilhører
     */
    public TrainingResults(CompetitiveMember member) {
        this.member = member;
        this.trainingResults = new ArrayList<>();
    }

    // Getters
    public CompetitiveMember getMember() { return member; }
    public ArrayList<Training> getTrainingResults() { return trainingResults; }

    /**
     * Tilføjer en ny træningsevaluering til oversigten.
     *
     * @param training træningsevalueringen der skal tilføjes
     */
    public void addTraining(Training training) {
        trainingResults.add(training);
    }

    /**
     * Fjerner en træningsevaluering fra oversigten.
     *
     * @param training træningsevalueringen der skal fjernes
     */
    public void removeTraining(Training training) {
        trainingResults.remove(training);
    }
}