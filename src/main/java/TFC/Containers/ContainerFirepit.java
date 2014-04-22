package TFC.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.API.HeatRegistry;
import TFC.Containers.Slots.SlotFirepit;
import TFC.Containers.Slots.SlotFirepitFuel;
import TFC.Containers.Slots.SlotFirepitIn;
import TFC.Containers.Slots.SlotFirepitOut;
import TFC.Containers.Slots.SlotForShowOnly;
import TFC.Core.Player.PlayerInventory;
import TFC.Items.ItemOre;
import TFC.TileEntities.TileEntityFirepit;

public class ContainerFirepit extends ContainerTFC
{
	private TileEntityFirepit firepit;
	private float firetemp;
	private int charcoal;

	public ContainerFirepit(InventoryPlayer inventoryplayer, TileEntityFirepit tileentityfirepit, World world, int x, int y, int z)
	{
		firepit = tileentityfirepit;
		firetemp = -1111;
		charcoal = 0;

		//Input slot
		addSlotToContainer(new SlotFirepitIn(inventoryplayer.player,tileentityfirepit, 1, 80, 20));
		//fuel stack
		addSlotToContainer(new SlotFirepitFuel(inventoryplayer.player, tileentityfirepit, 0, 8, 8));
		addSlotToContainer(new SlotFirepit(inventoryplayer.player, tileentityfirepit, 3, 8, 26));
		addSlotToContainer(new SlotFirepit(inventoryplayer.player, tileentityfirepit, 4, 8, 44));
		addSlotToContainer(new SlotFirepit(inventoryplayer.player, tileentityfirepit, 5, 8, 62));

		//item output
		addSlotToContainer(new SlotFirepitOut(inventoryplayer.player, tileentityfirepit, 7, 71, 48));
		addSlotToContainer(new SlotFirepitOut(inventoryplayer.player, tileentityfirepit, 8, 89, 48));
		
		//dummy byproducts out
		addSlotToContainer(new SlotForShowOnly(tileentityfirepit, 2, -50000, 0));
		addSlotToContainer(new SlotForShowOnly(tileentityfirepit, 6, -50000, 0));
		addSlotToContainer(new SlotForShowOnly(tileentityfirepit, 9, -50000, 0));
		addSlotToContainer(new SlotForShowOnly(tileentityfirepit, 10, -50000, 0));

		PlayerInventory.buildInventoryLayout(this, inventoryplayer, 8, 90, false, true);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p, int i)
	{
		Slot slot = (Slot)inventorySlots.get(i);
		Slot slotinput = (Slot)inventorySlots.get(0);
		Slot[] slotoutput = {(Slot)inventorySlots.get(7), (Slot)inventorySlots.get(8)};
		Slot[] slotfuel = {(Slot)inventorySlots.get(1), (Slot)inventorySlots.get(3), (Slot)inventorySlots.get(4), (Slot)inventorySlots.get(5)};
		HeatRegistry manager = HeatRegistry.getInstance();
		
		if(slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			if(i <= 10)
			{
				if(!this.mergeItemStack(itemstack1, 11, this.inventorySlots.size(), true))
					return null;
			}
			else
			{
				if(itemstack1.getItem() == TFCItems.Logs || itemstack1.getItem() == Item.getItemFromBlock(TFCBlocks.Peat))
				{
					if(slotfuel[0].getHasStack())
						return null;
					ItemStack stack = itemstack1.copy();
					stack.stackSize = 1;
					slotfuel[0].putStack(stack);
					itemstack1.stackSize--;
				}
				else if(!(itemstack1.getItem() instanceof ItemOre) && manager.findMatchingIndex(itemstack1) != null)
				{
					if(slotinput.getHasStack())
						return null;
					ItemStack stack = itemstack1.copy();
					stack.stackSize = 1;
					slotinput.putStack(stack);
					itemstack1.stackSize--;
				}
			}

			if(itemstack1.stackSize <= 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();
		}
		detectAndSendChanges();
		return null;
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (int var1 = 0; var1 < this.inventorySlots.size(); ++var1)
		{
			ItemStack var2 = ((Slot)this.inventorySlots.get(var1)).getStack();
			ItemStack var3 = (ItemStack)this.inventoryItemStacks.get(var1);

			if (!ItemStack.areItemStacksEqual(var3, var2))
			{
				var3 = var2 == null ? null : var2.copy();
				this.inventoryItemStacks.set(var1, var3);

				for (int var4 = 0; var4 < this.crafters.size(); ++var4)
					((ICrafting)this.crafters.get(var4)).sendSlotContents(this, var1, var3);
			}
		}

		for (int var1 = 0; var1 < this.crafters.size(); ++var1)
		{
			ICrafting var2 = (ICrafting)this.crafters.get(var1);
			if (this.firetemp != this.firepit.fireTemp)
				var2.sendProgressBarUpdate(this, 0, (int)this.firepit.fireTemp);
			if (this.charcoal != this.firepit.charcoalCounter)
				var2.sendProgressBarUpdate(this, 1, this.firepit.charcoalCounter);
		}

		firetemp = this.firepit.fireTemp;
		charcoal = this.firepit.charcoalCounter;
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		if (par1 == 0)
			this.firepit.fireTemp = par2;
		if (par1 == 1)
			this.firepit.charcoalCounter = par2;
	}
}
