/**
 * Stores the configuration for a typing race.
 * Includes passage selection, seat count, and difficulty modifiers.
 * Used by Part II GUI to pass settings to Part I's TypingRace.
 * 
 * @author Hasan Aliakbar Suleman
 * @version 1 : 29-04-2026
 */

public class RaceConfiguration
{
    private Passage passage;
    private int seatCount;  // 2-6 typists

    // Difficulty modifiers (will be applied by GUI to individual Typists)
    private boolean autocorrectEnabled;
    private boolean caffeineMode;
    private boolean nightShift;

    /**
     * Constructor for RaceConfiguration with default settings.
     */
    public RaceConfiguration()
    {
        this.passage = null;
        this.seatCount = 3;
        this.autocorrectEnabled = false;
        this.caffeineMode = false;
        this.nightShift = false;
    }

    /**
     * Sets the passage for the race.
     *
     * @param passage the Passage object
     */
    public void setPassage(Passage passage)
    {
        this.passage = passage;
    }

    /**
     * Returns the configured passage.
     *
     * @return the Passage object
     */
    public Passage getPassage()
    {
        return passage;
    }

    /**
     * Sets the number of typists competing (2-6).
     *
     * @param count number of seats
     */
    public void setSeatCount(int count)
    {
        if (count >= 2 && count <= 6) {
            this.seatCount = count;
        }
    }

    /**
     * Returns the number of typists.
     *
     * @return seat count
     */
    public int getSeatCount()
    {
        return seatCount;
    }

    /**
     * Enables or disables autocorrect mode.
     * When enabled, GUI should reduce typist accuracy slightly.
     *
     * @param enabled true to enable
     */
    public void setAutocorrect(boolean enabled)
    {
        this.autocorrectEnabled = enabled;
    }

    /**
     * Returns whether autocorrect is enabled.
     *
     * @return true if enabled
     */
    public boolean isAutocorrectEnabled()
    {
        return autocorrectEnabled;
    }

    /**
     * Enables or disables caffeine mode.
     * Typists should have higher accuracy initially, then drop.
     *
     * @param enabled true to enable
     */
    public void setCaffeineMode(boolean enabled)
    {
        this.caffeineMode = enabled;
    }

    /**
     * Returns whether caffeine mode is enabled.
     *
     * @return true if enabled
     */
    public boolean isCaffeineMode()
    {
        return caffeineMode;
    }

    /**
     * Enables or disables night shift mode.
     * All typists have reduced accuracy (everyone is tired).
     *
     * @param enabled true to enable
     */
    public void setNightShift(boolean enabled)
    {
        this.nightShift = enabled;
    }

    /**
     * Returns whether night shift mode is enabled.
     *
     * @return true if enabled
     */
    public boolean isNightShift()
    {
        return nightShift;
    }
}
