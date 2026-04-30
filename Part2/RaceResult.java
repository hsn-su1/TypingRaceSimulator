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
     */
    public RaceResult(String typistName, int position, double wpm,
                     double accuracyPercentage, int burnoutCount,
                     double accuracyChange, double timeTakenSeconds) {
        this.typistName = typistName;
        this.position = position;
        this.wpm = wpm;
        this.accuracyPercentage = accuracyPercentage;
        this.burnoutCount = burnoutCount;
        this.accuracyChange = accuracyChange;
        this.timeTakenSeconds = timeTakenSeconds;
    }

    // Getters
    public String getTypistName() { return typistName; }
    public int getPosition() { return position; }
    public double getWpm() { return wpm; }
    public double getAccuracyPercentage() { return accuracyPercentage; }
    public int getBurnoutCount() { return burnoutCount; }
    public double getAccuracyChange() { return accuracyChange; }
    public double getTimeTakenSeconds() { return timeTakenSeconds; }

    /**
     * Returns a formatted string summary of the race result.
     *
     * @return summary string
     */
    public String getSummary() {
        return String.format("%s - Pos: %d, WPM: %.1f, Acc: %.1f%%, Burnouts: %d, Acc Change: %+f",
                           typistName, position, wpm, accuracyPercentage, burnoutCount, accuracyChange);
    }

    /**
     * Returns a detailed string with all metrics.
     *
     * @return detailed string
     */
    public String getDetailedString() {
        return String.format("Typist: %s\nPosition: %d\nWPM: %.2f\nAccuracy: %.2f%%\nBurnouts: %d\nAccuracy Change: %+f\nTime: %.2f seconds",
                           typistName, position, wpm, accuracyPercentage, burnoutCount, accuracyChange, timeTakenSeconds);
    }
}