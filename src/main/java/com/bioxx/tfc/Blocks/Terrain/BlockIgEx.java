package com.bioxx.tfc.Blocks.Terrain;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.TFCBlocks;

public class BlockIgEx extends BlockStone {

    public BlockIgEx(Material material) {
        super(material);
        this.dropBlock = TFCBlocks.stoneIgExCobble;
        names = Global.STONE_IGNEOUS_EXTRUSIVE;
        icons = new IIcon[names.length];
        looseStart = Global.STONE_IGNEOUS_EXTRUSIVE_START;
        gemChance = 0;
    }
}
