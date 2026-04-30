import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages statistics for all typists across races.
 * Handles loading/saving stats to persistent storage.
 *
 * @author Hasan Aliakbar Suleman
 * @version 1.0
 */
public class StatisticsManager {
    private static final String STATS_FILE = "typist_stats.dat";
    private HashMap<String, TypistStats> typistStats;

    /**
     * Constructor - loads existing stats from file.
     */
    public StatisticsManager() {
        typistStats = new HashMap<>();
        loadStats();
    }

    /**
     * Gets stats for a typist, creating new stats if they don't exist.
     *
     * @param typistName the name of the typist
     * @return the TypistStats for that typist
     */
    public TypistStats getTypistStats(String typistName) {
        return typistStats.computeIfAbsent(typistName, TypistStats::new);
    }

    /**
     * Adds a race result for a typist.
     *
     * @param typistName the name of the typist
     * @param result the race result to add
     */
    public void addRaceResult(String typistName, RaceResult result) {
        TypistStats stats = getTypistStats(typistName);
        stats.addRaceResult(result);
        saveStats();
    }

    /**
     * Gets all typist stats.
     *
     * @return map of typist name to stats
     */
    public Map<String, TypistStats> getAllStats() {
        return new HashMap<>(typistStats);
    }

    /**
     * Gets all typists sorted by cumulative points descending.
     *
     * @return sorted array of TypistStats
     */
    public TypistStats[] getLeaderboardByPoints() {
        TypistStats[] leaderboard = typistStats.values().toArray(new TypistStats[0]);
        java.util.Arrays.sort(leaderboard, (a, b) -> Integer.compare(b.getCumulativePoints(), a.getCumulativePoints()));
        return leaderboard;
    }

    /**
     * Gets all typists sorted by total earnings descending.
     *
     * @return sorted array of TypistStats
     */
    public TypistStats[] getLeaderboardByEarnings() {
        TypistStats[] leaderboard = typistStats.values().toArray(new TypistStats[0]);
        java.util.Arrays.sort(leaderboard, (a, b) -> Double.compare(b.getTotalEarnings(), a.getTotalEarnings()));
        return leaderboard;
    }

    /**
     * Saves all stats to file.
     */
    private void saveStats() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STATS_FILE))) {
            oos.writeObject(typistStats);
        } catch (IOException e) {
            System.err.println("Error saving stats: " + e.getMessage());
        }
    }

    /**
     * Loads stats from file.
     */
    @SuppressWarnings("unchecked")
    private void loadStats() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STATS_FILE))) {
            typistStats = (HashMap<String, TypistStats>) ois.readObject();
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, start with empty stats
            typistStats = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading stats: " + e.getMessage());
            typistStats = new HashMap<>();
        }
    }

    /**
     * Clears all stats (for testing or reset).
     */
    public void clearAllStats() {
        typistStats.clear();
        saveStats();
    }

    /**
     * Gets a summary of all typists' stats.
     *
     * @return formatted summary string
     */
    public String getAllStatsSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== TYPING RACE STATISTICS ===\n\n");
        for (TypistStats stats : typistStats.values()) {
            sb.append(stats.getSummary()).append("\n");
        }
        if (typistStats.isEmpty()) {
            sb.append("No statistics available yet.\n");
        }
        return sb.toString();
    }
}