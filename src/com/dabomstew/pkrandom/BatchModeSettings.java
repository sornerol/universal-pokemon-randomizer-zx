package com.dabomstew.pkrandom;

/*----------------------------------------------------------------------------*/
/*--  BatchModeSettings.java - handles functionality related to batch mode. --*/
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

import java.io.*;
import java.util.StringJoiner;

public class BatchModeSettings implements Cloneable {
    private Boolean batchModeEnabled;
    private Boolean generateLogFile;
    private Boolean autoAdvanceStartingIndex;
    private Integer numberOfSeeds;
    private Integer startingIndex;
    private String fileNamePrefix;
    private String outputDirectory;

    public BatchModeSettings() {
        batchModeEnabled = false;
        generateLogFile = false;
        autoAdvanceStartingIndex = false;
        numberOfSeeds = 10;
        startingIndex = 0;
        fileNamePrefix = "random";
        outputDirectory = SysConstants.ROOT_PATH;
    }

    public boolean isBatchModeEnabled() {
        return batchModeEnabled;
    }

    public void setBatchModeEnabled(boolean batchModeEnabled) {
        this.batchModeEnabled = batchModeEnabled;
    }

    public boolean shouldGenerateLogFile() {
        return generateLogFile;
    }

    public void setGenerateLogFile(boolean generateLogFile) {
        this.generateLogFile = generateLogFile;
    }

    public boolean shouldAutoAdvanceStartingIndex() {
        return autoAdvanceStartingIndex;
    }

    public void setAutoAdvanceStartingIndex(boolean autoAdvanceStartingIndex) {
        this.autoAdvanceStartingIndex = autoAdvanceStartingIndex;
    }

    public int getNumberOfSeeds() {
        return numberOfSeeds;
    }

    public void setNumberOfSeeds(int numberOfSeeds) {
        this.numberOfSeeds = numberOfSeeds;
    }

    public int getStartingIndex() {
        return startingIndex;
    }

    public void setStartingIndex(int startingIndex) {
        this.startingIndex = startingIndex;
    }

    public String getFileNamePrefix() {
        return fileNamePrefix;
    }

    public void setFileNamePrefix(String fileNamePrefix) {
        this.fileNamePrefix = fileNamePrefix;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner("\n");
        sj.add("batchmode.enabled=" + batchModeEnabled.toString());
        sj.add("batchmode.generatelogfiles=" + generateLogFile.toString());
        sj.add("batchmode.autoadvanceindex=" + autoAdvanceStartingIndex.toString());
        sj.add("batchmode.numberofseeds=" + numberOfSeeds.toString());
        sj.add("batchmode.startingindex=" + startingIndex.toString());
        sj.add("batchmode.filenameprefix=" + fileNamePrefix);
        sj.add("batchmode.outputdirectory=" + outputDirectory);
        return sj.toString();
    }

    @Override
    public BatchModeSettings clone() {
        try {
            return (BatchModeSettings) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
