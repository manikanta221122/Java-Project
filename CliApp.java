import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CliApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LeagueTable table = new LeagueTable();
        Simulator simulator = SimulatorFactory.createRandomSimulator();
        List<Team> teams = null;
        List<Match> fixtures = null;

        while (true) {
            System.out.println("Commands: LoadTeams, Simulate, Standings, Exit");
            String cmd = scanner.nextLine().trim();

            try {
                switch (cmd.toLowerCase()) {
                    case "loadteams":
                        System.out.print("Enter teams.csv path: ");
                        String teamsPath = scanner.nextLine().trim();
                        System.out.print("Enter fixtures.csv path: ");
                        String fixturesPath = scanner.nextLine().trim();
                        teams = CsvLoader.loadTeams(teamsPath);
                        fixtures = CsvLoader.loadFixtures(fixturesPath, teams);
                        System.out.println("Loaded " + teams.size() + " teams and " +
                                fixtures.size() + " fixtures.");
                        break;

                    case "simulate":
                        if (fixtures == null) {
                            System.out.println("Load teams and fixtures first.");
                            break;
                        }
                        simulateAll(fixtures, table, simulator);
                        System.out.println("Simulation complete. Results in results.txt");
                        break;

                    case "standings":
                        printStandings(table);
                        writeStandingsCsv(table, "standings.csv");
                        System.out.println("Standings written to standings.csv");
                        break;

                    case "exit":
                        return;

                    default:
                        System.out.println("Unknown command.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void simulateAll(List<Match> fixtures, LeagueTable table, Simulator simulator)
            throws IOException {
        try (FileWriter fw = new FileWriter("results.txt")) {
            for (Match m : fixtures) {
                MatchResult result = simulator.simulate(m);
                table.applyResult(result);
                fw.write(result.toString());
                fw.write(System.lineSeparator());
            }
        }
    }

    private static void printStandings(LeagueTable table) {
        System.out.println("Team\tP\tW\tD\tL\tGF\tGA\tGD\tPts");
        for (Map.Entry<Team, Stats> e : table.getStandings()) {
            Team t = e.getKey();
            Stats s = e.getValue();
            System.out.printf("%s\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d%n",
                    t.getName(),
                    s.getPlayed(),
                    s.getWon(),
                    s.getDrawn(),
                    s.getLost(),
                    s.getGoalsFor(),
                    s.getGoalsAgainst(),
                    s.getGoalDifference(),
                    s.getPoints());
        }
    }

    private static void writeStandingsCsv(LeagueTable table, String path) throws IOException {
        try (FileWriter fw = new FileWriter(path)) {
            fw.write("Team,P,W,D,L,GF,GA,GD,Pts\n");
            for (Map.Entry<Team, Stats> e : table.getStandings()) {
                Team t = e.getKey();
                Stats s = e.getValue();
                fw.write(String.format("%s,%d,%d,%d,%d,%d,%d,%d,%d%n",
                        t.getName(),
                        s.getPlayed(),
                        s.getWon(),
                        s.getDrawn(),
                        s.getLost(),
                        s.getGoalsFor(),
                        s.getGoalsAgainst(),
                        s.getGoalDifference(),
                        s.getPoints()));
            }
        }
    }
}
