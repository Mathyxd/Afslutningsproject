package model;

/**
 * Repræsenterer én kamp inden for en turnering.
 * Indeholder runde, modstander, score og resultat.
 */
public class Match {
    private String round;
    private String opponent;
    private String score;
    private boolean won;

    /**
     * Opretter en ny kamp inden for en turnering.
     *
     * @param round    runden kampen blev spillet i (fx "Kvartfinale")
     * @param opponent modstanderens navn
     * @param score    kampens score (fx "6-3, 6-4")
     * @param won      true hvis kampen blev vundet, false hvis tabt
     */
    public Match(String round, String opponent, String score, boolean won) {
        this.round = round;
        this.opponent = opponent;
        this.score = score;
        this.won = won;
    }

    // Getters
    public String getRound() { return round; }
    public String getOpponent() { return opponent; }
    public String getScore() { return score; }
    public boolean isWon() { return won; }

    // Setters
    public void setRound(String round) { this.round = round; }
    public void setOpponent(String opponent) { this.opponent = opponent; }
    public void setScore(String score) { this.score = score; }
    public void setWon(boolean won) { this.won = won; }
}