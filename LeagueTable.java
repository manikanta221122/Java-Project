import java.util.*;

public class LeagueTable {
    private final Map<Team, Stats> table = new HashMap<>();

    public void registerTeam(Team team) {
        table.putIfAbsent(team, new Stats());
    }

    public void applyResult(MatchResult result) {
        Team home = result.getMatch().getHome();
        Team away = result.getMatch().getAway();
        registerTeam(home);
        registerTeam(away);

        table.get(home).update(result.getHomeGoals(), result.getAwayGoals());
        table.get(away).update(result.getAwayGoals(), result.getHomeGoals());
    }

    public List<Map.Entry<Team, Stats>> getStandings() {
        List<Map.Entry<Team, Stats>> list = new ArrayList<>(table.entrySet());
        list.sort((e1, e2) -> {
            Stats s1 = e1.getValue();
            Stats s2 = e2.getValue();
            // sort by points, then GD, then GF
            int cmp = Integer.compare(s2.getPoints(), s1.getPoints());
            if (cmp != 0) return cmp;
            cmp = Integer.compare(s2.getGoalDifference(), s1.getGoalDifference());
            if (cmp != 0) return cmp;
            return Integer.compare(s2.getGoalsFor(), s1.getGoalsFor());
        });
        return list;
    }
}
