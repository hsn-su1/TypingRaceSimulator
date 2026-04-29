import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * GUI panel for displaying the visual typing race.
 * Part II: Displays Part I Typist objects in real-time as they race.
 * Shows character-by-character progress through the passage.
 * 
 * @author Hasan Aliakbar Suleman
 * @version 1 : 29-04-2026
 */

public class RacePanel extends JPanel
{
    private String passage;
    private ArrayList<Typist> typists;
    private static final int PADDING = 20;
    private static final int LANE_HEIGHT = 70;
    private static final int CHAR_WIDTH = 8;

    /**
     * Constructor for RacePanel.
     *
     * @param passage the passage text to display
     * @param typists the ArrayList of Part I Typist objects from the race
     */
    public RacePanel(String passage, ArrayList<Typist> typists)
    {
        this.passage = passage;
        this.typists = typists;
        setBackground(new Color(245, 245, 245));
        setPreferredSize(new Dimension(900, PADDING * 2 + LANE_HEIGHT * typists.size() + 40));
    }

    /**
     * Updates the display (call this when race updates).
     */
    public void updateDisplay()
    {
        repaint();
    }

    /**
     * Paints the race display using actual Part I Typist data.
     *
     * @param g the Graphics object
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int y = PADDING;

        // Draw title
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.setColor(Color.BLACK);
        g2.drawString("TYPING RACE - Passage Length: " + passage.length() + " characters", PADDING, y);
        y += 30;

        // Draw each typist's lane using Part I Typist data
        for (Typist typist : typists) {
            drawLane(g2, typist, y);
            y += LANE_HEIGHT;
        }
    }

    /**
     * Draws a single typist's lane showing passage progress.
     *
     * @param g2 the Graphics2D object
     * @param typist the Part I Typist object
     * @param y the y-coordinate
     */
    private void drawLane(Graphics2D g2, Typist typist, int y)
    {
        int x = PADDING;
        int laneWidth = getWidth() - 2 * PADDING;

        // Draw background lane
        g2.setColor(new Color(220, 220, 220));
        g2.fillRect(x, y, laneWidth, 45);
        g2.setColor(new Color(100, 100, 100));
        g2.setStroke(new BasicStroke(1.0f));
        g2.drawRect(x, y, laneWidth, 45);

        // Draw passage text with progress highlighting
        g2.setFont(new Font("Monospaced", Font.PLAIN, 12));
        int textX = x + 5;
        int textY = y + 28;

        int progress = typist.getProgress();

        for (int i = 0; i < passage.length(); i++) {
            char c = passage.charAt(i);

            // Completed characters in green
            if (i < progress) {
                g2.setColor(new Color(76, 175, 80));  // Green
                g2.fillRect(textX, textY - 12, CHAR_WIDTH, 16);
                g2.setColor(Color.WHITE);
            }
            // Current character (cursor) in blue
            else if (i == progress) {
                g2.setColor(new Color(33, 150, 243));  // Blue
                g2.fillRect(textX, textY - 12, CHAR_WIDTH, 16);
                g2.setColor(Color.WHITE);
            }
            // Future characters in black
            else {
                g2.setColor(Color.BLACK);
            }

            g2.drawString(String.valueOf(c), textX + 2, textY);
            textX += CHAR_WIDTH;
        }

        // Draw typist info below lane
        g2.setFont(new Font("Arial", Font.PLAIN, 10));
        g2.setColor(Color.BLACK);

        String info = typist.getName() + " [" + typist.getSymbol() + "] " +
                      "Accuracy: " + String.format("%.0f%%", typist.getAccuracy() * 100) + " | " +
                      "Progress: " + progress + "/" + passage.length();

        if (typist.isBurntOut()) {
            info += " [BURNT OUT - " + typist.getBurnoutTurnsRemaining() + " turns left]";
            g2.setColor(new Color(255, 69, 0));  // Orange-red
        } else if (typist.justMistyped()) {
            info += " [MISTYPED]";
            g2.setColor(new Color(244, 67, 54));  // Red
        }

        g2.drawString(info, PADDING, y + 62);
    }
}
