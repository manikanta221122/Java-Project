public class Stats {
    private int played;
    private int won;
    private int drawn;
    private int lost;
    private int goalsFor;
    private int goalsAgainst;
    private int points;

    public void update(int gf, int ga) {
        played++;
        goalsFor += gf;
        goalsAgainst += ga;
        if (gf > ga) {
            won++;
            points += 3;
        } else if (gf == ga) {
            drawn++;
            points += 1;
        } else {
            lost++;
        }
    }

    public int getPlayed() { return played; }
    public int getWon() { return won; }
    public int getDrawn() { return drawn; }
    public int getLost() { return lost; }
    public int getGoalsFor() { return goalsFor; }
    public int getGoalsAgainst() { return goalsAgainst; }
    public int getGoalDifference() { return goalsFor - goalsAgainst; }
    public int getPoints() { return points; }
}
