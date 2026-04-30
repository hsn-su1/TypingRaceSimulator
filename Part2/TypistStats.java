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
    private int cumulativePoints;
    private double totalEarnings;
    private double availableEarnings;
    private ArrayList<String> badges;
    private ArrayList<String> upgrades;
    private int consecutiveWins;
    private int noBurnoutStreak;

    /**
     * Constructor for TypistStats.
     *
     * @param typistName the name of the typist
     */
    public TypistStats(String typistName) {
        this.typistName = typistName;
        this.personalBestWpm = 0.0;
        this.raceHistory = new ArrayList<>();
        this.cumulativePoints = 0;
        this.totalEarnings = 0.0;
        this.availableEarnings = 0.0;
        this.badges = new ArrayList<>();
        this.upgrades = new ArrayList<>();
        this.consecutiveWins = 0;
        this.noBurnoutStreak = 0;
    }

    /**
     * Adds a new race result and updates personal best, points, earnings, and titles.
     *
     * @param result the race result to add
     */
    public void addRaceResult(RaceResult result) {
        if (raceHistory == null) {
            raceHistory = new ArrayList<>();
        }
        if (badges == null) {
            badges = new ArrayList<>();
        }
        if (upgrades == null) {
            upgrades = new ArrayList<>();
        }

        raceHistory.add(result);
        if (result.getWpm() > personalBestWpm) {
            personalBestWpm = result.getWpm();
        }
        cumulativePoints += result.getPointsAwarded();
        totalEarnings += result.getEarningsAwarded();
        availableEarnings += result.getEarningsAwarded();

        if (result.getPosition() == 1) {
            consecutiveWins++;
        } else {
            consecutiveWins = 0;
        }

        if (result.getBurnoutCount() == 0 && result.getPosition() > 0) {
            noBurnoutStreak++;
        } else {
            noBurnoutStreak = 0;
        }

        checkAndAwardTitles();
    }

    /**
     * Ensures all transient collections are initialized after deserialization.
     *
     * @param in the input stream
     * @throws java.io.IOException if an I/O error occurs
     * @throws ClassNotFoundException if a class cannot be found
     */
    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        in.defaultReadObject();
        if (raceHistory == null) {
            raceHistory = new ArrayList<>();
        }
        if (badges == null) {
            badges = new ArrayList<>();
        }
        if (upgrades == null) {
            upgrades = new ArrayList<>();
        }
    }

    /**
     * Purchases upgrades automatically using available earnings.
     *
     * @param customization the typist customization to apply upgrades to
     */
    public void applyAvailableUpgrades(TypistCustomization customization) {
        if (customization == null) {
            return;
        }

        if (availableEarnings >= 200 && !upgrades.contains("Wrist Support Upgrade")) {
            availableEarnings -= 200;
            upgrades.add("Wrist Support Upgrade");
            customization.equipAccessory(Accessory.WRIST_SUPPORT);
        }
        if (availableEarnings >= 300 && !upgrades.contains("Headphones Upgrade")) {
            availableEarnings -= 300;
            upgrades.add("Headphones Upgrade");
            customization.equipAccessory(Accessory.NOISE_CANCELLING_HEADPHONES);
        }
        if (availableEarnings >= 500 && !upgrades.contains("Mechanical Keyboard Upgrade")) {
            availableEarnings -= 500;
            upgrades.add("Mechanical Keyboard Upgrade");
            customization.setKeyboardType(KeyboardType.MECHANICAL);
        }
    }

    /**
     * Checks if any milestone titles should be awarded.
     */
    private void checkAndAwardTitles() {
        if (consecutiveWins >= 3 && !badges.contains("Speed Demon")) {
            badges.add("Speed Demon");
        }
        if (noBurnoutStreak >= 5 && !badges.contains("Iron Fingers")) {
            badges.add("Iron Fingers");
        }
        if (getTotalRaces() >= 10 && !badges.contains("Marathon Master")) {
            badges.add("Marathon Master");
        }
    }

    // Getters
    public String getTypistName() { return typistName; }
    public double getPersonalBestWpm() { return personalBestWpm; }
    public ArrayList<RaceResult> getRaceHistory() { return raceHistory == null ? new ArrayList<>() : new ArrayList<>(raceHistory); }
    public int getCumulativePoints() { return cumulativePoints; }
    public double getTotalEarnings() { return totalEarnings; }
    public double getAvailableEarnings() { return availableEarnings; }
    public ArrayList<String> getBadges() { return badges == null ? new ArrayList<>() : new ArrayList<>(badges); }
    public ArrayList<String> getUpgrades() { return upgrades == null ? new ArrayList<>() : new ArrayList<>(upgrades); }
    public int getConsecutiveWins() { return consecutiveWins; }
    public int getNoBurnoutStreak() { return noBurnoutStreak; }

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
        return String.format("%s - Races: %d, Points: %d, Earnings: %.0f, Best WPM: %.1f, Wins: %d",
                           typistName, getTotalRaces(), cumulativePoints, totalEarnings,
                           personalBestWpm, getWinCount());
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
        sb.append("Cumulative Points: ").append(cumulativePoints).append("\n");
        sb.append("Total Earnings: ").append(String.format("%.2f", totalEarnings)).append(" coins\n");
        sb.append("Available Earnings: ").append(String.format("%.2f", availableEarnings)).append(" coins\n");
        sb.append("Personal Best WPM: ").append(String.format("%.2f", personalBestWpm)).append("\n");
        sb.append("Average WPM: ").append(String.format("%.2f", getAverageWpm())).append("\n");
        sb.append("Average Accuracy: ").append(String.format("%.2f%%", getAverageAccuracy())).append("\n");
        sb.append("Total Burnouts: ").append(getTotalBurnouts()).append("\n");
        sb.append("Wins: ").append(getWinCount()).append("\n");
        sb.append("Consecutive Wins: ").append(consecutiveWins).append("\n");
        sb.append("No Burnout Streak: ").append(noBurnoutStreak).append("\n");
        if (!badges.isEmpty()) {
            sb.append("Badges: ").append(String.join(", ", badges)).append("\n");
        }
        if (!upgrades.isEmpty()) {
            sb.append("Upgrades: ").append(String.join(", ", upgrades)).append("\n");
        }
        sb.append("Recent Results:\n");
        int count = Math.min(5, raceHistory.size());
        for (int i = raceHistory.size() - count; i < raceHistory.size(); i++) {
            sb.append("  ").append(raceHistory.get(i).getSummary()).append("\n");
        }
        return sb.toString();
    }
}