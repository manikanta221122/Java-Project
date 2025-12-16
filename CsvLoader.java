import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CsvLoader {

    public static List<Team> loadTeams(String teamsFile) throws IOException {
        List<Team> teams = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(teamsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String name = line.trim();
                if (!name.isEmpty()) {
                    teams.add(new Team(name));
                }
            }
        }
        return teams;
    }

    // fixtures.csv line format: HomeTeam,AwayTeam
    public static List<Match> loadFixtures(String fixturesFile, List<Team> teams)
            throws IOException {
        Map<String, Team> byName = new HashMap<>();
        for (Team t : teams) {
            byName.put(t.getName().toLowerCase(), t);
        }

        List<Match> fixtures = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fixturesFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 2) {
                    throw new InvalidFixtureException("Bad fixture line: " + line);
                }
                Team home = byName.get(parts[0].trim().toLowerCase());
                Team away = byName.get(parts[1].trim().toLowerCase());
                if (home == null || away == null) {
                    throw new InvalidFixtureException("Unknown team in fixture: " + line);
                }
                fixtures.add(new Match(home, away));
            }
        }
        return fixtures;
    }
}
