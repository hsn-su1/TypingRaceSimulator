/**
 * Enumeration of sponsor deals for typists.
 * Sponsors offer performance bonuses based on specific race conditions.
 *
 * @author Hasan Aliakbar Suleman
 * @version 1.0
 */
public enum Sponsor {
    NONE("No Sponsor", "No bonus", 0),
    KEYCORP("KeyCorp", "+50 coins if race completes without a burnout", 50),
    SPEEDSPRINT("SpeedSprint", "+40 coins if WPM exceeds 70", 40),
    PRECISION_PARTNERS("Precision Partners", "+30 coins if accuracy is 90% or higher", 30),
    ENDURANCE_ENTERPRISE("Endurance Enterprise", "+40 coins if no burnout and finish the race", 40);

    private final String displayName;
    private final String description;
    private final int bonusAmount;

    Sponsor(String displayName, String description, int bonusAmount) {
        this.displayName = displayName;
        this.description = description;
        this.bonusAmount = bonusAmount;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public int getBonusAmount() {
        return bonusAmount;
    }

    /**
     * Determines whether the sponsor bonus applies to the given race result.
     *
     * @param result the race result
     * @return true if the bonus condition is met
     */
    public boolean bonusApplies(RaceResult result) {
        if (result == null) {
            return false;
        }

        switch (this) {
            case KEYCORP:
                return result.getBurnoutCount() == 0 && result.getPosition() > 0;
            case SPEEDSPRINT:
                return result.getWpm() >= 70.0 && result.getPosition() > 0;
            case PRECISION_PARTNERS:
                return result.getAccuracyPercentage() >= 90.0 && result.getPosition() > 0;
            case ENDURANCE_ENTERPRISE:
                return result.getBurnoutCount() == 0 && result.getPosition() > 0;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return displayName;
    }
}