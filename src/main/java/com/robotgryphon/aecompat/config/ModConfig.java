package com.robotgryphon.aecompat.config;

import com.robotgryphon.aecompat.AppliedCompatibility;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.nio.file.Path;

public class ModConfig {

    protected Path modConfigDir;
    protected Configuration mainConfig;

    /**
     * If this is true, all other configuration is skipped and the mod registers every tile entity available.
     * It's called nuclearApproach for a reason; you really shouldn't be using this..
     */
    public Boolean enableAllMode;

    public ModConfig(File modConfigDirectory) {
        this.modConfigDir = modConfigDirectory.toPath();
        this.mainConfig = new Configuration(this.modConfigDir.resolve(AppliedCompatibility.NAME).toFile());
    }

    public void setup() {
        this.enableAllMode = mainConfig.getBoolean(Categories.BASE, "nuclearApproach", true, "Whitelists all tile entities that are registered.");
    }

    private class Categories {
        public static final String BASE = "base";
    }
}
