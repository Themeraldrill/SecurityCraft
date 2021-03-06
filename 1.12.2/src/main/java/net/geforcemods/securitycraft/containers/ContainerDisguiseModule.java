package net.geforcemods.securitycraft.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerDisguiseModule extends Container {

	private ModuleInventory inventory;

	public ContainerDisguiseModule(EntityPlayer par1Player, InventoryPlayer playerInventory, ModuleInventory moduleInventory) {
		inventory = moduleInventory;
		addSlotToContainer(new AddonSlot(inventory, 0, 79, 20));

		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for(int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if(index < inventory.SIZE) {
				if(!mergeItemStack(itemstack1, inventory.SIZE, 37, true))
					return ItemStack.EMPTY;

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if(index >= inventory.SIZE)
				if(!mergeItemStack(itemstack1, 0, inventory.SIZE, false))
					return ItemStack.EMPTY;

			if(itemstack1.getCount() == 0)
				slot.putStack(ItemStack.EMPTY);
			else
				slot.onSlotChanged();

			if(itemstack1.getCount() == itemstack.getCount())
				return ItemStack.EMPTY;

			slot.onTake(par1EntityPlayer, itemstack1);
		}

		return itemstack;
	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player)
	{
		if(slot >= 0 && getSlot(slot) != null && ((!player.getHeldItemMainhand().isEmpty() && getSlot(slot).getStack() == player.getHeldItemMainhand()) || (!player.getHeldItemOffhand().isEmpty() && getSlot(slot).getStack() == player.getHeldItemOffhand())))
			return ItemStack.EMPTY;

		return super.slotClick(slot, dragType, clickTypeIn, player);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}


	public static class AddonSlot extends Slot {

		private ModuleInventory inventory;

		public AddonSlot(ModuleInventory par1IInventory, int par2, int par3, int par4) {
			super(par1IInventory, par2, par3, par4);
			inventory = par1IInventory;
		}

		@Override
		public boolean isItemValid(ItemStack par1ItemStack) {
			int numberOfItems = 0;
			int numberOfBlocks = 0;
			boolean isStackBlock = par1ItemStack.getTranslationKey().startsWith("tile.");

			for(ItemStack stack : inventory.moduleInventory)
				if(!stack.isEmpty() && stack.getItem() != null)
					if(stack.getItem().getTranslationKey().startsWith("tile."))
						numberOfBlocks++;
					else
						numberOfItems++;

			if(isStackBlock && numberOfBlocks < inventory.maxNumberOfBlocks)
				return true;
			else if(!isStackBlock && numberOfItems < inventory.maxNumberOfItems)
				return true;

			return false;
		}

		@Override
		public int getSlotStackLimit() {
			return 1;
		}
	}

}
