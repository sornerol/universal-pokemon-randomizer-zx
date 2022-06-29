package com.dabomstew.pkrandom.newgui;

import com.dabomstew.pkrandom.BatchModeSettings;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public class BatchModeSettingsDialog extends JDialog {
    private JPanel mainPanel;
    private JButton okButton;
    private JButton cancelButton;
    private JCheckBox enableBatchModeCheckBox;
    private JSpinner numberOfSeedsSpinner;
    private JSpinner startingIndexSpinner;
    private JTextField fileNamePrefixTextField;
    private JCheckBox generateLogFilesCheckBox;
    private JCheckBox autoAdvanceIndexCheckBox;
    private JButton chooseDirectoryButton;
    private JLabel outputDirectoryLabel;

    private JFileChooser outputDirectoryFileChooser;

    private final BatchModeSettings currentSettings;

    public BatchModeSettings getCurrentSettings() {
        return this.currentSettings;
    }

    public BatchModeSettingsDialog(JFrame parent, BatchModeSettings currentSettings) {
        super(parent, true);
        add(mainPanel);
        setTitle("Batch Mode Settings");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        getRootPane().setDefaultButton(okButton);

        this.currentSettings = currentSettings.clone();

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
        outputDirectoryFileChooser = new JFileChooser();
        okButton.addActionListener(e -> onOK());
        cancelButton.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        mainPanel.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        SpinnerNumberModel numberOfSeedsModel = new SpinnerNumberModel(1,1, Integer.MAX_VALUE, 1);
        numberOfSeedsSpinner.setModel(numberOfSeedsModel);

        SpinnerNumberModel startingIndexModel = new SpinnerNumberModel(1,0, Integer.MAX_VALUE, 1);
        startingIndexSpinner.setModel(startingIndexModel);

        chooseDirectoryButton.addActionListener(e -> {
            int selectionResult = outputDirectoryFileChooser.showDialog(this, "Select");
            if (selectionResult == JFileChooser.APPROVE_OPTION) {
                outputDirectoryFileChooser.setCurrentDirectory(new File(currentSettings.getOutputDirectory()).getParentFile());
                outputDirectoryLabel.setText(outputDirectoryFileChooser.getSelectedFile().getAbsolutePath());
            }
        });
        setInitialControlValues();
        setControlsEnabled(currentSettings.isBatchModeEnabled());
    }

    private void setInitialControlValues() {
        enableBatchModeCheckBox.setSelected(currentSettings.isBatchModeEnabled());
        generateLogFilesCheckBox.setSelected(currentSettings.shouldGenerateLogFile());
        autoAdvanceIndexCheckBox.setSelected(currentSettings.shouldAutoAdvanceStartingIndex());
        numberOfSeedsSpinner.setValue(currentSettings.getNumberOfSeeds());
        startingIndexSpinner.setValue(currentSettings.getStartingIndex());
        fileNamePrefixTextField.setText(currentSettings.getFileNamePrefix());
        outputDirectoryLabel.setText(currentSettings.getOutputDirectory());
        outputDirectoryFileChooser.setCurrentDirectory(new File(currentSettings.getOutputDirectory()).getParentFile());
        outputDirectoryFileChooser.setSelectedFile(new File(currentSettings.getOutputDirectory()));
        outputDirectoryFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        enableBatchModeCheckBox.addActionListener(a -> setControlsEnabled(enableBatchModeCheckBox.isSelected()));
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
        currentSettings.setOutputDirectory(outputDirectoryFileChooser.getSelectedFile().getAbsolutePath());
    }
}
