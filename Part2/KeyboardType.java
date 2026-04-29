/**
 * Enumeration of different keyboard types.
 * Each keyboard type affects speed and mistype rates.
 * 
 * @author Hasan Aliakbar Suleman
 * @version 1 : 29-04-2026
 */

public enum KeyboardType
{
    MECHANICAL("Mechanical Keyboard", 1.1, 0.85),
        // Faster feedback, lower mistype rate
    
    MEMBRANE("Membrane Keyboard", 1.0, 1.0),
        // Standard baseline
    
    TOUCHSCREEN("Touchscreen", 0.8, 1.3),
        // Slower, higher mistype rate
    
    STENOGRAPHY("Stenography", 1.2, 0.70);
        // Fastest, lowest mistype rate (specialized)

    private final String displayName;
    private final double speedModifier;         // Affects character progression speed
    private final double mistypeRateModifier;   // Multiplier for mistype chance

    /**
     * Constructor for KeyboardType.
     * 
     * @param displayName friendly name for UI display
     * @param speedMod speed modifier (affects how fast typist progresses)
     * @param mistypeMod mistype rate modifier (multiplied against mistype chance)
     */
    KeyboardType(String displayName, double speedMod, double mistypeMod)
    {
        this.displayName = displayName;
        this.speedModifier = speedMod;
        this.mistypeRateModifier = mistypeMod;
    }

    /**
     * Gets the display name of this keyboard type.
     * 
     * @return friendly name for UI
     */
    public String getDisplayName()
    {
        return displayName;
    }

    /**
     * Gets the speed modifier for this keyboard type.
     * Affects character progression rate.
     * 
     * @return speed modifier (typically 0.8 to 1.2)
     */
    public double getSpeedModifier()
    {
        return speedModifier;
    }

    /**
     * Gets the mistype rate modifier for this keyboard type.
     * Multiply the baseline mistype chance by this value.
     * 
     * @return mistype modifier (typically 0.7 to 1.3)
     */
    public double getMistypeRateModifier()
    {
        return mistypeRateModifier;
    }

    /**
     * Returns a description of this keyboard type's effects.
     * 
     * @return detailed description
     */
    public String getDescription()
    {
        switch (this) {
            case MECHANICAL:
                return "Faster speed, lower mistype rate";
            case MEMBRANE:
                return "Standard baseline speed and accuracy";
            case TOUCHSCREEN:
                return "Slower speed, higher mistype rate";
            case STENOGRAPHY:
                return "Fastest speed, lowest mistype rate";
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
