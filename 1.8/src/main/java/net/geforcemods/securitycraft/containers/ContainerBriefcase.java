package net.geforcemods.securitycraft.containers;

import net.geforcemods.securitycraft.SCContent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBriefcase extends Container {

	private BriefcaseInventory inventory;

	public ContainerBriefcase(EntityPlayer par1Player, InventoryPlayer playerInventory, BriefcaseInventory briefcaseInventory) {
		inventory = briefcaseInventory;

		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 4; j++)
				addSlotToContainer(new SlotItemRestricted(inventory, j + (i * 4), 53 + (j * 18), 17 + (i * 18), SCContent.briefcase));

		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for(int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int index) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if(index < BriefcaseInventory.SIZE) {
				if(!mergeItemStack(itemstack1, BriefcaseInventory.SIZE, 48, true))
					return null;

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if(index >= BriefcaseInventory.SIZE)
				if(!mergeItemStack(itemstack1, 0, BriefcaseInventory.SIZE, false))
					return null;

			if(itemstack1.stackSize == 0)
				slot.putStack((ItemStack) null);
			else
				slot.onSlotChanged();

			if(itemstack1.stackSize == itemstack.stackSize)
				return null;

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}

		return itemstack;
	}

	@Override
	public ItemStack slotClick(int slot, int button, int flag, EntityPlayer player) {
		if(slot >= 0 && getSlot(slot) != null && getSlot(slot).getStack() == player.getHeldItem())
			return null;

		return super.slotClick(slot, button, flag, player);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

}
