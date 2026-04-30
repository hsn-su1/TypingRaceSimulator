import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

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
    private static final int MIN_LANE_HEIGHT = 45;
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
        setPreferredSize(new Dimension(900, PADDING * 2 + MIN_LANE_HEIGHT * typists.size() + 80));
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
            y += drawLane(g2, typist, y);
            y += 10;
        }
    }

    /**
     * Draws a single typist's lane showing passage progress.
     *
     * @param g2 the Graphics2D object
     * @param typist the Part I Typist object
     * @param y the y-coordinate
     * @return total height used by this lane
     */
    private int drawLane(Graphics2D g2, Typist typist, int y)
    {
        int x = PADDING;
        int laneWidth = getWidth() - 2 * PADDING;

        g2.setFont(new Font("Monospaced", Font.PLAIN, 12));
        FontMetrics metrics = g2.getFontMetrics();
        int lineHeight = metrics.getHeight();
        int lineWidth = laneWidth - 10;
        int maxCharsPerLine = Math.max(1, lineWidth / CHAR_WIDTH);
        int totalLines = (passage.length() + maxCharsPerLine - 1) / maxCharsPerLine;
        int laneHeight = Math.max(MIN_LANE_HEIGHT, totalLines * lineHeight + 10);

        // Draw background lane
        g2.setColor(new Color(220, 220, 220));
        g2.fillRect(x, y, laneWidth, laneHeight);
        g2.setColor(new Color(100, 100, 100));
        g2.setStroke(new BasicStroke(1.0f));
        g2.drawRect(x, y, laneWidth, laneHeight);

        int textX = x + 5;
        int textY = y + lineHeight;
        int progress = typist.getProgress();

        // Get custom color from customization if available
        Color progressColor = new Color(76, 175, 80);  // Default green
        Color cursorColor = new Color(33, 150, 243);   // Default blue
        if (typist.getCustomization() != null) {
            progressColor = typist.getCustomization().getBarColor();
            int r = Math.max(progressColor.getRed() - 50, 0);
            int g = Math.max(progressColor.getGreen() - 50, 0);
            int b = Math.max(progressColor.getBlue() - 50, 0);
            cursorColor = new Color(r, g, b);
        }

        for (int i = 0; i < passage.length(); i++) {
            if (i > 0 && i % maxCharsPerLine == 0) {
                textX = x + 5;
                textY += lineHeight;
            }

            char c = passage.charAt(i);

            if (i < progress) {
                g2.setColor(progressColor);
                g2.fillRect(textX, textY - lineHeight + 3, CHAR_WIDTH, lineHeight - 2);
                g2.setColor(Color.WHITE);
            } else if (i == progress) {
                g2.setColor(cursorColor);
                g2.fillRect(textX, textY - lineHeight + 3, CHAR_WIDTH, lineHeight - 2);
                g2.setColor(Color.WHITE);
            } else {
                g2.setColor(Color.BLACK);
            }

            g2.drawString(String.valueOf(c), textX, textY);
            textX += CHAR_WIDTH;
        }

        g2.setFont(new Font("Arial", Font.PLAIN, 10));
        g2.setColor(Color.BLACK);

        String info = typist.getName() + " [" + typist.getSymbol() + "] " +
                      "Accuracy: " + String.format("%.0f%%", typist.getAccuracy() * 100) + " | " +
                      "Progress: " + progress + "/" + passage.length();

        if (typist.isBurntOut()) {
            info += " [BURNT OUT - " + typist.getBurnoutTurnsRemaining() + " turns left]";
            g2.setColor(new Color(255, 69, 0));
        } else if (typist.justMistyped()) {
            info += " [MISTYPED]";
            g2.setColor(new Color(244, 67, 54));
        }

        int infoY = y + laneHeight + 15;
        g2.drawString(info, x, infoY);

        return laneHeight + 25;
    }
}
