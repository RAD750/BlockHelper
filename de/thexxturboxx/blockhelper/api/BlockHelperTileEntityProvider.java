package de.thexxturboxx.blockhelper.api;

import de.thexxturboxx.blockhelper.InfoHolder;
import net.minecraft.tileentity.TileEntity;

public interface BlockHelperTileEntityProvider {

    void addInformation(TileEntity te, int id, int meta, InfoHolder info);

}
