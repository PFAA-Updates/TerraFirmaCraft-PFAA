package com.bioxx.tfc.Blocks.Terrain;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bioxx.tfc.api.Constant.Global;

public class BlockIgExCobble extends BlockCobble {

    public BlockIgExCobble(Material material) {
        super(material);
        names = Global.STONE_IGNEOUS_EXTRUSIVE;
        icons = new IIcon[names.length];
        looseStart = Global.STONE_IGNEOUS_EXTRUSIVE_START;
    }

    @Override
    public int tickRate(World world) {
        // return 3;
        return 20;
    }
}
