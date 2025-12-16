public class Match {
    private final Team home;
    private final Team away;

    public Match(Team home, Team away) {
        if (home.equals(away)) {
            throw new InvalidFixtureException("Team cannot play against itself: " + home);
        }
        this.home = home;
        this.away = away;
    }

    public Team getHome() {
        return home;
    }

    public Team getAway() {
        return away;
    }

    @Override
    public String toString() {
        return home + " vs " + away;
    }
}
