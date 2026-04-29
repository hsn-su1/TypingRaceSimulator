import java.awt.*;
import javax.swing.*;

/**
 * GUI panel for customizing an individual typist's attributes.
 * Allows users to select typing style, keyboard type, accessories, symbol, and color.
 * 
 * @author Hasan Aliakbar Suleman
 * @version 1 : 29-04-2026
 */

public class TypistCustomizationPanel extends JPanel
{
    private int typistNumber;
    private TypistCustomization customization;
    private JComboBox<TypingStyle> styleCombo;
    private JComboBox<KeyboardType> keyboardCombo;
    private JCheckBox wristSupportCheckbox;
    private JCheckBox energyDrinkCheckbox;
    private JCheckBox headphonesCheckbox;
    private JComboBox<String> symbolCombo;
    private JButton colorButton;
    private Color currentColor;
    private JTextArea effectsArea;

    /**
     * Constructor for TypistCustomizationPanel.
     * 
     * @param number the typist number (1-6)
     */
    public TypistCustomizationPanel(int number)
    {
        this.typistNumber = number;
        this.customization = new TypistCustomization();
        this.currentColor = Color.BLUE;
        
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Typist #" + number + " Customization"));
        setBackground(new Color(250, 250, 250));

        add(createMainPanel(), BorderLayout.CENTER);
        add(createEffectsPanel(), BorderLayout.SOUTH);
    }

    /**
     * Creates the main customization panel.
     * 
     * @return JPanel with customization controls
     */
    private JPanel createMainPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(250, 250, 250));

        // Typing Style
        panel.add(createTypingStylePanel());
        panel.add(Box.createVerticalStrut(10));

        // Keyboard Type
        panel.add(createKeyboardPanel());
        panel.add(Box.createVerticalStrut(10));

        // Accessories
        panel.add(createAccessoriesPanel());
        panel.add(Box.createVerticalStrut(10));

        // Symbol & Color
        panel.add(createSymbolColorPanel());

        return panel;
    }

    /**
     * Creates the typing style selection panel.
     * 
     * @return JPanel with typing style options
     */
    private JPanel createTypingStylePanel()
    {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(new Color(250, 250, 250));

        JLabel label = new JLabel("Typing Style:");
        label.setFont(new Font("Arial", Font.BOLD, 11));

        styleCombo = new JComboBox<>(TypingStyle.values());
        styleCombo.setSelectedItem(TypingStyle.TOUCH_TYPIST);
        styleCombo.addActionListener(e -> {
            TypingStyle selected = (TypingStyle) styleCombo.getSelectedItem();
            customization.setTypingStyle(selected);
            updateEffectsArea();
        });

        JLabel styleDescLabel = new JLabel();
        styleDescLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        styleDescLabel.setForeground(new Color(100, 100, 100));
        updateStyleDescription(styleDescLabel, TypingStyle.TOUCH_TYPIST);

        styleCombo.addActionListener(e -> {
            TypingStyle selected = (TypingStyle) styleCombo.getSelectedItem();
            updateStyleDescription(styleDescLabel, selected);
        });

        panel.add(label, BorderLayout.WEST);
        panel.add(styleCombo, BorderLayout.CENTER);
        panel.add(styleDescLabel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Updates the style description label.
     * 
     * @param label the label to update
     * @param style the typing style
     */
    private void updateStyleDescription(JLabel label, TypingStyle style)
    {
        if (style != null) {
            label.setText("  → " + style.getDescription());
        }
    }

    /**
     * Creates the keyboard type selection panel.
     * 
     * @return JPanel with keyboard options
     */
    private JPanel createKeyboardPanel()
    {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(new Color(250, 250, 250));

        JLabel label = new JLabel("Keyboard Type:");
        label.setFont(new Font("Arial", Font.BOLD, 11));

        keyboardCombo = new JComboBox<>(KeyboardType.values());
        keyboardCombo.setSelectedItem(KeyboardType.MEMBRANE);
        keyboardCombo.addActionListener(e -> {
            KeyboardType selected = (KeyboardType) keyboardCombo.getSelectedItem();
            customization.setKeyboardType(selected);
            updateEffectsArea();
        });

        JLabel keyboardDescLabel = new JLabel();
        keyboardDescLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        keyboardDescLabel.setForeground(new Color(100, 100, 100));
        updateKeyboardDescription(keyboardDescLabel, KeyboardType.MEMBRANE);

        keyboardCombo.addActionListener(e -> {
            KeyboardType selected = (KeyboardType) keyboardCombo.getSelectedItem();
            updateKeyboardDescription(keyboardDescLabel, selected);
        });

        panel.add(label, BorderLayout.WEST);
        panel.add(keyboardCombo, BorderLayout.CENTER);
        panel.add(keyboardDescLabel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Updates the keyboard description label.
     * 
     * @param label the label to update
     * @param keyboard the keyboard type
     */
    private void updateKeyboardDescription(JLabel label, KeyboardType keyboard)
    {
        if (keyboard != null) {
            label.setText("  → " + keyboard.getDescription());
        }
    }

    /**
     * Creates the accessories selection panel.
     * 
     * @return JPanel with accessory checkboxes
     */
    private JPanel createAccessoriesPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(250, 250, 250));
        panel.setBorder(BorderFactory.createTitledBorder("Accessories"));

        // Wrist Support
        wristSupportCheckbox = new JCheckBox("Wrist Support");
        wristSupportCheckbox.setFont(new Font("Arial", Font.PLAIN, 10));
        wristSupportCheckbox.addActionListener(e -> {
            if (wristSupportCheckbox.isSelected()) {
                customization.equipAccessory(Accessory.WRIST_SUPPORT);
            } else {
                customization.removeAccessory(Accessory.WRIST_SUPPORT);
            }
            updateEffectsArea();
        });
        panel.add(wristSupportCheckbox);

        JLabel wristDesc = new JLabel("  → " + Accessory.WRIST_SUPPORT.getDescription());
        wristDesc.setFont(new Font("Arial", Font.ITALIC, 9));
        wristDesc.setForeground(new Color(100, 100, 100));
        panel.add(wristDesc);

        panel.add(Box.createVerticalStrut(5));

        // Energy Drink
        energyDrinkCheckbox = new JCheckBox("Energy Drink");
        energyDrinkCheckbox.setFont(new Font("Arial", Font.PLAIN, 10));
        energyDrinkCheckbox.addActionListener(e -> {
            if (energyDrinkCheckbox.isSelected()) {
                customization.equipAccessory(Accessory.ENERGY_DRINK);
            } else {
                customization.removeAccessory(Accessory.ENERGY_DRINK);
            }
            updateEffectsArea();
        });
        panel.add(energyDrinkCheckbox);

        JLabel energyDesc = new JLabel("  → " + Accessory.ENERGY_DRINK.getDescription());
        energyDesc.setFont(new Font("Arial", Font.ITALIC, 9));
        energyDesc.setForeground(new Color(100, 100, 100));
        panel.add(energyDesc);

        panel.add(Box.createVerticalStrut(5));

        // Noise-Cancelling Headphones
        headphonesCheckbox = new JCheckBox("Noise-Cancelling Headphones");
        headphonesCheckbox.setFont(new Font("Arial", Font.PLAIN, 10));
        headphonesCheckbox.addActionListener(e -> {
            if (headphonesCheckbox.isSelected()) {
                customization.equipAccessory(Accessory.NOISE_CANCELLING_HEADPHONES);
            } else {
                customization.removeAccessory(Accessory.NOISE_CANCELLING_HEADPHONES);
            }
            updateEffectsArea();
        });
        panel.add(headphonesCheckbox);

        JLabel headphonesDesc = new JLabel("  → " + Accessory.NOISE_CANCELLING_HEADPHONES.getDescription());
        headphonesDesc.setFont(new Font("Arial", Font.ITALIC, 9));
        headphonesDesc.setForeground(new Color(100, 100, 100));
        panel.add(headphonesDesc);

        return panel;
    }

    /**
     * Creates the symbol and color selection panel.
     * 
     * @return JPanel with symbol and color controls
     */
    private JPanel createSymbolColorPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(new Color(250, 250, 250));

        // Symbol
        JLabel symbolLabel = new JLabel("Symbol:");
        symbolLabel.setFont(new Font("Arial", Font.BOLD, 11));

        String[] symbols = {
            "●", "■", "▲", "◆", "★", "☆", "♠", "♣", "♥", "♦",
            "◯", "◉", "◎", "○", "◉", "✦", "✧", "❖", "⬤", "🎯"
        };
        symbolCombo = new JComboBox<>(symbols);
        symbolCombo.setSelectedItem("●");
        symbolCombo.addActionListener(e -> {
            String selected = (String) symbolCombo.getSelectedItem();
            if (selected != null && selected.length() > 0) {
                char symbol = selected.charAt(0);
                customization.setSymbol(symbol);
            }
        });

        panel.add(symbolLabel);
        panel.add(symbolCombo);
        panel.add(Box.createHorizontalStrut(20));

        // Color
        JLabel colorLabel = new JLabel("Color:");
        colorLabel.setFont(new Font("Arial", Font.BOLD, 11));

        colorButton = new JButton("  ");
        colorButton.setBackground(currentColor);
        colorButton.setPreferredSize(new Dimension(40, 25));
        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(TypistCustomizationPanel.this, 
                                                      "Choose Color", currentColor);
            if (newColor != null) {
                currentColor = newColor;
                colorButton.setBackground(currentColor);
                customization.setBarColor(currentColor);
            }
        });

        panel.add(colorLabel);
        panel.add(colorButton);

        return panel;
    }

    /**
     * Creates the effects display panel.
     * 
     * @return JPanel with effects summary
     */
    private JPanel createEffectsPanel()
    {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createTitledBorder("Effects Summary"));

        effectsArea = new JTextArea(5, 40);
        effectsArea.setEditable(false);
        effectsArea.setLineWrap(true);
        effectsArea.setWrapStyleWord(true);
        effectsArea.setFont(new Font("Monospaced", Font.PLAIN, 10));
        effectsArea.setBackground(new Color(255, 255, 255));

        JScrollPane scrollPane = new JScrollPane(effectsArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        updateEffectsArea();

        return panel;
    }

    /**
     * Updates the effects area with current customization summary.
     */
    private void updateEffectsArea()
    {
        StringBuilder sb = new StringBuilder();
        
        TypingStyle style = (TypingStyle) styleCombo.getSelectedItem();
        KeyboardType keyboard = (KeyboardType) keyboardCombo.getSelectedItem();

        sb.append("IMPACTS:\n");
        sb.append("━━━━━━━━━━━━━━━━━━━━━\n");
        
        if (style != null) {
            sb.append(String.format("Accuracy: %.0f%% (style: %.0f%%)\n", 
                                   75.0, style.getAccuracyModifier() * 100));
            sb.append(String.format("Burnout Risk: %.0f%% (style: %.0f%%)\n", 
                                   50.0, style.getBurnoutRiskModifier() * 100));
            sb.append(String.format("Speed: %.0f%% (keyboard: %.0f%%)\n", 
                                   100.0 * (keyboard != null ? keyboard.getSpeedModifier() : 1.0),
                                   100.0));
        }

        sb.append("\nACCESSORI ES APPLIED:\n");
        if (wristSupportCheckbox.isSelected() || 
            energyDrinkCheckbox.isSelected() || 
            headphonesCheckbox.isSelected()) {
            if (wristSupportCheckbox.isSelected()) sb.append("✓ Wrist Support (-30% burnout)\n");
            if (energyDrinkCheckbox.isSelected()) sb.append("✓ Energy Drink (±accuracy)\n");
            if (headphonesCheckbox.isSelected()) sb.append("✓ Headphones (-20% mistype)\n");
        } else {
            sb.append("None selected\n");
        }

        effectsArea.setText(sb.toString());
    }

    /**
     * Gets the customization from this panel.
     * 
     * @return TypistCustomization
     */
    public TypistCustomization getCustomization()
    {
        return customization;
    }
}
