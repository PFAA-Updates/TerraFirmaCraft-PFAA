package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;

import com.bioxx.tfc.api.Constant.Global;

public class ItemWoodSupport2 extends ItemWoodSupport {

    public ItemWoodSupport2(Block par1) {
        super(par1);
        this.hasSubtypes = true;
        this.setMaxDamage(0);
        this.metaNames = new String[Global.WOOD_NAMES.length - 16];
        System.arraycopy(Global.WOOD_NAMES, 16, metaNames, 0, Global.WOOD_NAMES.length - 16);
    }

}
