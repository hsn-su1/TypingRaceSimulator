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
    private StatisticsPanel statsPanel;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private TypingRace currentRace;  // Reference to Part I TypingRace instance
    private StatisticsManager statsManager;

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

        statsManager = new StatisticsManager();

        // Create card layout for switching between configuration and race panels
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create configuration panel
        configPanel = new ConfigurationPanel();
        configPanel.addStartButtonListener(e -> startRace());
        configPanel.addViewStatsButtonListener(e -> showStatistics());
        cardPanel.add(configPanel, "config");

        // Create statistics panel
        statsPanel = new StatisticsPanel(statsManager, () -> cardLayout.show(cardPanel, "config"));
        cardPanel.add(statsPanel, "stats");

        // Add card panel to frame
        add(cardPanel);

        setVisible(true);
    }

    /**
     * Shows the statistics panel.
     */
    private void showStatistics()
    {
        cardLayout.show(cardPanel, "stats");
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
        JScrollPane raceScrollPane = new JScrollPane(racePanel);
        raceScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        raceScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        cardPanel.add(raceScrollPane, "race");
        cardLayout.show(cardPanel, "race");

        // Display customization info for each typist
        displayCustomizationInfo(typists);

        // STEP 5: Run Part I's TypingRace on background thread
        SwingWorker<ArrayList<RaceResult>, Void> worker = new SwingWorker<ArrayList<RaceResult>, Void>()
        {
            @Override
            protected ArrayList<RaceResult> doInBackground() throws Exception
            {
                // This runs Part I's simulation logic and returns results
                return currentRace.startRace();
            }

            @Override
            protected void done()
            {
                try {
                    ArrayList<RaceResult> results = get();
                    if (results != null && !results.isEmpty()) {
                        // Save results to statistics
                        for (RaceResult result : results) {
                            statsManager.addRaceResult(result.getTypistName(), result);
                        }
                        statsPanel.refresh();
                        
                        // Find winner from results
                        RaceResult winnerResult = results.stream()
                            .filter(r -> r.getPosition() == 1)
                            .findFirst()
                            .orElse(null);
                            
                        if (winnerResult != null) {
                            Typist winner = getTypistByName(winnerResult.getTypistName());
                            showWinner(winner, results);
                        }
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
            if (customization == null) {
                customization = new TypistCustomization();
            }

            // Apply earned upgrades from previous earnings before race
            TypistStats existingStats = statsManager.getTypistStats(name);
            existingStats.applyAvailableUpgrades(customization);

            // If customization exists, override symbol and apply style modifiers
            if (customization != null) {
                symbol = customization.getSymbol();
                accuracy *= customization.getTypingStyle().getAccuracyModifier();
            }

            // Apply champion pressure if this typist is top-ranked
            TypistStats[] leaderboard = statsManager.getLeaderboardByPoints();
            if (leaderboard.length > 0 && leaderboard[0].getTypistName().equals(name) && leaderboard[0].getTotalRaces() > 0) {
                accuracy *= 0.95; // champions face higher expectations
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
     * @param results the race results for all typists
     */
    private void showWinner(Typist winner, ArrayList<RaceResult> results)
    {
        StringBuilder message = new StringBuilder();
        message.append("🏆 ").append(winner.getName()).append(" wins the race!\n\n");
        message.append("Final Accuracy: ").append(String.format("%.2f", winner.getAccuracy())).append("\n\n");
        message.append("=== RACE RESULTS ===\n");
        
        for (RaceResult result : results) {
            message.append(result.getSummary()).append("\n");
        }

        int option = JOptionPane.showConfirmDialog(this, message.toString(),
            "Race Complete", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            // Return to configuration
            cardLayout.show(cardPanel, "config");
            racePanel = null;
            currentRace = null;
        }
    }

    /**
     * Gets a typist by name from the current race.
     *
     * @param name the typist name
     * @return the Typist object, or null if not found
     */
    private Typist getTypistByName(String name) {
        if (currentRace == null) return null;
        for (Typist t : currentRace.getTypists()) {
            if (t != null && t.getName().equals(name)) {
                return t;
            }
        }
        return null;
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
     * Starts the graphical typing race application.
     * This method is provided as a minimal external entry point.
     */
    public static void startRaceGUI() {
        SwingUtilities.invokeLater(TypingRaceGUI::new);
    }

    /**
     * Main method to launch the application.
     *
     * @param args command line arguments (unused)
     */
    public static void main(String[] args)
    {
        startRaceGUI();
    }
}
