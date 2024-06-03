package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Constant.Global;

public class ItemStone extends ItemTerraBlock {

    public ItemStone(Block b) {
        super(b);
        if (TFC_Core.isStoneIgEx(b)) metaNames = Global.STONE_IGNEOUS_EXTRUSIVE;
        else if (TFC_Core.isStoneIgIn(b)) metaNames = Global.STONE_IGNEOUS_INTRUSIVE;
        else if (TFC_Core.isStoneSed(b)) metaNames = Global.STONE_SEDIMENTARY;
        else if (TFC_Core.isStoneMM(b)) metaNames = Global.STONE_METAMORPHIC;
    }
}
