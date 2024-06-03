package com.bioxx.tfc.Blocks.Terrain;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.TFCBlocks;

public class BlockSed extends BlockStone {

    public BlockSed(Material material) {
        super(material);
        this.dropBlock = TFCBlocks.stoneSedCobble;
        names = Global.STONE_SEDIMENTARY;
        icons = new IIcon[names.length];
        looseStart = Global.STONE_SEDIMENTARY_START;
        gemChance = 1;
    }
}
