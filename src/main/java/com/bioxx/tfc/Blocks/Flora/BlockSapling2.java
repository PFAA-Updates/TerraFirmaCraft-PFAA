package com.bioxx.tfc.Blocks.Flora;

import net.minecraft.util.IIcon;

import com.bioxx.tfc.api.Constant.Global;

public class BlockSapling2 extends BlockSapling {

    public BlockSapling2() {
        super();
        this.woodNames = new String[Global.WOOD_NAMES.length - 16];
        System.arraycopy(Global.WOOD_NAMES, 16, woodNames, 0, Global.WOOD_NAMES.length - 16);
        this.icons = new IIcon[woodNames.length];
    }
}
