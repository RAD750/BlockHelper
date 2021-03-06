package de.thexxturboxx.blockhelper.integration;

import de.thexxturboxx.blockhelper.InfoHolder;
import de.thexxturboxx.blockhelper.api.BlockHelperInfoProvider;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;

public class VanillaIntegration extends BlockHelperInfoProvider {

    @Override
    public void addInformation(Block b, int id, int meta, InfoHolder info) {
        boolean crop = isCrop(b);
        if (crop) {
            double max_stage = getMaxStage(b, id);
            String grow = ((int) ((meta / max_stage) * 100)) + "";
            if (grow.equals("100")) {
                grow = "Mature";
            } else {
                grow = grow + "%";
            }
            info.add(2, "Growth State: " + grow);
        }

        if (id == Block.redstoneWire.blockID) {
            info.add(3, "Strength: " + meta);
        }

        if (id == Block.lever.blockID) {
            String state = "Off";
            if (meta >= 8) {
                state = "On";
            }
            info.add(3, "State: " + state);
        }
    }

    private double getMaxStage(Block b, int id) {
        try {
            if (iof(b, "florasoma.crops.blocks.FloraCropBlock")) {
                return 3;
            } else {
                for (Field field : b.getClass().getFields()) {
                    if (containsIgnoreCase(field.getName(), "max")
                            && containsIgnoreCase(field.getName(), "stage")) {
                        field.setAccessible(true);
                        return field.getInt(Block.blocksList[id]);
                    }
                }
                for (Field field : b.getClass().getDeclaredFields()) {
                    if (containsIgnoreCase(field.getName(), "max")
                            && containsIgnoreCase(field.getName(), "stage")) {
                        field.setAccessible(true);
                        return field.getInt(Block.blocksList[id]);
                    }
                }
            }
        } catch (Throwable ignored) {
        }
        return 7;
    }

    private boolean isCrop(Block b) {
        boolean crop = b instanceof BlockCrops;
        if (!crop) {
            try {
                for (Method method : b.getClass().getDeclaredMethods()) {
                    if (method.getName().equals("getGrowthRate")) {
                        return true;
                    }
                }
            } catch (Throwable ignored) {
            }
        }
        return crop;
    }

    private static boolean containsIgnoreCase(String str, String searchStr) {
        if (str == null || searchStr == null)
            return false;

        final int length = searchStr.length();
        if (length == 0)
            return true;

        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length))
                return true;
        }
        return false;
    }

}
