import java.util.Random;

public class RandomSimulator implements Simulator {
    private final Random random = new Random();

    @Override
    public MatchResult simulate(Match match) {
        int homeGoals = random.nextInt(5); // 0–4
        int awayGoals = random.nextInt(5);
        return new MatchResult(match, homeGoals, awayGoals);
    }
}
