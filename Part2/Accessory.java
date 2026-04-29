/**
 * Enumeration of accessories that can be equipped to affect typist performance.
 * Accessories provide various bonuses and penalties during the race.
 * 
 * @author Hasan Aliakbar Suleman
 * @version 1 : 29-04-2026
 */

public enum Accessory
{
    WRIST_SUPPORT("Wrist Support", "Reduces burnout duration by 30%"),
    
    ENERGY_DRINK("Energy Drink", "Increases accuracy by 15% in first half, decreases by 10% in second half"),
    
    NOISE_CANCELLING_HEADPHONES("Noise-Cancelling Headphones", "Reduces mistype chance by 20%");

    private final String displayName;
    private final String description;

    /**
     * Constructor for Accessory.
     * 
     * @param displayName friendly name for UI display
     * @param description effect description
     */
    Accessory(String displayName, String description)
    {
        this.displayName = displayName;
        this.description = description;
    }

    /**
     * Gets the display name of this accessory.
     * 
     * @return friendly name for UI
     */
    public String getDisplayName()
    {
        return displayName;
    }

    /**
     * Gets the description of this accessory's effects.
     * 
     * @return detailed description of effects
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Gets the burnout duration modifier for this accessory.
     * 
     * @return burnout modifier (1.0 means no change, <1.0 reduces duration)
     */
    public double getBurnoutModifier()
    {
        switch (this) {
            case WRIST_SUPPORT:
                return 0.7;  // 30% reduction in burnout duration
            default:
                return 1.0;
        }
    }

    /**
     * Gets the accuracy modifier for the first half of the race.
     * 
     * @return accuracy modifier for first half
     */
    public double getFirstHalfAccuracyModifier()
    {
        switch (this) {
            case ENERGY_DRINK:
                return 1.15;  // +15% accuracy in first half
            default:
                return 1.0;
        }
    }

    /**
     * Gets the accuracy modifier for the second half of the race.
     * 
     * @return accuracy modifier for second half
     */
    public double getSecondHalfAccuracyModifier()
    {
        switch (this) {
            case ENERGY_DRINK:
                return 0.90;  // -10% accuracy in second half
            default:
                return 1.0;
        }
    }

    /**
     * Gets the mistype rate modifier for this accessory.
     * 
     * @return mistype modifier (1.0 means no change, <1.0 reduces mistype chance)
     */
    public double getMistypeRateModifier()
    {
        switch (this) {
            case NOISE_CANCELLING_HEADPHONES:
                return 0.80;  // 20% reduction in mistype chance
            default:
                return 1.0;
        }
    }

    @Override
    public String toString()
    {
        return displayName;
    }
}
