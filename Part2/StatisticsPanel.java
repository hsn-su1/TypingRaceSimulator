import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.*;

/**
 * Panel for viewing and analyzing typing race statistics.
 * Shows personal bests, historical data, and comparison views.
 *
 * @author Hasan Aliakbar Suleman
 * @version 1.0
 */
public class StatisticsPanel extends JPanel {
    private StatisticsManager statsManager;
    private JTextArea statsDisplay;
    private JComboBox<String> typistSelector;
    private JComboBox<String> metricSelector;
    private JButton viewStatsButton;
    private JButton compareButton;
    private JButton backButton;

    /**
     * Constructor for StatisticsPanel.
     *
     * @param statsManager the statistics manager
     * @param backAction action to go back to main menu
     */
    public StatisticsPanel(StatisticsManager statsManager, Runnable backAction) {
        this.statsManager = statsManager;

        setLayout(new BorderLayout());

        // Top panel with controls
        JPanel controlPanel = new JPanel(new FlowLayout());

        typistSelector = new JComboBox<>();
        updateTypistSelector();

        metricSelector = new JComboBox<>(new String[]{
            "Overview", "Detailed Stats", "Race History", "WPM Trend"
        });

        viewStatsButton = new JButton("View Stats");
        viewStatsButton.addActionListener(e -> viewStats());

        compareButton = new JButton("Compare Typists");
        compareButton.addActionListener(e -> showComparison());

        backButton = new JButton("Back");
        backButton.addActionListener(e -> backAction.run());

        controlPanel.add(new JLabel("Typist:"));
        controlPanel.add(typistSelector);
        controlPanel.add(new JLabel("View:"));
        controlPanel.add(metricSelector);
        controlPanel.add(viewStatsButton);
        controlPanel.add(compareButton);
        controlPanel.add(backButton);

        add(controlPanel, BorderLayout.NORTH);

        // Main display area
        statsDisplay = new JTextArea(20, 60);
        statsDisplay.setEditable(false);
        statsDisplay.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(statsDisplay);
        add(scrollPane, BorderLayout.CENTER);

        // Initial display
        showOverview();
    }

    /**
     * Updates the typist selector with current typists.
     */
    private void updateTypistSelector() {
        typistSelector.removeAllItems();
        Map<String, TypistStats> allStats = statsManager.getAllStats();
        if (allStats.isEmpty()) {
            typistSelector.addItem("No data available");
        } else {
            for (String name : allStats.keySet()) {
                typistSelector.addItem(name);
            }
        }
    }

    /**
     * Views stats for the selected typist and metric.
     */
    private void viewStats() {
        String selectedTypist = (String) typistSelector.getSelectedItem();
        String selectedMetric = (String) metricSelector.getSelectedItem();

        if (selectedTypist == null || "No data available".equals(selectedTypist)) {
            statsDisplay.setText("No statistics available yet.\nRun some races to generate data!");
            return;
        }

        TypistStats stats = statsManager.getTypistStats(selectedTypist);

        switch (selectedMetric) {
            case "Overview":
                statsDisplay.setText(stats.getSummary());
                break;
            case "Detailed Stats":
                statsDisplay.setText(stats.getDetailedString());
                break;
            case "Race History":
                showRaceHistory(stats);
                break;
            case "WPM Trend":
                showWPMTrend(stats);
                break;
        }
    }

    /**
     * Shows the race history for a typist.
     */
    private void showRaceHistory(TypistStats stats) {
        StringBuilder sb = new StringBuilder();
        sb.append("Race History for ").append(stats.getTypistName()).append("\n\n");

        ArrayList<RaceResult> history = stats.getRaceHistory();
        if (history.isEmpty()) {
            sb.append("No races completed yet.");
        } else {
            for (int i = 0; i < history.size(); i++) {
                sb.append("Race ").append(i + 1).append(": ").append(history.get(i).getSummary()).append("\n");
            }
        }

        statsDisplay.setText(sb.toString());
    }

    /**
     * Shows WPM trend over time.
     */
    private void showWPMTrend(TypistStats stats) {
        StringBuilder sb = new StringBuilder();
        sb.append("WPM Trend for ").append(stats.getTypistName()).append("\n\n");

        ArrayList<RaceResult> history = stats.getRaceHistory();
        if (history.isEmpty()) {
            sb.append("No races completed yet.");
        } else {
            sb.append("Race | WPM  | Position | Accuracy\n");
            sb.append("-----|------|----------|----------\n");
            for (int i = 0; i < history.size(); i++) {
                RaceResult r = history.get(i);
                sb.append(String.format("%4d | %4.1f | %8d | %7.1f%%\n",
                    i + 1, r.getWpm(), r.getPosition(), r.getAccuracyPercentage()));
            }
        }

        statsDisplay.setText(sb.toString());
    }

    /**
     * Shows overview of all typists.
     */
    private void showOverview() {
        statsDisplay.setText(statsManager.getAllStatsSummary());
    }

    /**
     * Shows comparison between typists.
     */
    private void showComparison() {
        Map<String, TypistStats> allStats = statsManager.getAllStats();
        if (allStats.size() < 2) {
            JOptionPane.showMessageDialog(this, "Need at least 2 typists with data for comparison.");
            return;
        }

        // Simple comparison dialog
        StringBuilder comparison = new StringBuilder();
        comparison.append("=== TYPIST COMPARISON ===\n\n");

        for (TypistStats stats : allStats.values()) {
            comparison.append(stats.getSummary()).append("\n");
        }

        JTextArea comparisonArea = new JTextArea(comparison.toString());
        comparisonArea.setEditable(false);
        comparisonArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(comparisonArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        JOptionPane.showMessageDialog(this, scrollPane, "Typist Comparison",
            JOptionPane.INFORMATION_MESSAGE);
    }
}