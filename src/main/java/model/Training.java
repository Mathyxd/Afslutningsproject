package model;

import java.time.LocalDate;

/**
 * Repræsenterer en ugentlig træningsevaluering givet af coachen.
 * Indeholder en score, dato og kommentar til spillerens udvikling.
 */
public class Training {
    private Discipline discipline;
    private int score; // 1-10 vurdering af coach
    private LocalDate date;
    private String coachComment;

    /**
     * Opretter en ny træningsevaluering for en konkurrencespiller.
     *
     * @param discipline   disciplinen evalueringen er registreret i
     * @param score        ugentlig score mellem 1-10 givet af coachen
     * @param date         datoen for evalueringen
     * @param coachComment coachenens kommentar til spillerens udviklingspunkter
     */
    public Training(Discipline discipline, int score, LocalDate date, String coachComment) {
        this.discipline = discipline;
        this.score = score;
        this.date = date;
        this.coachComment = coachComment;
    }

    // Getters
    public Discipline getDiscipline() {
        return discipline; }

    public int getScore() {
        return score; }

    public LocalDate getDate() {
        return date; }

    public String getCoachComment() {
        return coachComment; }

    // Setters
    public void setScore(int score) {
        this.score = score; }

    public void setCoachComment(String coachComment) {
        this.coachComment = coachComment; }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline; }
}