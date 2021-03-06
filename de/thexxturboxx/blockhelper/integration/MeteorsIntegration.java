package de.thexxturboxx.blockhelper.integration;

import de.thexxturboxx.blockhelper.InfoHolder;
import de.thexxturboxx.blockhelper.api.BlockHelperInfoProvider;
import net.meteor.common.MeteorsMod;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public class MeteorsIntegration extends BlockHelperInfoProvider {

    @Override
    public void addInformation(Block b, int id, int meta, InfoHolder info) {
        if (iof(b, "net.meteor.common.BlockMeteorShieldTorch")) {
            info.add(0, "State: "
                    + (id == MeteorsMod.torchMeteorShieldActive.blockID ? "Protected" : "Unprotected"));
        }
    }

    @Override
    public void addInformation(TileEntity te, int id, int meta, InfoHolder info) {
        if (iof(te, "net.meteor.common.TileEntityMeteorShield")) {
            if (meta == 0) {
                info.add(0, "State: Charging");
            } else {
                info.add(0, "Radius: " + meta * 4 + "x" + meta * 4 + " Chunks");
            }
        }
    }

}
