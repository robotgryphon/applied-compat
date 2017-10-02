package com.robotgryphon.aecompat;

import com.robotgryphon.aecompat.config.ModConfig;
import com.robotgryphon.aecompat.loader.NuclearApproach;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = AppliedCompatibility.ID, name = AppliedCompatibility.NAME, version = AppliedCompatibility.VERSION, acceptableRemoteVersions = "*", dependencies = AppliedCompatibility.DEPSENDENCIES)
public class AppliedCompatibility {
    public static final String VERSION = "1.0.0";
    public static final String NAME = "Applied Compatibility";
    public static final String ID = "appliedcompat";
    public static final String DEPSENDENCIES = "required-after:appliedenergistics2";

    @Mod.Instance(ID)
    public static AppliedCompatibility INSTANCE;

    public static ModConfig CONFIG;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        AppliedCompatibility.logger = event.getModLog();
        AppliedCompatibility.CONFIG = new ModConfig(event.getModConfigurationDirectory());
        CONFIG.setup();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if(CONFIG.enableAllMode) {
            logger.info("Nuclear Approach enabled. Setting up the blacklist and preparing for registration...");
            NuclearApproach.loadBlacklist();
            NuclearApproach.registerEverything();
            return;
        }
    }
}
