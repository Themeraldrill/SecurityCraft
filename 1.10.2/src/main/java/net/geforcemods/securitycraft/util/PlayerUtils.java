package net.geforcemods.securitycraft.util;

import java.util.Iterator;
import java.util.List;

import net.geforcemods.securitycraft.entity.EntitySecurityCamera;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class PlayerUtils{

	/**
	 * Gets the EntityPlayer instance of a player (if they're online) using their name. <p>
	 *
	 * Args: playerName.
	 */
	public static EntityPlayer getPlayerFromName(String par1){
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT){
			List<?> players = Minecraft.getMinecraft().theWorld.playerEntities;
			Iterator<?> iterator = players.iterator();

			while(iterator.hasNext()){
				EntityPlayer tempPlayer = (EntityPlayer) iterator.next();
				if(tempPlayer.getName().matches(par1))
					return tempPlayer;
			}

			return null;
		}else{
			List<?> players = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerList();
			Iterator<?> iterator = players.iterator();

			while(iterator.hasNext()){
				EntityPlayer tempPlayer = (EntityPlayer) iterator.next();
				if(tempPlayer.getName().matches(par1))
					return tempPlayer;
			}

			return null;
		}
	}

	public static EntityPlayer getPlayerByUUID(String uuid){
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT){
			List<?> players = Minecraft.getMinecraft().theWorld.playerEntities;
			Iterator<?> iterator = players.iterator();

			while(iterator.hasNext()){
				EntityPlayer tempPlayer = (EntityPlayer) iterator.next();
				if(tempPlayer.getGameProfile().getId().toString().matches(uuid))
					return tempPlayer;
			}

			return null;
		}else{
			List<?> players = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerList();
			Iterator<?> iterator = players.iterator();

			while(iterator.hasNext()){
				EntityPlayer tempPlayer = (EntityPlayer) iterator.next();
				if(tempPlayer.getGameProfile().getId().toString().matches(uuid))
					return tempPlayer;
			}

			return null;
		}
	}

	/**
	 * Returns true if a player with the given name is in the world.
	 *
	 * Args: playerName.
	 */
	public static boolean isPlayerOnline(String par1) {
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT){
			for(int i = 0; i < Minecraft.getMinecraft().theWorld.playerEntities.size(); i++){
				EntityPlayer player = Minecraft.getMinecraft().theWorld.playerEntities.get(i);

				if(player != null && player.getName().matches(par1))
					return true;
			}

			return false;
		}
		else
			return (FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(par1) != null);
	}

	public static void sendMessageToPlayer(EntityPlayer player, String prefix, String text, TextFormatting color){
		player.addChatComponentMessage(new TextComponentString("[" + color + prefix + TextFormatting.WHITE + "] " + text));
	}

	public static void sendMessageToPlayer(ICommandSender sender, String prefix, String text, TextFormatting color){
		sender.addChatMessage(new TextComponentString("[" + color + prefix + TextFormatting.WHITE + "] " + text));
	}

	/**
	 * Sends the given {@link ICommandSender} a chat message, followed by a link prefixed with a colon. <p>
	 *
	 * Args: sender, prefix, text, link, color.
	 */
	public static void sendMessageEndingWithLink(ICommandSender sender, String prefix, String text, String link, TextFormatting color){
		sender.addChatMessage(new TextComponentString("[" + color + prefix + TextFormatting.WHITE + "] " + text + ": ").appendSibling(ForgeHooks.newChatWithLinks(link)));
	}

	/**
	 * Returns true if the player is holding the given item.
	 *
	 * Args: player, item.
	 */
	public static boolean isHoldingItem(EntityPlayer player, Item item){
		if(item == null && player.inventory.getCurrentItem() == null)
			return true;

		return (player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() == item);
	}

	/**
	 * Is the entity mounted on to a security camera?
	 *
	 * Args: entity.
	 */
	public static boolean isPlayerMountedOnCamera(EntityLivingBase entity) {
		return entity.getRidingEntity() != null && entity.getRidingEntity() instanceof EntitySecurityCamera;
	}
}
