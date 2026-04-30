
/**
 * The Typist class represents individual competitors in the race.
 * 
 * Each typist has:
 * 
 * - A name and a unique symbol (like ①, ②, ③) to identify them
 * - An accuracy rating (0 to 1) — higher = faster but more likely to burn out
 * - Progress that goes forward on correct keystrokes and backward on mistakes (never below zero)
 * - A burnout state — they freeze for a few turns then recover
 * - Accuracy adjusts slightly — goes up after winning, down after burning out
 * - Customizable attributes: typing style, keyboard type, accessories
 * 
 * CUSTOMIZATION IMPACT:
 * Typing Style: Influences base accuracy and burnout risk
 * Keyboard Type: Affects speed and mistype rates
 * Accessories: Provide specialized effects (wrist support, energy drink, etc.)
 * Symbol & Color: Visual representation on screen
 * 
 * @author Hasan Aliakbar Suleman
 * @version 2 : 29-04-2026
 */

public class Typist
{
    // Instance variables of class Typist

    private String name;
    private char symbol;
    private int progress;
    private boolean isBurntOut;
    private int burnoutTurnsRemaining;
    private double accuracy;
    private boolean justMistyped;
    private TypistCustomization customization;
    private int raceProgress;  // Track position in passage for Energy Drink timing

    // Statistics tracking for current race
    private int totalKeystrokes;
    private int correctKeystrokes;
    private int burnoutCount;


    // Constructors of class Typist
 
    /**
     * Constructor with basic parameters (for backward compatibility).
     * Uses default customization.
     *
     * @param typistSymbol the symbol to represent this typist
     * @param typistName the name of the typist
     * @param typistAccuracy the base accuracy rating
     */
    public Typist(char typistSymbol, String typistName, double typistAccuracy)
    {
        symbol = typistSymbol;
        name = typistName;
        accuracy = typistAccuracy;
        progress = 0;
        isBurntOut = false;
        burnoutTurnsRemaining = 0;
        justMistyped = false;
        customization = new TypistCustomization();
        raceProgress = 0;
        totalKeystrokes = 0;
        correctKeystrokes = 0;
        burnoutCount = 0;
    }

    /**
     * Constructor with customization.
     *
     * @param typistSymbol the symbol to represent this typist
     * @param typistName the name of the typist
     * @param typistAccuracy the base accuracy rating
     * @param custom the TypistCustomization with style, keyboard, and accessories
     */
    public Typist(char typistSymbol, String typistName, double typistAccuracy, 
                  TypistCustomization custom)
    {
        symbol = typistSymbol;
        name = typistName;
        accuracy = typistAccuracy;
        progress = 0;
        isBurntOut = false;
        burnoutTurnsRemaining = 0;
        justMistyped = false;
        customization = custom != null ? custom : new TypistCustomization();
        raceProgress = 0;
        totalKeystrokes = 0;
        correctKeystrokes = 0;
        burnoutCount = 0;
    }


    // Methods of class Typist

    /**
     * Sets this typist into a burnout state for a given number of turns.
     * A burnt-out typist cannot type until their burnout has worn off.
     *
     * @param turns the number of turns the burnout will last
     */

    public void burnOut(int turns)
    {
        isBurntOut = true;
        burnoutTurnsRemaining = turns;
        burnoutCount++;
    }

    /**
     * Reduces the remaining burnout counter by one turn.
     * When the counter reaches zero, the typist recovers automatically.
     * Has no effect if the typist is not currently burnt out.
     */
    
    public void recoverFromBurnout()
    {
        if (isBurntOut) {
            if (burnoutTurnsRemaining > 0) {
                burnoutTurnsRemaining--;
            } else {
                isBurntOut = false;
            }
        }
    }

    /**
     * Returns the typist's accuracy rating.
     *
     * @return accuracy as a double between 0.0 and 1.0
     */

    public double getAccuracy()
    {
        return accuracy; 
    }

    /**
     * Returns the typist's current progress through the passage.
     * Progress is measured in characters typed correctly so far.
     * Note: this value can decrease if the typist mistypes.
     *
     * @return progress as a non-negative integer
     */
    
    public int getProgress()
    {
        return progress;
    }

    /**
     * Returns the name of the typist.
     *
     * @return the typist's name as a String
     */

    public String getName()
    {
        return name;
    }

    /**
     * Returns the character symbol used to represent this typist.
     *
     * @return the typist's symbol as a char
     */

    public char getSymbol()
    {
        return symbol;
    }

    /**
     * Returns the number of turns of burnout remaining.
     * Returns 0 if the typist is not currently burnt out.
     *
     * @return burnout turns remaining as a non-negative integer
     */

    public int getBurnoutTurnsRemaining()
    {
        return burnoutTurnsRemaining;
    }

    /**
     * Resets the typist to their initial state, ready for a new race.
     * Progress returns to zero, burnout is cleared entirely.
     */

    public void resetToStart()
    {
        progress = 0;
        burnoutTurnsRemaining = 0;
        isBurntOut = false;
        justMistyped = false;
        raceProgress = 0;
        totalKeystrokes = 0;
        correctKeystrokes = 0;
        burnoutCount = 0;
    }

    /**
     * Returns true if this typist is currently burnt out, false otherwise.
     *
     * @return true if burnt out
     */

    public boolean isBurntOut()
    {
        return isBurntOut;
    }

    /**
     * Returns true if this typist just mistyped on their last turn, false otherwise.
     *
     * @return true if just mistyped
     */

    public boolean justMistyped()
    {
        return justMistyped;
    }

    /**
     * Clears the mistype flag, typically called at the start of each turn.
     * This allows the display to only show the mistype indicator for the turn
     * in which the mistype occurred.
     */

    public void clearMistypeFlag()
    {
        justMistyped = false;
    }

    /**
     * Advances the typist forward by one character along the passage.
     * Should only be called when the typist is not burnt out.
     */

    public void typeCharacter()
    {
        if (!isBurntOut) {
            progress++;
            totalKeystrokes++;
            correctKeystrokes++;
        }
    }

    /**
     * Moves the typist backwards by a given number of characters (a mistype).
     * Progress cannot go below zero — the typist cannot slide off the start.
     *
     * @param amount the number of characters to slide back (must be positive)
     */

    public void slideBack(int amount)
    {
        progress -= amount;
        if (progress < 0) {
            progress = 0;
        }
        justMistyped = true;
        totalKeystrokes++;
    }

    /**
     * Sets the accuracy rating of the typist.
     * Values below 0.0 should be set to 0.0; values above 1.0 should be set to 1.0.
     *
     * @param newAccuracy the new accuracy rating
     */

    public void setAccuracy(double newAccuracy)
    {
        if (newAccuracy < 0.0) {
            accuracy = 0.0;
        } else if (newAccuracy > 1.0) {
            accuracy = 1.0;
        } else {
            accuracy = Math.round(newAccuracy * 100.0) / 100.0;
        }
    }

    /**
     * Sets the symbol used to represent this typist.
     *
     * @param newSymbol the new symbol character
     */

    public void setSymbol(char newSymbol)
    {
        symbol = newSymbol;
    }

    /**
     * Gets the customization for this typist.
     *
     * @return the TypistCustomization
     */
    public TypistCustomization getCustomization()
    {
        return customization;
    }

    /**
     * Sets the customization for this typist.
     *
     * @param custom the new TypistCustomization
     */
    public void setCustomization(TypistCustomization custom)
    {
        if (custom != null) {
            customization = custom;
            // Update symbol and color from customization
            symbol = custom.getSymbol();
        }
    }

    /**
     * Applies the typing style modifiers to the accuracy.
     * Typing style affects the base accuracy of the typist.
     *
     * @return the modified accuracy
     */
    public double getAccuracyWithStyleModifier()
    {
        if (customization == null) return accuracy;
        return accuracy * customization.getTypingStyle().getAccuracyModifier();
    }

    /**
     * Applies the keyboard type speed modifier.
     * Used to adjust progress advancement based on keyboard.
     *
     * @return the speed multiplier
     */
    public double getSpeedModifier()
    {
        if (customization == null) return 1.0;
        return customization.getKeyboardType().getSpeedModifier();
    }

    /**
     * Gets the mistype rate modifier based on keyboard and accessories.
     * Lower values mean fewer mistypes.
     *
     * @return the mistype rate multiplier
     */
    public double getMistypeRateModifier()
    {
        if (customization == null) return 1.0;
        
        double modifier = customization.getKeyboardType().getMistypeRateModifier();
        
        // Apply accessory modifiers
        if (customization.hasAccessory(Accessory.NOISE_CANCELLING_HEADPHONES)) {
            modifier *= Accessory.NOISE_CANCELLING_HEADPHONES.getMistypeRateModifier();
        }
        
        return modifier;
    }

    /**
     * Gets the burnout duration modifier based on accessories.
     * Applies to burnout turns remaining.
     *
     * @return the burnout duration multiplier
     */
    public double getBurnoutModifier()
    {
        if (customization == null) return 1.0;
        
        double modifier = 1.0;
        
        if (customization.hasAccessory(Accessory.WRIST_SUPPORT)) {
            modifier *= Accessory.WRIST_SUPPORT.getBurnoutModifier();
        }
        
        return modifier;
    }

    /**
     * Gets accuracy modifier for Energy Drink accessory based on race progress.
     * First half of race: +15% accuracy
     * Second half: -10% accuracy
     *
     * @param passageLength total length of the passage
     * @return accuracy multiplier for Energy Drink effect
     */
    public double getEnergyDrinkModifier(int passageLength)
    {
        if (!customization.hasAccessory(Accessory.ENERGY_DRINK)) {
            return 1.0;
        }
        
        if (passageLength <= 0) return 1.0;
        
        int midpoint = passageLength / 2;
        if (progress < midpoint) {
            return Accessory.ENERGY_DRINK.getFirstHalfAccuracyModifier();
        } else {
            return Accessory.ENERGY_DRINK.getSecondHalfAccuracyModifier();
        }
    }

    /**
     * Gets a summary of all customization effects for display.
     *
     * @return formatted string describing effects
     */
    public String getCustomizationSummary()
    {
        if (customization == null) return "No customization";
        return customization.getSummary();
    }

    /**
     * Gets the total keystrokes made in the current race.
     *
     * @return total keystrokes
     */
    public int getTotalKeystrokes() {
        return totalKeystrokes;
    }

    /**
     * Gets the correct keystrokes made in the current race.
     *
     * @return correct keystrokes
     */
    public int getCorrectKeystrokes() {
        return correctKeystrokes;
    }

    /**
     * Gets the burnout count in the current race.
     *
     * @return burnout count
     */
    public int getBurnoutCount() {
        return burnoutCount;
    }

    /**
     * Gets the accuracy percentage for the current race.
     *
     * @return accuracy percentage (0-100)
     */
    public double getRaceAccuracyPercentage() {
        if (totalKeystrokes == 0) return 0.0;
        return (correctKeystrokes * 100.0) / totalKeystrokes;
    }

}
