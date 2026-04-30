import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

/**
 * Stores customization options for a typist.
 * Includes typing style, keyboard type, symbol, color, and equipped accessories.
 * 
 * @author Hasan Aliakbar Suleman
 * @version 1 : 29-04-2026
 */

public class TypistCustomization
{
    private TypingStyle typingStyle;
    private KeyboardType keyboardType;
    private char symbol;
    private Color barColor;
    private Set<Accessory> accessories;
    private Sponsor sponsor;

    /**
     * Constructor with default customization.
     * Sets reasonable defaults for all customization options.
     */
    public TypistCustomization()
    {
        this.typingStyle = TypingStyle.TOUCH_TYPIST;
        this.keyboardType = KeyboardType.MEMBRANE;
        this.symbol = '●';
        this.barColor = Color.BLUE;
        this.accessories = new HashSet<>();
        this.sponsor = Sponsor.NONE;
    }

    /**
     * Full constructor for creating a fully customized typist.
     * 
     * @param style the typing style
     * @param keyboard the keyboard type
     * @param sym the symbol character
     * @param color the progress bar color
     */
    public TypistCustomization(TypingStyle style, KeyboardType keyboard, char sym, Color color)
    {
        this.typingStyle = style;
        this.keyboardType = keyboard;
        this.symbol = sym;
        this.barColor = color;
        this.accessories = new HashSet<>();
        this.sponsor = Sponsor.NONE;
    }

    // Getters

    /**
     * Gets the typing style.
     * 
     * @return the TypingStyle
     */
    public TypingStyle getTypingStyle()
    {
        return typingStyle;
    }

    /**
     * Gets the keyboard type.
     * 
     * @return the KeyboardType
     */
    public KeyboardType getKeyboardType()
    {
        return keyboardType;
    }

    /**
     * Gets the typist's symbol character.
     * 
     * @return the symbol char
     */
    public char getSymbol()
    {
        return symbol;
    }

    /**
     * Gets the progress bar color.
     * 
     * @return the Color
     */
    public Color getBarColor()
    {
        return barColor;
    }

    /**
     * Gets the current sponsor.
     *
     * @return the Sponsor
     */
    public Sponsor getSponsor()
    {
        return sponsor;
    }

    /**
     * Gets all equipped accessories.
     * 
     * @return unmodifiable set of accessories
     */
    public Set<Accessory> getAccessories()
    {
        return new HashSet<>(accessories);
    }

    /**
     * Checks if a specific accessory is equipped.
     * 
     * @param accessory the accessory to check
     * @return true if equipped
     */
    public boolean hasAccessory(Accessory accessory)
    {
        return accessories.contains(accessory);
    }

    // Setters

    /**
     * Sets the typing style.
     * 
     * @param style the new TypingStyle
     */
    public void setTypingStyle(TypingStyle style)
    {
        if (style != null) {
            this.typingStyle = style;
        }
    }

    /**
     * Sets the keyboard type.
     * 
     * @param keyboard the new KeyboardType
     */
    public void setKeyboardType(KeyboardType keyboard)
    {
        if (keyboard != null) {
            this.keyboardType = keyboard;
        }
    }

    /**
     * Sets the symbol character.
     * 
     * @param sym the new symbol
     */
    public void setSymbol(char sym)
    {
        this.symbol = sym;
    }

    /**
     * Sets the progress bar color.
     * 
     * @param color the new Color
     */
    public void setBarColor(Color color)
    {
        if (color != null) {
            this.barColor = color;
        }
    }

    /**
     * Sets the sponsor for this typist.
     *
     * @param sponsor the Sponsor
     */
    public void setSponsor(Sponsor sponsor)
    {
        if (sponsor != null) {
            this.sponsor = sponsor;
        }
    }

    /**
     * Equips an accessory.
     * 
     * @param accessory the accessory to equip
     */
    public void equipAccessory(Accessory accessory)
    {
        if (accessory != null) {
            accessories.add(accessory);
        }
    }

    /**
     * Removes an equipped accessory.
     * 
     * @param accessory the accessory to remove
     */
    public void removeAccessory(Accessory accessory)
    {
        accessories.remove(accessory);
    }

    /**
     * Clears all equipped accessories.
     */
    public void clearAccessories()
    {
        accessories.clear();
    }

    /**
     * Gets a summary of all customization effects.
     * 
     * @return string describing all effects
     */
    public String getSummary()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Style: ").append(typingStyle).append("\n");
        sb.append("Keyboard: ").append(keyboardType).append("\n");
        sb.append("Symbol: ").append(symbol).append("\n");
        
        if (!accessories.isEmpty()) {
            sb.append("Accessories: ");
            for (Accessory acc : accessories) {
                sb.append(acc).append(", ");
            }
            sb.setLength(sb.length() - 2);  // Remove trailing comma
            sb.append("\n");
        }

        if (sponsor != null && sponsor != Sponsor.NONE) {
            sb.append("Sponsor: ").append(sponsor.getDisplayName()).append(" - ").append(sponsor.getDescription()).append("\n");
        }
        
        return sb.toString();
    }

    @Override
    public String toString()
    {
        return "TypistCustomization{" +
                "style=" + typingStyle +
                ", keyboard=" + keyboardType +
                ", symbol=" + symbol +
                ", barColor=" + barColor +
                ", accessories=" + accessories +
                '}';
    }
}
