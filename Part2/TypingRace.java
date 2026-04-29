import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * A typing race simulation. Multiple typists (2-6) race to complete a passage of text,
 * advancing character by character — or sliding backwards when they mistype.
 *
 * @author TyPosaurus
 * @version 2.0 - Modified to support 2-6 typists
 */

public class TypingRace
{
    private int passageLength;   // Total characters in the passage to type
    private ArrayList<Typist> typists;  // Dynamic list of typists

    // Accuracy thresholds for mistype and burnout events
    private static final double MISTYPE_BASE_CHANCE = 0.15;
    private static final int    SLIDE_BACK_AMOUNT   = 1;
    private static final int    BURNOUT_DURATION     = 2;

    /**
     * Constructor for objects of class TypingRace.
     * Sets up the race with a passage of the given length and seat count.
     *
     * @param passageLength the number of characters in the passage to type
     * @param seatCount the number of typists (2-6)
     */

    public TypingRace(int passageLength, int seatCount)
    {
        this.passageLength = passageLength;
        this.typists = new ArrayList<>();
        
        // Validate and set seat count
        if (seatCount < 2) seatCount = 2;
        if (seatCount > 6) seatCount = 6;
        
        // Initialize seats with null placeholders
        for (int i = 0; i < seatCount; i++) {
            typists.add(null);
        }
    }

    /**
     * Deprecated constructor for backward compatibility (defaults to 3 seats).
     *
     * @param passageLength the number of characters in the passage to type
     */
    public TypingRace(int passageLength)
    {
        this(passageLength, 3);
    }

    /**
     * Seats a typist at the given seat number (1-based index).
     *
     * @param theTypist  the typist to seat
     * @param seatNumber the seat to place them in (1 to seatCount)
     */

    public void addTypist(Typist theTypist, int seatNumber)
    {
        if (seatNumber >= 1 && seatNumber <= typists.size()) {
            typists.set(seatNumber - 1, theTypist);
        } else {
            System.out.println("Cannot seat typist at seat " + seatNumber + " — there is no such seat.");
        }
    }

    /**
     * Returns the typist at a given seat number.
     *
     * @param seatNumber the seat number (1-based)
     * @return the Typist at that seat, or null if empty
     */
    public Typist getTypist(int seatNumber)
    {
        if (seatNumber >= 1 && seatNumber <= typists.size()) {
            return typists.get(seatNumber - 1);
        }
        return null;
    }

    /**
     * Returns all typists in the race.
     *
     * @return ArrayList of all typists
     */
    public ArrayList<Typist> getTypists()
    {
        return typists;
    }

    /**
     * Returns the number of seats in the race.
     *
     * @return seat count
     */
    public int getSeatCount()
    {
        return typists.size();
    }

    /**
     * Starts the typing race.
     * All typists are reset to the beginning, then the simulation runs
     * turn by turn until one typist completes the full passage.
     */

    public void startRace()
    {
        // Validate all seats are filled
        for (int i = 0; i < typists.size(); i++) {
            if (typists.get(i) == null) {
                System.err.println("Error: Not all seats are filled!");
                return;
            }
        }

        boolean finished = false;

        // Reset all typists to the start of the passage
        for (Typist t : typists) {
            t.resetToStart();
        }

        // Store original accuracies before the race begins
        double[] originalAccuracies = new double[typists.size()];
        for (int i = 0; i < typists.size(); i++) {
            originalAccuracies[i] = typists.get(i).getAccuracy();
        }

        while (!finished)
        {
            // Advance each typist by one turn
            for (Typist t : typists) {
                advanceTypist(t);
            }

            // Print the current state of the race
            printRace();

            // Check if any typist has finished the passage
            for (Typist t : typists) {
                if (raceFinishedBy(t)) {
                    finished = true;
                    break;
                }
            }

            // Wait 200ms between turns so the animation is visible
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (Exception e) {}
        }

        // Find the winner and display results
        Typist winner = null;
        double originalAccuracy = 0;
        
        for (int i = 0; i < typists.size(); i++) {
            Typist t = typists.get(i);
            if (raceFinishedBy(t)) {
                winner = t;
                originalAccuracy = originalAccuracies[i];
                break;
            }
        }

        // Winning slightly increases accuracy
        if (winner != null) {
            winner.setAccuracy(winner.getAccuracy() + 0.01);
            System.out.println("And the winner is... " + winner.getName() + "!");
            System.out.println("Final accuracy: " + winner.getAccuracy() + " (changed from " + originalAccuracy + ")");
        }
    }

    /**
     * Simulates one turn for a typist.
     *
     * If the typist is burnt out, they recover one turn's worth and skip typing.
     * Otherwise:
     *   - They may type a character (advancing progress) based on their accuracy.
     *   - They may mistype (sliding back) — the chance of a mistype should decrease
     *     for more accurate typists.
     *   - They may burn out — more likely for very high-accuracy typists
     *     who are pushing themselves too hard.
     *
     * @param theTypist the typist to advance
     */

    private void advanceTypist(Typist theTypist)
    {
        // Clear the mistype flag from the previous turn
        theTypist.clearMistypeFlag();

        if (theTypist.isBurntOut())
        {
            // Recovering from burnout — skip this turn
            theTypist.recoverFromBurnout();
            return;
        }

        // Attempt to type a character
        if (Math.random() < theTypist.getAccuracy())
        {
            theTypist.typeCharacter();
        }

        // Mistype check — less accurate typists are more likely to mistype
        if (Math.random() < (1 - theTypist.getAccuracy()) * MISTYPE_BASE_CHANCE)
        {
            theTypist.slideBack(SLIDE_BACK_AMOUNT);
        }

        // Burnout check — pushing too hard increases burnout risk
        // (probability scales with accuracy squared, capped at ~0.05)
        if (Math.random() < 0.02 * theTypist.getAccuracy() * theTypist.getAccuracy())
        {
            theTypist.burnOut(BURNOUT_DURATION);
            theTypist.setAccuracy(theTypist.getAccuracy() - 0.01); // Burnout causes a small accuracy drop
        }
    }

    /**
     * Returns true if the given typist has completed the full passage.
     *
     * @param theTypist the typist to check
     * @return true if their progress has reached or passed the passage length
     */

    private boolean raceFinishedBy(Typist theTypist)
    {
         return theTypist.getProgress() >= passageLength;
    }

    /**
     * Prints the current state of the race to the terminal.
     * Shows each typist's position along the passage, burnout state,
     * and a WPM estimate based on current progress.
     */
    private void printRace()
    {
        System.out.print('\u000C'); // Clear terminal

        System.out.println("  TYPING RACE - passage length: " + passageLength + " chars");
        multiplePrint('=', passageLength + 3);
        System.out.println();

        for (Typist t : typists) {
            printSeat(t);
            System.out.println();
        }

        multiplePrint('=', passageLength + 3);
        System.out.println();
        System.out.println("  [~] = burnt out    [<] = just mistyped");
    }

    /**
     * Prints a single typist's lane.
     *
     * Examples:
     *   |          ⌨           | TURBOFINGERS (Accuracy: 0.85)
     *   |    [zz]              | HUNT_N_PECK  (Accuracy: 0.40) BURNT OUT (2 turns)
     *
     *
     * @param theTypist the typist whose lane to print
     */
    private void printSeat(Typist theTypist)
    {
        int spacesBefore = theTypist.getProgress();
        int spacesAfter  = passageLength - theTypist.getProgress();

        System.out.print('|');
        multiplePrint(' ', spacesBefore);

        // Always show the typist's symbol so they can be identified on screen.
        // Append ~ when burnt out so the state is visible without hiding identity.
        // Append [<] when they just mistyped
        System.out.print(theTypist.getSymbol());
        if (theTypist.isBurntOut()) {
            System.out.print('~');
            spacesAfter--; // symbol + ~ together take two characters
        } else if (theTypist.justMistyped()) {
            System.out.print("<");
            spacesAfter--; // symbol + < together take two characters
        }

        multiplePrint(' ', spacesAfter);
        System.out.print('|');
        System.out.print(' ');

        // Print name and accuracy
        if (theTypist.isBurntOut())
        {
            System.out.print(theTypist.getName()
                + " (Accuracy: " + theTypist.getAccuracy() + ")"
                + " BURNT OUT (" + theTypist.getBurnoutTurnsRemaining() + " turns)");
        } else if (theTypist.justMistyped())
        {
             System.out.print(theTypist.getName()
                + " (Accuracy: " + theTypist.getAccuracy() + ")"
                + " JUST MISTYPED");
        }
        else
        {
            System.out.print(theTypist.getName()
                + " (Accuracy: " + theTypist.getAccuracy() + ")");
        }
    }

    /**
     * Prints a character a given number of times.
     *
     * @param aChar the character to print
     * @param times how many times to print it
     */
    private void multiplePrint(char aChar, int times)
    {
        int i = 0;
        while (i < times)
        {
            System.out.print(aChar);
            i = i + 1;
        }
    }

    public static void main(String[] args) {
        TypingRace race = new TypingRace(40);
        race.addTypist(new Typist('A', "TURBOFINGERS", 0.85), 1);
        race.addTypist(new Typist('B', "QWERTY_QUEEN",  0.60), 2);
        race.addTypist(new Typist('C', "HUNT_N_PECK",   0.30), 3);
        race.startRace();
    }

}

