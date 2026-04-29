import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * GUI panel for interactive race configuration.
 * Part II: Allows users to select passage, seat count, and difficulty modifiers.
 * Settings are collected and passed to create a Part I TypingRace instance.
 * 
 * @author Hasan Aliakbar Suleman
 * @version 1 : 29-04-2026
 */

public class ConfigurationPanel extends JPanel
{
    private JComboBox<Passage> passageCombo;
    private JTextArea customPassageArea;
    private JSpinner seatCountSpinner;
    private JCheckBox autocorrectCheckbox;
    private JCheckBox caffeineCheckbox;
    private JCheckBox nightShiftCheckbox;
    private JButton startButton;
    private JButton customizeButton;
    private RaceConfiguration config;
    private TypistCustomizationPanel[] customizationPanels;

    /**
     * Constructor for ConfigurationPanel.
     */
    public ConfigurationPanel()
    {
        this.config = new RaceConfiguration();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(240, 240, 240));

        add(createTitleLabel(), BorderLayout.NORTH);
        add(createControlsPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    /**
     * Creates the title label.
     *
     * @return JLabel with title
     */
    private JLabel createTitleLabel()
    {
        JLabel title = new JLabel("Typing Race Configuration");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        return title;
    }

    /**
     * Creates the main controls panel.
     *
     * @return JPanel with all controls
     */
    private JPanel createControlsPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));

        panel.add(createPassagePanel());
        panel.add(Box.createVerticalStrut(15));
        panel.add(createSeatCountPanel());
        panel.add(Box.createVerticalStrut(15));
        panel.add(createModifiersPanel());

        return panel;
    }

    /**
     * Creates the passage selection panel.
     *
     * @return JPanel with passage options
     */
    private JPanel createPassagePanel()
    {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(240, 240, 240));

        JLabel label = new JLabel("Select Passage:");
        label.setFont(new Font("Arial", Font.BOLD, 12));

        passageCombo = new JComboBox<>(Passage.getPredefinedPassages());
        passageCombo.addActionListener(e -> {
            Passage selected = (Passage) passageCombo.getSelectedItem();
            if (selected != null) {
                config.setPassage(selected);
                customPassageArea.setText(selected.getText());
            }
        });

        JPanel comboPanel = new JPanel(new BorderLayout());
        comboPanel.add(label, BorderLayout.WEST);
        comboPanel.add(passageCombo, BorderLayout.CENTER);
        panel.add(comboPanel, BorderLayout.NORTH);

        // Custom passage option
        JLabel customLabel = new JLabel("Or enter custom passage:");
        customLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        customPassageArea = new JTextArea(4, 40);
        customPassageArea.setLineWrap(true);
        customPassageArea.setWrapStyleWord(true);
        customPassageArea.setFont(new Font("Monospaced", Font.PLAIN, 11));

        JScrollPane scrollPane = new JScrollPane(customPassageArea);
        scrollPane.setPreferredSize(new Dimension(400, 80));

        panel.add(customLabel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Creates the seat count selection panel.
     *
     * @return JPanel with seat count spinner
     */
    private JPanel createSeatCountPanel()
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setBackground(new Color(240, 240, 240));

        JLabel label = new JLabel("Number of Typists:");
        label.setFont(new Font("Arial", Font.BOLD, 12));

        seatCountSpinner = new JSpinner(new SpinnerNumberModel(3, 2, 6, 1));
        seatCountSpinner.addChangeListener(e -> {
            config.setSeatCount((Integer) seatCountSpinner.getValue());
        });

        panel.add(label);
        panel.add(seatCountSpinner);

        return panel;
    }

    /**
     * Creates the difficulty modifiers panel.
     *
     * @return JPanel with modifier checkboxes
     */
    private JPanel createModifiersPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createTitledBorder("Difficulty Modifiers"));

        // Autocorrect
        autocorrectCheckbox = new JCheckBox("Autocorrect On");
        autocorrectCheckbox.setFont(new Font("Arial", Font.PLAIN, 11));
        autocorrectCheckbox.addActionListener(e -> {
            config.setAutocorrect(autocorrectCheckbox.isSelected());
        });
        panel.add(autocorrectCheckbox);

        JLabel autocorrectDesc = new JLabel("  → Reduces mistype impact");
        autocorrectDesc.setFont(new Font("Arial", Font.ITALIC, 10));
        autocorrectDesc.setForeground(new Color(100, 100, 100));
        panel.add(autocorrectDesc);

        panel.add(Box.createVerticalStrut(8));

        // Caffeine Mode
        caffeineCheckbox = new JCheckBox("Caffeine Mode");
        caffeineCheckbox.setFont(new Font("Arial", Font.PLAIN, 11));
        caffeineCheckbox.addActionListener(e -> {
            config.setCaffeineMode(caffeineCheckbox.isSelected());
        });
        panel.add(caffeineCheckbox);

        JLabel caffeineDesc = new JLabel("  → Higher initial accuracy with increased burnout risk");
        caffeineDesc.setFont(new Font("Arial", Font.ITALIC, 10));
        caffeineDesc.setForeground(new Color(100, 100, 100));
        panel.add(caffeineDesc);

        panel.add(Box.createVerticalStrut(8));

        // Night Shift
        nightShiftCheckbox = new JCheckBox("Night Shift");
        nightShiftCheckbox.setFont(new Font("Arial", Font.PLAIN, 11));
        nightShiftCheckbox.addActionListener(e -> {
            config.setNightShift(nightShiftCheckbox.isSelected());
        });
        panel.add(nightShiftCheckbox);

        JLabel nightShiftDesc = new JLabel("  → All typists have reduced accuracy (everyone is tired)");
        nightShiftDesc.setFont(new Font("Arial", Font.ITALIC, 10));
        nightShiftDesc.setForeground(new Color(100, 100, 100));
        panel.add(nightShiftDesc);

        return panel;
    }

    /**
     * Creates the button panel.
     *
     * @return JPanel with start button
     */
    private JPanel createButtonPanel()
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(new Color(240, 240, 240));

        customizeButton = new JButton("Customize Typists");
        customizeButton.setFont(new Font("Arial", Font.BOLD, 12));
        customizeButton.setPreferredSize(new Dimension(150, 40));
        customizeButton.setBackground(new Color(156, 39, 176));
        customizeButton.setForeground(Color.WHITE);
        customizeButton.addActionListener(e -> showCustomizationDialog());
        panel.add(customizeButton);

        startButton = new JButton("Start Race");
        startButton.setFont(new Font("Arial", Font.BOLD, 14));
        startButton.setPreferredSize(new Dimension(120, 40));
        startButton.setBackground(new Color(70, 130, 180));
        startButton.setForeground(Color.WHITE);

        panel.add(startButton);

        return panel;
    }

    /**
     * Returns the current configuration.
     *
     * @return RaceConfiguration object
     */
    public RaceConfiguration getConfiguration()
    {
        // Update passage if custom text was entered
        String customText = customPassageArea.getText().trim();
        if (!customText.isEmpty()) {
            Passage customPassage = new Passage(customText, "Custom Passage", 2);
            config.setPassage(customPassage);
        }
        return config;
    }

    /**
     * Adds an action listener to the start button.
     *
     * @param listener the ActionListener
     */
    public void addStartButtonListener(ActionListener listener)
    {
        startButton.addActionListener(listener);
    }

    /**
     * Validates the current configuration.
     *
     * @return true if valid, false otherwise
     */
    public boolean isConfigurationValid()
    {
        if (config.getPassage() == null && customPassageArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a passage or enter a custom passage.",
                "Configuration Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Shows typist customization dialog.
     */
    private void showCustomizationDialog()
    {
        int seatCount = (Integer) seatCountSpinner.getValue();
        
        JDialog dialog = new JDialog((java.awt.Frame) SwingUtilities.getWindowAncestor(this), 
                                      "Customize Typists", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(600, 100 + seatCount * 250);
        dialog.setLocationRelativeTo(this);

        // Main scrollable panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 240, 240));

        // Create customization panels for each typist
        customizationPanels = new TypistCustomizationPanel[seatCount];
        for (int i = 0; i < seatCount; i++) {
            customizationPanels[i] = new TypistCustomizationPanel(i + 1);
            mainPanel.add(customizationPanels[i]);
            mainPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));

        JButton saveButton = new JButton("Save Customizations");
        saveButton.setFont(new Font("Arial", Font.BOLD, 12));
        saveButton.addActionListener(e -> {
            // Save all customizations to config
            for (int i = 0; i < customizationPanels.length; i++) {
                config.setTypistCustomization(i, customizationPanels[i].getCustomization());
            }
            JOptionPane.showMessageDialog(dialog, "Customizations saved!", 
                                        "Success", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}
