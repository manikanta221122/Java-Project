public class MatchResult {
    private final Match match;
    private final int homeGoals;
    private final int awayGoals;

    public MatchResult(Match match, int homeGoals, int awayGoals) {
        this.match = match;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }

    public Match getMatch() { return match; }
    public int getHomeGoals() { return homeGoals; }
    public int getAwayGoals() { return awayGoals; }

    @Override
    public String toString() {
        return match + " : " + homeGoals + "-" + awayGoals;
    }
}
