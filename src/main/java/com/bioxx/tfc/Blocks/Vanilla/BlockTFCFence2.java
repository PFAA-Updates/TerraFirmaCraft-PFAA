package com.bioxx.tfc.Blocks.Vanilla;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

import com.bioxx.tfc.api.Constant.Global;

public class BlockTFCFence2 extends BlockTFCFence {

    public BlockTFCFence2(String str, Material mat) {
        super(str, mat);
        woodNames = new String[Global.WOOD_NAMES.length - 16];
        System.arraycopy(Global.WOOD_NAMES, 16, woodNames, 0, Global.WOOD_NAMES.length - 16);
        iconsPost = new IIcon[woodNames.length];
        iconsPostTop = new IIcon[woodNames.length];
    }
}
