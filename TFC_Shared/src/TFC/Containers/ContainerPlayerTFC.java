package TFC.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import TFC.Containers.Slots.SlotArmorTFC;
import TFC.Core.Player.PlayerInventory;

public class ContainerPlayerTFC extends ContainerPlayer 
{
	public ContainerPlayerTFC(InventoryPlayer playerInv,
			boolean par2, EntityPlayer player) 
	{
		super(playerInv, par2, player);
		craftMatrix = new InventoryCrafting(this, 3, 3);
		inventorySlots.clear();
		inventoryItemStacks.clear();

		this.addSlotToContainer(new SlotCrafting(player, craftMatrix, craftResult, 0, 152, 36));
		int x;
		int y;

		for (x = 0; x < 2; ++x)
			for (y = 0; y < 2; ++y)
				this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18, 18 + x * 18));

		for (x = 0; x < 4; ++x)
		{
			int index = playerInv.getSizeInventory() - 1 - x;
			this.addSlotToContainer(new SlotArmorTFC(this, playerInv, index, 8, 8 + x * 18, x));
		}
		PlayerInventory.buildInventoryLayout(this, playerInv, 8, 90, false, true);

		//Manually built the remaining crafting slots because of an order issue. These have to be created after the default slots
		x = 2; y = 0; this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18, 18 + x * 18));
		x = 2; y = 1; this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18, 18 + x * 18));
		x = 0; y = 2; this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18, 18 + x * 18));
		x = 1; y = 2; this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18, 18 + x * 18));
		x = 2; y = 2; this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18, 18 + x * 18));

		this.onCraftMatrixChanged(this.craftMatrix);
	}

	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		super.onContainerClosed(par1EntityPlayer);

		for (int i = 0; i < 9; ++i)
		{
			ItemStack itemstack = this.craftMatrix.getStackInSlotOnClosing(i);

			if (itemstack != null)
				par1EntityPlayer.dropPlayerItem(itemstack);
		}

		this.craftResult.setInventorySlotContents(0, (ItemStack)null);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 == 0)
			{
				if (!this.mergeItemStack(itemstack1, 9, 45, true))
					return null;

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if ((par2 >= 1 && par2 < 5) || (par2 >= 45 && par2 < 50))
			{
				if (!this.mergeItemStack(itemstack1, 9, 45, false))
					return null;
			}
			else if (par2 >= 5 && par2 < 9)
			{
				if (!this.mergeItemStack(itemstack1, 9, 45, false))
					return null;
			}
			else if (itemstack.getItem() instanceof ItemArmor && !((Slot)this.inventorySlots.get(5 + ((ItemArmor)itemstack.getItem()).armorType)).getHasStack())
			{
				int j = 5 + ((ItemArmor)itemstack.getItem()).armorType;

				if (!this.mergeItemStack(itemstack1, j, j + 1, false))
					return null;
			}
			else if (par2 >= 9 && par2 < 36)
			{
				if (!this.mergeItemStack(itemstack1, 36, 45, false))
					return null;
			}
			else if (par2 >= 36 && par2 < 45)
			{
				if (!this.mergeItemStack(itemstack1, 9, 36, false))
					return null;
			}
			else if (!this.mergeItemStack(itemstack1, 9, 45, false))
				return null;

			if (itemstack1.stackSize == 0)
				slot.putStack((ItemStack)null);
			else
				slot.onSlotChanged();

			if (itemstack1.stackSize == itemstack.stackSize)
				return null;

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}

		return itemstack;
	}

	public EntityPlayer getPlayer()
	{
		return this.thePlayer;
	}

}