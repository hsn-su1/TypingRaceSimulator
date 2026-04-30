import java.util.ArrayList;

/**
 * Stores statistics for a single typist across multiple races.
 * Maintains personal bests and historical race results.
 *
 * @author Hasan Aliakbar Suleman
 * @version 1.0
 */
public class TypistStats implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private String typistName;
    private double personalBestWpm;
    private ArrayList<RaceResult> raceHistory;

    /**
     * Constructor for TypistStats.
     *
     * @param typistName the name of the typist
     */
    public TypistStats(String typistName) {
        this.typistName = typistName;
        this.personalBestWpm = 0.0;
        this.raceHistory = new ArrayList<>();
    }

    /**
     * Adds a new race result and updates personal best if necessary.
     *
     * @param result the race result to add
     */
    public void addRaceResult(RaceResult result) {
        raceHistory.add(result);
        if (result.getWpm() > personalBestWpm) {
            personalBestWpm = result.getWpm();
        }
    }

    // Getters
    public String getTypistName() { return typistName; }
    public double getPersonalBestWpm() { return personalBestWpm; }
    public ArrayList<RaceResult> getRaceHistory() { return new ArrayList<>(raceHistory); }

    /**
     * Gets the number of races completed.
     *
     * @return total races
     */
    public int getTotalRaces() {
        return raceHistory.size();
    }

    /**
     * Gets the average WPM across all races.
     *
     * @return average WPM
     */
    public double getAverageWpm() {
        if (raceHistory.isEmpty()) return 0.0;
        double sum = 0.0;
        for (RaceResult r : raceHistory) {
            sum += r.getWpm();
        }
        return sum / raceHistory.size();
    }

    /**
     * Gets the average accuracy across all races.
     *
     * @return average accuracy percentage
     */
    public double getAverageAccuracy() {
        if (raceHistory.isEmpty()) return 0.0;
        double sum = 0.0;
        for (RaceResult r : raceHistory) {
            sum += r.getAccuracyPercentage();
        }
        return sum / raceHistory.size();
    }

    /**
     * Gets the total burnouts across all races.
     *
     * @return total burnouts
     */
    public int getTotalBurnouts() {
        int total = 0;
        for (RaceResult r : raceHistory) {
            total += r.getBurnoutCount();
        }
        return total;
    }

    /**
     * Gets the win count (position 1).
     *
     * @return number of wins
     */
    public int getWinCount() {
        int wins = 0;
        for (RaceResult r : raceHistory) {
            if (r.getPosition() == 1) wins++;
        }
        return wins;
    }

    /**
     * Gets the latest race result.
     *
     * @return the most recent RaceResult, or null if no races
     */
    public RaceResult getLatestResult() {
        if (raceHistory.isEmpty()) return null;
        return raceHistory.get(raceHistory.size() - 1);
    }

    /**
     * Gets a summary string of the typist's stats.
     *
     * @return summary string
     */
    public String getSummary() {
        return String.format("%s - Races: %d, Best WPM: %.1f, Avg WPM: %.1f, Avg Acc: %.1f%%, Wins: %d",
                           typistName, getTotalRaces(), personalBestWpm, getAverageWpm(),
                           getAverageAccuracy(), getWinCount());
    }

    /**
     * Gets a detailed string with all stats.
     *
     * @return detailed string
     */
    public String getDetailedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Typist: ").append(typistName).append("\n");
        sb.append("Total Races: ").append(getTotalRaces()).append("\n");
        sb.append("Personal Best WPM: ").append(String.format("%.2f", personalBestWpm)).append("\n");
        sb.append("Average WPM: ").append(String.format("%.2f", getAverageWpm())).append("\n");
        sb.append("Average Accuracy: ").append(String.format("%.2f%%", getAverageAccuracy())).append("\n");
        sb.append("Total Burnouts: ").append(getTotalBurnouts()).append("\n");
        sb.append("Wins: ").append(getWinCount()).append("\n");
        sb.append("Recent Results:\n");
        int count = Math.min(5, raceHistory.size());
        for (int i = raceHistory.size() - count; i < raceHistory.size(); i++) {
            sb.append("  ").append(raceHistory.get(i).getSummary()).append("\n");
        }
        return sb.toString();
    }
}