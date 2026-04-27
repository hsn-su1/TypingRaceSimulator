/**
 * The Typist class represents individual competitors in the race.
 * 
 * Each one has :
 * 
 * A name and a unique symbol (like ①, ②, ③) to identify them
 * An accuracy rating (0 to 1) — higher = faster but more likely to burn out
 * Progress that goes forward on correct keystrokes and backward on mistakes (never below zero)
 * A burnout state — they freeze for a few turns then recover
 * Accuracy adjusts slightly — goes up after winning, down after burning out
 * 
 * @author Hasan Aliakbar Suleman
 * @version 27-04-2026
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


    // Constructor of class Typist
 
    public Typist(char typistSymbol, String typistName, double typistAccuracy)
    {
        symbol = typistSymbol;
        name = typistName;
        accuracy = typistAccuracy;
        progress = 0;
        isBurntOut = false;
        burnoutTurnsRemaining = 0;
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
     * Advances the typist forward by one character along the passage.
     * Should only be called when the typist is not burnt out.
     */

    public void typeCharacter()
    {
        if (!isBurntOut) {
            progress++;
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
            accuracy = newAccuracy;
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

}
