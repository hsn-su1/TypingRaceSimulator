/**
 * Enumeration of different typing styles.
 * Each style influences accuracy and burnout characteristics.
 * 
 * @author Hasan Aliakbar Suleman
 * @version 1 : 29-04-2026
 */

public enum TypingStyle
{
    TOUCH_TYPIST("Touch Typist", 0.85, 0.15, 1.0),
        // High accuracy, moderate burnout, normal speed
    
    HUNT_AND_PECK("Hunt & Peck", 0.65, 0.08, 0.75),
        // Lower accuracy, lower burnout risk, slower
    
    PHONE_THUMBS("Phone Thumbs", 0.60, 0.12, 0.60),
        // Lowest accuracy, moderate burnout, very slow
    
    VOICE_TO_TEXT("Voice-to-Text", 0.80, 0.20, 0.90);
        // High accuracy but highest burnout risk, slower than touch typist

    private final String displayName;
    private final double baseAccuracyModifier;  // Multiplier for accuracy (0.0 - 1.0)
    private final double burnoutRiskModifier;   // Multiplier for burnout (0.0 - 1.0)
    private final double speedModifier;         // Multiplier for speed (0.0 - 1.0)

    /**
     * Constructor for TypingStyle.
     * 
     * @param displayName friendly name for UI display
     * @param accuracyMod accuracy modifier (multiplied against base accuracy)
     * @param burnoutMod burnout risk modifier (affects burnout duration)
     * @param speedMod speed modifier (affects character progression)
     */
    TypingStyle(String displayName, double accuracyMod, double burnoutMod, double speedMod)
    {
        this.displayName = displayName;
        this.baseAccuracyModifier = accuracyMod;
        this.burnoutRiskModifier = burnoutMod;
        this.speedModifier = speedMod;
    }

    /**
     * Gets the display name of this typing style.
     * 
     * @return friendly name for UI
     */
    public String getDisplayName()
    {
        return displayName;
    }

    /**
     * Gets the accuracy modifier for this typing style.
     * Multiply a typist's base accuracy by this value.
     * 
     * @return accuracy modifier (0.0 to 1.0)
     */
    public double getAccuracyModifier()
    {
        return baseAccuracyModifier;
    }

    /**
     * Gets the burnout risk modifier for this typing style.
     * Multiply the standard burnout duration by this value.
     * 
     * @return burnout modifier (0.0 to 1.0)
     */
    public double getBurnoutRiskModifier()
    {
        return burnoutRiskModifier;
    }

    /**
     * Gets the speed modifier for this typing style.
     * Affects how quickly the typist progresses through the passage.
     * 
     * @return speed modifier (0.0 to 1.0)
     */
    public double getSpeedModifier()
    {
        return speedModifier;
    }

    /**
     * Returns a description of this typing style's effects.
     * 
     * @return detailed description
     */
    public String getDescription()
    {
        switch (this) {
            case TOUCH_TYPIST:
                return "High accuracy, moderate burnout risk, normal speed";
            case HUNT_AND_PECK:
                return "Lower accuracy, minimal burnout, slower speed";
            case PHONE_THUMBS:
                return "Lowest accuracy, moderate burnout, very slow";
            case VOICE_TO_TEXT:
                return "High accuracy, highest burnout risk, slower speed";
            default:
                return "";
        }
    }

    @Override
    public String toString()
    {
        return displayName;
    }
}
