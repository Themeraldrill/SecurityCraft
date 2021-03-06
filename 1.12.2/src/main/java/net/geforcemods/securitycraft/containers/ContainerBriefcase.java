package net.geforcemods.securitycraft.containers;

import net.geforcemods.securitycraft.SCContent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
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
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if(index < BriefcaseInventory.SIZE) {
				if(!mergeItemStack(itemstack1, BriefcaseInventory.SIZE, 48, true))
					return ItemStack.EMPTY;

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if(index >= BriefcaseInventory.SIZE)
				if(!mergeItemStack(itemstack1, 0, BriefcaseInventory.SIZE, false))
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
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player) {
		if(slot >= 0 && getSlot(slot) != null && ((!player.getHeldItemMainhand().isEmpty() && getSlot(slot).getStack() == player.getHeldItemMainhand()) || (!player.getHeldItemOffhand().isEmpty() && getSlot(slot).getStack() == player.getHeldItemOffhand())))
			return ItemStack.EMPTY;

		return super.slotClick(slot, dragType, clickTypeIn, player);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

}
