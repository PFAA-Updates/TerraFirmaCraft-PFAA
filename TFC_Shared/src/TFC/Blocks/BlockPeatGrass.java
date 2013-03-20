package TFC.Blocks;

import java.util.Random;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;

import TFC.*;
import TFC.Core.ColorizerGrassTFC;
import TFC.Core.TFC_Settings;

public class BlockPeatGrass extends BlockGrass
{
	
    public BlockPeatGrass(int par1)
    {
        super(par1);

    }
    
    @Override
    public void func_94332_a(IconRegister registerer)
    {
		DirtTexture[0] = registerer.func_94245_a("soil/Peat");
		GrassTopTexture = registerer.func_94245_a("GrassTop");
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return TFCBlocks.Peat.blockID;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 1;
    }
    
    public void addCreativeItems(java.util.ArrayList list)
	{
	    list.add(new ItemStack(this,1,0));
	}
}
