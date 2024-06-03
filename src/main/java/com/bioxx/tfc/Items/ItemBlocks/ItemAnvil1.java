package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Metal;

public class ItemAnvil1 extends ItemAnvil {

    public ItemAnvil1(Block par1) {
        super(par1);
        this.metaNames = new String[] { "Stone", "Copper", "Bronze", "Wrought Iron", "Steel", "Black Steel",
            "Blue Steel", "Red Steel" };
    }

    @Override
    public Metal getMetalType(ItemStack is) {
        int meta = is.getItemDamage();
        switch (meta) {
            case 1:
                return Global.COPPER;
            case 2:
                return Global.BRONZE;
            case 3:
                return Global.WROUGHT_IRON;
            case 4:
                return Global.STEEL;
            case 5:
                return Global.BLACK_STEEL;
            case 6:
                return Global.BLUE_STEEL;
            case 7:
                return Global.RED_STEEL;
            default:
                return Global.UNKNOWN;
        }
    }
}
