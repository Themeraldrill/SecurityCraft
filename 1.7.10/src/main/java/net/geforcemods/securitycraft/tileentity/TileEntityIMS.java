package net.geforcemods.securitycraft.tileentity;

import java.util.Iterator;
import java.util.List;

import net.geforcemods.securitycraft.SecurityCraft;
import net.geforcemods.securitycraft.api.CustomizableSCTE;
import net.geforcemods.securitycraft.api.Option;
import net.geforcemods.securitycraft.entity.EntityIMSBomb;
import net.geforcemods.securitycraft.misc.EnumCustomModules;
import net.geforcemods.securitycraft.network.packets.PacketCPlaySoundAtPos;
import net.geforcemods.securitycraft.util.ModuleUtils;
import net.geforcemods.securitycraft.util.PlayerUtils;
import net.geforcemods.securitycraft.util.WorldUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityIMS extends CustomizableSCTE {

	/** Number of bombs remaining in storage. **/
	private int bombsRemaining = 4;

	/** The targeting option currently selected for this IMS. 0 = players, 1 = hostile mobs & players.**/
	private int targetingOption = 1;

	@Override
	public void updateEntity(){
		if(worldObj.getTotalWorldTime() % 80L == 0L)
			launchMine();
	}

	/**
	 * Create a bounding box around the IMS, and fire a mine if a mob or player is found.
	 */
	private void launchMine() {
		if(bombsRemaining > 0){
			double d0 = SecurityCraft.config.imsRange;

			AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1).expand(d0, d0, d0);
			List<?> list1 = worldObj.getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);
			List<?> list2 = worldObj.getEntitiesWithinAABB(IMob.class, axisalignedbb);
			Iterator<?> iterator1 = list1.iterator();
			Iterator<?> iterator2 = list2.iterator();

			while(targetingOption == 1 && iterator2.hasNext()){
				EntityLivingBase entity = (EntityLivingBase) iterator2.next();
				int launchHeight = getLaunchHeight();

				if(PlayerUtils.isPlayerMountedOnCamera(entity))
					continue;

				if(WorldUtils.isPathObstructed(worldObj, xCoord + 0.5D, yCoord + (((launchHeight - 1) / 3) + 0.5D), zCoord + 0.5D, entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ))
					continue;
				if(hasModule(EnumCustomModules.WHITELIST) && ModuleUtils.getPlayersFromModule(worldObj, xCoord, yCoord, zCoord, EnumCustomModules.WHITELIST).contains(entity.getCommandSenderName().toLowerCase()))
					continue;

				double d5 = entity.posX - (xCoord + 0.5D);
				double d6 = entity.boundingBox.minY + entity.height / 2.0F - (yCoord + 1.25D);
				double d7 = entity.posZ - (zCoord + 0.5D);

				this.spawnMine(entity, d5, d6, d7, launchHeight);

				if(worldObj.isRemote)
					SecurityCraft.network.sendToAll(new PacketCPlaySoundAtPos(xCoord, yCoord, zCoord, "random.bow", 1.0F));

				bombsRemaining--;

				if(bombsRemaining == 0)
					worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord), 140);

				return;
			}

			while(iterator1.hasNext()){
				EntityPlayer entity = (EntityPlayer) iterator1.next();
				int launchHeight = getLaunchHeight();
				if((entity != null && getOwner().isOwner((entity))) || PlayerUtils.isPlayerMountedOnCamera(entity))
					continue;
				if(WorldUtils.isPathObstructed(worldObj, xCoord + 0.5D, yCoord + (((launchHeight - 1) / 3) + 0.5D), zCoord + 0.5D, entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ))
					continue;
				if(hasModule(EnumCustomModules.WHITELIST) && ModuleUtils.getPlayersFromModule(worldObj, xCoord, yCoord, zCoord, EnumCustomModules.WHITELIST).contains(entity.getCommandSenderName()))
					continue;

				double d5 = entity.posX - (xCoord + 0.5D);
				double d6 = entity.boundingBox.minY + entity.height / 2.0F - (yCoord + 1.25D);
				double d7 = entity.posZ - (zCoord + 0.5D);

				this.spawnMine(entity, d5, d6, d7, launchHeight);

				if(worldObj.isRemote)
					SecurityCraft.network.sendToAll(new PacketCPlaySoundAtPos(xCoord, yCoord, zCoord, "random.bow", 1.0F));

				bombsRemaining--;

				if(bombsRemaining == 0)
					worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord), 140);
			}
		}
	}

	/**
	 * Spawn a mine at the correct position on the IMS model.
	 */
	private void spawnMine(EntityPlayer target, double x, double y, double z, int launchHeight){
		if(bombsRemaining == 4){
			EntityIMSBomb entitylargefireball = new EntityIMSBomb(worldObj, target, xCoord + 1.2D, yCoord, zCoord + 1.2D, x, y, z, launchHeight);
			worldObj.spawnEntityInWorld(entitylargefireball);
		}else if(bombsRemaining == 3){
			EntityIMSBomb entitylargefireball = new EntityIMSBomb(worldObj, target, xCoord + 1.2D, yCoord, zCoord + 0.6D, x, y, z, launchHeight);
			worldObj.spawnEntityInWorld(entitylargefireball);
		}else if(bombsRemaining == 2){
			EntityIMSBomb entitylargefireball = new EntityIMSBomb(worldObj, target, xCoord + 0.55D, yCoord, zCoord + 1.2D, x, y, z, launchHeight);
			worldObj.spawnEntityInWorld(entitylargefireball);
		}else if(bombsRemaining == 1){
			EntityIMSBomb entitylargefireball = new EntityIMSBomb(worldObj, target, xCoord + 0.55D, yCoord, zCoord + 0.6D, x, y, z, launchHeight);
			worldObj.spawnEntityInWorld(entitylargefireball);
		}
	}

	/**
	 * Spawn a mine at the correct position on the IMS model.
	 */
	private void spawnMine(EntityLivingBase target, double x, double y, double z, int launchHeight){
		if(bombsRemaining == 4){
			EntityIMSBomb entitylargefireball = new EntityIMSBomb(worldObj, target, xCoord + 1.2D, yCoord, zCoord + 1.2D, x, y, z, launchHeight);
			worldObj.spawnEntityInWorld(entitylargefireball);
		}else if(bombsRemaining == 3){
			EntityIMSBomb entitylargefireball = new EntityIMSBomb(worldObj, target, xCoord + 1.2D, yCoord, zCoord + 0.6D, x, y, z, launchHeight);
			worldObj.spawnEntityInWorld(entitylargefireball);
		}else if(bombsRemaining == 2){
			EntityIMSBomb entitylargefireball = new EntityIMSBomb(worldObj, target, xCoord + 0.55D, yCoord, zCoord + 1.2D, x, y, z, launchHeight);
			worldObj.spawnEntityInWorld(entitylargefireball);
		}else if(bombsRemaining == 1){
			EntityIMSBomb entitylargefireball = new EntityIMSBomb(worldObj, target, xCoord + 0.55D, yCoord, zCoord + 0.6D, x, y, z, launchHeight);
			worldObj.spawnEntityInWorld(entitylargefireball);
		}
	}

	/**
	 * Returns the amount of ticks the {@link EntityIMSBomb} should float in the air before firing at an entity.
	 */
	private int getLaunchHeight() {
		int height;

		for(height = 1; height <= 9; height++)
			if(worldObj.getBlock(xCoord, yCoord + height, zCoord) == null || worldObj.getBlock(xCoord, yCoord + height, zCoord) == Blocks.air)
				continue;
			else
				break;

		return height * 3;
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound){
		super.writeToNBT(par1NBTTagCompound);

		par1NBTTagCompound.setInteger("bombsRemaining", bombsRemaining);
		par1NBTTagCompound.setInteger("targetingOption", targetingOption);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound){
		super.readFromNBT(par1NBTTagCompound);

		if (par1NBTTagCompound.hasKey("bombsRemaining"))
			bombsRemaining = par1NBTTagCompound.getInteger("bombsRemaining");

		if (par1NBTTagCompound.hasKey("targetingOption"))
			targetingOption = par1NBTTagCompound.getInteger("targetingOption");
	}

	public int getBombsRemaining() {
		return bombsRemaining;
	}

	public void setBombsRemaining(int bombsRemaining) {
		this.bombsRemaining = bombsRemaining;
	}

	public int getTargetingOption() {
		return targetingOption;
	}

	public void setTargetingOption(int targetingOption) {
		this.targetingOption = targetingOption;
	}

	@Override
	public EnumCustomModules[] acceptedModules() {
		return new EnumCustomModules[]{EnumCustomModules.WHITELIST};
	}

	@Override
	public Option<?>[] customOptions() {
		return null;
	}

}
