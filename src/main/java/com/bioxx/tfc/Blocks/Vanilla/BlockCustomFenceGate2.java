package com.bioxx.tfc.Blocks.Vanilla;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.util.IIcon;

import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Interfaces.IMultipleBlock;
import com.bioxx.tfc.api.TFCBlocks;

public class BlockCustomFenceGate2 extends BlockCustomFenceGate implements ITileEntityProvider, IMultipleBlock {

    public BlockCustomFenceGate2() {
        super();
        woodNames = new String[Global.WOOD_NAMES.length - 16];
        System.arraycopy(Global.WOOD_NAMES, 16, woodNames, 0, Global.WOOD_NAMES.length - 16);
        icons = new IIcon[woodNames.length];
    }

    @Override
    public IIcon getIcon(int par1, int par2) {
        return icons[Math.min(par2, icons.length - 1)];
    }

    @Override
    public Block getBlockTypeForRender() {
        return TFCBlocks.fenceGate2;
    }
}
