import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Main GUI application for the typing race simulator.
 * Part II: Orchestrates Part I TypingRace and Typist classes with GUI display.
 * 
 * Design Flow:
 * 1. User configures race (passage, typist count, modifiers) in ConfigurationPanel
 * 2. TypingRaceGUI creates Part I Typist objects based on configuration
 * 3. TypingRaceGUI creates Part I TypingRace instance with dynamic seat count
 * 4. Typists are added to TypingRace using Part I's addTypist() method
 * 5. Race runs in background thread using Part I's startRace() method
 * 6. RacePanel reads live Typist state (progress, burnout, mistype) in real-time
 * 7. GUI updates display as Typists advance through passage
 * 8. Winner announced when any Typist finishes (Part I's logic)
 * 
 * @author Hasan Aliakbar Suleman
 * @version 1 : 29-04-2026
 */

public class TypingRaceGUI extends JFrame
{
    private ConfigurationPanel configPanel;
    private RacePanel racePanel;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private TypingRace currentRace;  // Reference to Part I TypingRace instance

    /**
     * Constructor for TypingRaceGUI.
     */
    public TypingRaceGUI()
    {
        setTitle("Typing Race Simulator - Part II (GUI)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 750);
        setLocationRelativeTo(null);
        setResizable(true);

        // Create card layout for switching between configuration and race panels
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create configuration panel
        configPanel = new ConfigurationPanel();
        configPanel.addStartButtonListener(e -> startRace());
        cardPanel.add(configPanel, "config");

        // Add card panel to frame
        add(cardPanel);

        setVisible(true);
    }

    /**
     * Handles start race action: validates config, creates Part I race, displays GUI.
     */
    private void startRace()
    {
        if (!configPanel.isConfigurationValid()) {
            return;
        }

        RaceConfiguration config = configPanel.getConfiguration();

        // STEP 1: Create Typist objects with modifiers applied
        ArrayList<Typist> typists = createTypists(config.getSeatCount(), config);

        // STEP 2: Create Part I TypingRace instance with dynamic seat count
        currentRace = new TypingRace(config.getPassage().getLength(), config.getSeatCount());

        // STEP 3: Add typists to Part I's TypingRace
        for (int i = 0; i < typists.size(); i++) {
            currentRace.addTypist(typists.get(i), i + 1);
        }

        // STEP 4: Create RacePanel to display the Part I Typist objects
        racePanel = new RacePanel(config.getPassage().getText(), typists);
        cardPanel.add(racePanel, "race");
        cardLayout.show(cardPanel, "race");

        // Display customization info for each typist
        displayCustomizationInfo(typists);

        // STEP 5: Run Part I's TypingRace on background thread
        SwingWorker<Typist, Void> worker = new SwingWorker<Typist, Void>()
        {
            @Override
            protected Typist doInBackground() throws Exception
            {
                // This runs Part I's simulation logic
                currentRace.startRace();

                // Find and return the winner from Part I's typists
                for (Typist t : typists) {
                    if (t.getProgress() >= config.getPassage().getLength()) {
                        return t;
                    }
                }
                return null;
            }

            @Override
            protected void done()
            {
                try {
                    Typist winner = get();
                    if (winner != null) {
                        showWinner(winner);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        worker.execute();

        // STEP 6: Update GUI display regularly as race progresses
        Timer updateTimer = new Timer(100, e -> {
            if (racePanel != null) {
                racePanel.updateDisplay();  // Reads live Typist states
            }
        });
        updateTimer.start();
    }

    /**
     * Creates Typist objects with modifiers applied.
     *
     * @param count number of typists
     * @param config the race configuration with modifiers
     * @return ArrayList of configured Typist objects
     */
    private ArrayList<Typist> createTypists(int count, RaceConfiguration config)
    {
        String[] names = {
            "TURBOFINGERS",
            "QWERTY_QUEEN",
            "HUNT_N_PECK",
            "SPEEDSTER",
            "ACCURATE_ANNA",
            "CASUAL_CODER"
        };

        double[] baseAccuracies = {
            0.85,
            0.60,
            0.30,
            0.75,
            0.90,
            0.50
        };

        ArrayList<Typist> typists = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            char symbol = (char)('①' + i);
            String name = names[i % names.length];
            double accuracy = baseAccuracies[i % baseAccuracies.length];

            // Apply difficulty modifiers
            if (config.isNightShift()) {
                accuracy *= 0.9;  // 10% reduction
            }

            if (config.isCaffeineMode()) {
                accuracy *= 1.15;  // 15% boost (but may cause burnout)
            }

            // Get customization for this typist
            TypistCustomization customization = config.getTypistCustomization(i);
            
            // If customization exists, override symbol and apply style modifiers
            if (customization != null) {
                symbol = customization.getSymbol();
                // Apply typing style modifier to accuracy
                accuracy *= customization.getTypingStyle().getAccuracyModifier();
            }

            // Create Part I Typist object with customization
            Typist typist = new Typist(symbol, name, accuracy, customization);
            typists.add(typist);
        }

        return typists;
    }

    /**
     * Shows winner announcement and prompts for new race.
     *
     * @param winner the winning Typist (Part I object)
     */
    private void showWinner(Typist winner)
    {
        String message = "🏆 " + winner.getName() + " wins the race!\n\n" +
                         "Final Accuracy: " + String.format("%.2f", winner.getAccuracy());

        int result = JOptionPane.showConfirmDialog(this,
            message + "\n\nStart a new race?",
            "Race Winner",
            JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            // Reset to configuration panel
            cardPanel.removeAll();
            configPanel = new ConfigurationPanel();
            configPanel.addStartButtonListener(e -> startRace());
            cardPanel.add(configPanel, "config");
            cardLayout.show(cardPanel, "config");
        }
    }

    /**
     * Displays customization info for each typist in console.
     *
     * @param typists the list of Typist objects with customizations
     */
    private void displayCustomizationInfo(ArrayList<Typist> typists)
    {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║              TYPIST CUSTOMIZATIONS AND ATTRIBUTES              ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        
        for (int i = 0; i < typists.size(); i++) {
            Typist t = typists.get(i);
            System.out.println("[Typist " + (i + 1) + "] " + t.getName() + " [" + t.getSymbol() + "]");
            System.out.println("Base Accuracy: " + String.format("%.0f%%", t.getAccuracy() * 100));
            
            if (t.getCustomization() != null) {
                TypistCustomization custom = t.getCustomization();
                System.out.println("  • Typing Style: " + custom.getTypingStyle().getDisplayName());
                System.out.println("    - " + custom.getTypingStyle().getDescription());
                System.out.println("  • Keyboard: " + custom.getKeyboardType().getDisplayName());
                System.out.println("    - " + custom.getKeyboardType().getDescription());
                
                if (!custom.getAccessories().isEmpty()) {
                    System.out.println("  • Accessories:");
                    for (Accessory acc : custom.getAccessories()) {
                        System.out.println("    - " + acc.getDisplayName() + ": " + acc.getDescription());
                    }
                } else {
                    System.out.println("  • Accessories: None");
                }
            }
            System.out.println();
        }
    }

    /**
     * Main method to launch the application.
     *
     * @param args command line arguments (unused)
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(TypingRaceGUI::new);
    }
}
