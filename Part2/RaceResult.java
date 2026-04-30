import java.io.Serializable;

/**
 * Represents the performance metrics for a single typist in a completed race.
 * Stores all the key statistics calculated after a race finishes.
 *
 * @author Hasan Aliakbar Suleman
 * @version 1.0
 */
public class RaceResult implements Serializable {
    private static final long serialVersionUID = 1L;
    private String typistName;
    private int position; // 1-based, 0 if didn't finish
    private double wpm;
    private double accuracyPercentage;
    private int burnoutCount;
    private double accuracyChange;
    private double timeTakenSeconds;
    private int pointsAwarded;
    private double earningsAwarded;
    private String sponsorName;
    private double sponsorBonus;
    private String titleEarned;

    /**
     * Constructor for RaceResult.
     *
     * @param typistName the name of the typist
     * @param position finishing position (1 for winner, higher for others, 0 if DNF)
     * @param wpm words per minute achieved
     * @param accuracyPercentage percentage of correct keystrokes
     * @param burnoutCount number of times burnt out
     * @param accuracyChange change in accuracy rating after race
     * @param timeTakenSeconds total time taken for the race in seconds
     * @param pointsAwarded points earned from this race
     * @param earningsAwarded prize money earned
     * @param sponsorName sponsor assigned to the typist
     * @param sponsorBonus sponsor bonus amount
     * @param titleEarned title or badge awarded by this race
     */
    public RaceResult(String typistName, int position, double wpm,
                     double accuracyPercentage, int burnoutCount,
                     double accuracyChange, double timeTakenSeconds,
                     int pointsAwarded, double earningsAwarded,
                     String sponsorName, double sponsorBonus,
                     String titleEarned) {
        this.typistName = typistName;
        this.position = position;
        this.wpm = wpm;
        this.accuracyPercentage = accuracyPercentage;
        this.burnoutCount = burnoutCount;
        this.accuracyChange = accuracyChange;
        this.timeTakenSeconds = timeTakenSeconds;
        this.pointsAwarded = pointsAwarded;
        this.earningsAwarded = earningsAwarded;
        this.sponsorName = sponsorName;
        this.sponsorBonus = sponsorBonus;
        this.titleEarned = titleEarned;
    }

    // Getters
    public String getTypistName() { return typistName; }
    public int getPosition() { return position; }
    public double getWpm() { return wpm; }
    public double getAccuracyPercentage() { return accuracyPercentage; }
    public int getBurnoutCount() { return burnoutCount; }
    public double getAccuracyChange() { return accuracyChange; }
    public double getTimeTakenSeconds() { return timeTakenSeconds; }
    public int getPointsAwarded() { return pointsAwarded; }
    public double getEarningsAwarded() { return earningsAwarded; }
    public String getSponsorName() { return sponsorName; }
    public double getSponsorBonus() { return sponsorBonus; }
    public String getTitleEarned() { return titleEarned; }

    /**
     * Returns a formatted string summary of the race result.
     *
     * @return summary string
     */
    public String getSummary() {
        return String.format("%s - Pos: %d, WPM: %.1f, Acc: %.1f%%, Burnouts: %d, Points: %d, Earned: %.0f",
                           typistName, position, wpm, accuracyPercentage, burnoutCount, pointsAwarded, earningsAwarded);
    }

    /**
     * Returns a detailed string with all metrics.
     *
     * @return detailed string
     */
    public String getDetailedString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Typist: %s\nPosition: %d\nWPM: %.2f\nAccuracy: %.2f%%\nBurnouts: %d\nAccuracy Change: %+f\nTime: %.2f seconds\n",
                           typistName, position, wpm, accuracyPercentage, burnoutCount, accuracyChange, timeTakenSeconds));
        sb.append(String.format("Points Awarded: %d\n", pointsAwarded));
        sb.append(String.format("Earnings Awarded: %.2f coins\n", earningsAwarded));
        if (sponsorName != null && !sponsorName.isEmpty()) {
            sb.append(String.format("Sponsor: %s (+%.0f)\n", sponsorName, sponsorBonus));
        }
        if (titleEarned != null && !titleEarned.isEmpty()) {
            sb.append(String.format("Title Earned: %s\n", titleEarned));
        }
        return sb.toString();
    }
}