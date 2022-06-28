package com.dabomstew.pkrandom.newgui;

import com.dabomstew.pkrandom.BatchModeSettings;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class BatchModeSettingsDialog extends JDialog {
    private JPanel mainPanel;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JCheckBox enableBatchModeCheckBox;
    private JSpinner numberOfSeedsSpinner;
    private JSpinner startingIndexSpinner;
    private JTextField fileNamePrefixTextField;
    private JCheckBox generateLogFilesCheckBox;
    private JCheckBox autoAdvanceIndexCheckBox;
    private JButton chooseDirectoryButton;
    private JLabel outputDirectoryLabel;

    private final BatchModeSettings currentSettings;

    public BatchModeSettings getCurrentSettings() {
        return this.currentSettings;
    }

    public BatchModeSettingsDialog(JFrame parent, BatchModeSettings currentSettings) {
        super(parent, true);
        add(mainPanel);
        setTitle("Batch Mode Settings");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        getRootPane().setDefaultButton(buttonOK);

        this.currentSettings = currentSettings.clone();

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        mainPanel.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        initializeControls();
        setLocationRelativeTo(parent);
        pack();
        setVisible(true);
    }

    private void onOK() {
        updateSettings();
        setVisible(false);
    }

    private void onCancel() {
        // add your code here if necessary
        setVisible(false);
    }

    private void initializeControls() {
        enableBatchModeCheckBox.setSelected(currentSettings.isBatchModeEnabled());
        generateLogFilesCheckBox.setSelected(currentSettings.shouldGenerateLogFile());
        autoAdvanceIndexCheckBox.setSelected(currentSettings.shouldAutoAdvanceStartingIndex());
        numberOfSeedsSpinner.setValue(currentSettings.getNumberOfSeeds());
        startingIndexSpinner.setValue(currentSettings.getStartingIndex());
        fileNamePrefixTextField.setText(currentSettings.getFileNamePrefix());
        outputDirectoryLabel.setText(currentSettings.getOutputDirectory().getAbsolutePath());

        enableBatchModeCheckBox.addActionListener(a -> setControlsEnabled(enableBatchModeCheckBox.isSelected()));

        setControlsEnabled(currentSettings.isBatchModeEnabled());
    }

    private void setControlsEnabled(boolean enabled) {
        numberOfSeedsSpinner.setEnabled(enabled);
        startingIndexSpinner.setEnabled(enabled);
        fileNamePrefixTextField.setEnabled(enabled);
        generateLogFilesCheckBox.setEnabled(enabled);
        autoAdvanceIndexCheckBox.setEnabled(enabled);
        chooseDirectoryButton.setEnabled(enabled);
    }

    private void updateSettings() {
        currentSettings.setBatchModeEnabled(enableBatchModeCheckBox.isSelected());
        currentSettings.setGenerateLogFile(enableBatchModeCheckBox.isSelected());
        currentSettings.setAutoAdvanceStartingIndex(autoAdvanceIndexCheckBox.isSelected());
        currentSettings.setNumberOfSeeds((Integer) numberOfSeedsSpinner.getValue());
        currentSettings.setStartingIndex((Integer) startingIndexSpinner.getValue());
        currentSettings.setFileNamePrefix(fileNamePrefixTextField.getText());
        currentSettings.setOutputDirectory(new File(outputDirectoryLabel.getText()));
    }
}
