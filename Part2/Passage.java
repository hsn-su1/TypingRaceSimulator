/**
 * Represents a typing passage with pre-defined and custom options.
 * Each passage has text content and a difficulty level indicator.
 * 
 * @author Hasan Aliakbar Suleman
 * @version 1 : 29-04-2026
 */

public class Passage
{
    private String text;
    private String name;
    private int difficulty;  // 1=Easy, 2=Medium, 3=Hard

    /**
     * Constructor for a passage with text, name, and difficulty.
     *
     * @param text the passage text
     * @param name the name/title of the passage
     * @param difficulty difficulty level (1-3)
     */
    public Passage(String text, String name, int difficulty)
    {
        this.text = text;
        this.name = name;
        this.difficulty = difficulty;
    }

    /**
     * Returns the passage text.
     *
     * @return the text to type
     */
    public String getText()
    {
        return text;
    }

    /**
     * Returns the passage name.
     *
     * @return the passage name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the difficulty level.
     *
     * @return difficulty (1=Easy, 2=Medium, 3=Hard)
     */
    public int getDifficulty()
    {
        return difficulty;
    }

    /**
     * Returns the length of the passage in characters.
     *
     * @return passage length
     */
    public int getLength()
    {
        return text.length();
    }

    /**
     * Returns a string representation of the passage.
     *
     * @return name and length
     */
    @Override
    public String toString()
    {
        return name + " (" + getLength() + " chars)";
    }

    /**
     * Creates and returns a set of pre-defined passages.
     *
     * @return array of Passage objects
     */
    public static Passage[] getPredefinedPassages()
    {
        return new Passage[] {
            new Passage(
                "The quick brown fox jumps over the lazy dog.",
                "Short: Quick Fox",
                1
            ),
            new Passage(
                "In the heart of the city lies a sprawling park where ancient oak trees stand " +
                "as silent witnesses to centuries of human history and stories untold.",
                "Medium: Park Story",
                2
            ),
            new Passage(
                "Programming is the art of instructing computers to solve problems through a sequence " +
                "of logical steps. Every algorithm, every line of code, every function represents " +
                "the crystallization of human thought into executable instructions.",
                "Long: Programming Philosophy",
                3
            ),
            new Passage(
                "The early morning light filtered through the trees as the runner set off at dawn. " +
                "Each breath came steady and sure, each footfall rhythmic against the forest path.",
                "Medium: Early Run",
                2
            ),
            new Passage(
                "Java is a versatile object-oriented programming language that runs on the Java Virtual Machine, " +
                "enabling developers to build robust, scalable applications across multiple platforms.",
                "Medium: Java Intro",
                2
            )
        };
    }
}
