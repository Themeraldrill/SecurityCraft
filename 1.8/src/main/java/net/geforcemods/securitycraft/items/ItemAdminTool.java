package net.geforcemods.securitycraft.items;

import java.util.List;

import net.geforcemods.securitycraft.api.CustomizableSCTE;
import net.geforcemods.securitycraft.api.IOwnable;
import net.geforcemods.securitycraft.api.IPasswordProtected;
import net.geforcemods.securitycraft.main.Utils.PlayerUtils;
import net.geforcemods.securitycraft.main.mod_SecurityCraft;
import net.geforcemods.securitycraft.misc.EnumCustomModules;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemAdminTool extends Item {

	public ItemAdminTool() {
		super();
		
		if(mod_SecurityCraft.configHandler.allowAdminTool){
			this.setCreativeTab(mod_SecurityCraft.tabSCTechnical);
		}
	}
	
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, BlockPos pos, EnumFacing par5EnumFacing, float hitX, float hitY, float hitZ){
		if(!par3World.isRemote){
			if(par3World.getTileEntity(pos) != null){
				TileEntity te = par3World.getTileEntity(pos);
				
				if(te instanceof IOwnable){
					PlayerUtils.sendMessageToPlayer(par2EntityPlayer, "Admin Tool", "Owner: " + (((IOwnable) te).getOwnerName() == null ? "????" : ((IOwnable) te).getOwnerName()), EnumChatFormatting.DARK_PURPLE);
					PlayerUtils.sendMessageToPlayer(par2EntityPlayer, "Admin Tool", "Owner's UUID: " + (((IOwnable) te).getOwnerUUID() == null ? "????" : ((IOwnable) te).getOwnerUUID()), EnumChatFormatting.DARK_PURPLE);
				}
				
				if(te instanceof IPasswordProtected){
					PlayerUtils.sendMessageToPlayer(par2EntityPlayer, "Admin Tool", "Password: " + (((IPasswordProtected) te).getPassword() == null ? "????" : ((IPasswordProtected) te).getPassword()), EnumChatFormatting.DARK_PURPLE);
				}
				
				if(te instanceof CustomizableSCTE){
					List<EnumCustomModules> modules = ((CustomizableSCTE) te).getModules();
					
					if(!modules.isEmpty()){
						PlayerUtils.sendMessageToPlayer(par2EntityPlayer, "Admin Tool", "Equipped modules: ", EnumChatFormatting.DARK_PURPLE);
						
						for(EnumCustomModules module : modules){
							PlayerUtils.sendMessageToPlayer(par2EntityPlayer, "Admin Tool", "-" + module.getModuleName(), EnumChatFormatting.DARK_PURPLE);
						}
					}
				}
				
				return true;
			}
		}
		
		return false;
	}

}