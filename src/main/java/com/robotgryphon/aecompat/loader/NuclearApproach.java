package com.robotgryphon.aecompat.loader;

import com.robotgryphon.aecompat.AppliedCompatibility;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Predicate;

public class NuclearApproach {

    /**
     * A set of checks to filter out tiles when adding them to the whitelist.
     */
    protected static Predicate<Class> checkList = (c -> true);

    static {
        // Funky Locomotion crashes game on tile move
        checkList = checkList.and(c -> !c.getName().contains("funkylocomotion"));

        // Vanilla is already handled by AE2
        checkList = checkList.and(c -> !c.getName().startsWith("net.minecraft"));

        // AE2 is (obviously) handled by AE2
        checkList = checkList.and(c -> !c.getName().startsWith("appeng."));
    }

    public static void loadBlacklist() {
        // TODO: JSON blacklist additions to this
    }

    public static void registerEverything() {
        Field classMapping;
        try { classMapping = ReflectionHelper.findField(TileEntity.class, "classToNameMap", "field_145853_j"); }
        catch(ReflectionHelper.UnableToFindFieldException rhe) {
            AppliedCompatibility.logger.error("Couldn't access the Tile Entity mapping field, aborting.");
            return;
        }

        try {
            classMapping.setAccessible(true);
            Map<Class<? extends TileEntity>, String> reg = (Map) classMapping.get(null);

            AppliedCompatibility.logger.info("Check minecraft class for exclusion: " + checkList.test(TileEntity.class));

            reg.keySet()
                    .stream()
                    .filter(checkList)
                    .forEach(entity -> {
                        FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", entity.getName());
                        AppliedCompatibility.logger.info(String.format("Registered block %s for spatial IO.", entity.getName()));
                    });
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            AppliedCompatibility.logger.error("An unexpected error occured ", ex);
        }
    }
}
