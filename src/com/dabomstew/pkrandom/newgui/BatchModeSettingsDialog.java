package com.dabomstew.pkrandom.newgui;

/*----------------------------------------------------------------------------*/
/*--  BatchModeSettingsDialog.java - a dialog for configuring batch mode    --*/
/*--                                 settings (allows for making multiple   --*/
/*--                                 randomized ROMs)                       --*/
/*--                                                                        --*/
/*--  Part of "Universal Pokemon Randomizer ZX" by the UPR-ZX team          --*/
/*--  Originally part of "Universal Pokemon Randomizer" by Dabomstew        --*/
/*--  Pokemon and any associated names and the like are                     --*/
/*--  trademark and (C) Nintendo 1996-2020.                                 --*/
/*--                                                                        --*/
/*--  The custom code written here is licensed under the terms of the GPL:  --*/
/*--                                                                        --*/
/*--  This program is free software: you can redistribute it and/or modify  --*/
/*--  it under the terms of the GNU General Public License as published by  --*/
/*--  the Free Software Foundation, either version 3 of the License, or     --*/
/*--  (at your option) any later version.                                   --*/
/*--                                                                        --*/
/*--  This program is distributed in the hope that it will be useful,       --*/
/*--  but WITHOUT ANY WARRANTY; without even the implied warranty of        --*/
/*--  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the          --*/
/*--  GNU General Public License for more details.                          --*/
/*--                                                                        --*/
/*--  You should have received a copy of the GNU General Public License     --*/
/*--  along with this program. If not, see <http://www.gnu.org/licenses/>.  --*/
/*----------------------------------------------------------------------------*/

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
        currentSettings.setGenerateLogFile(generateLogFilesCheckBox.isSelected());
        currentSettings.setAutoAdvanceStartingIndex(autoAdvanceIndexCheckBox.isSelected());
        currentSettings.setNumberOfSeeds((Integer) numberOfSeedsSpinner.getValue());
        currentSettings.setStartingIndex((Integer) startingIndexSpinner.getValue());
        currentSettings.setFileNamePrefix(fileNamePrefixTextField.getText());
        currentSettings.setOutputDirectory(outputDirectoryFileChooser.getSelectedFile().getAbsolutePath());
    }
}
