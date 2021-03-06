package net.geforcemods.securitycraft.util;

import net.geforcemods.securitycraft.api.CustomizableSCTE;
import net.geforcemods.securitycraft.api.Owner;
import net.geforcemods.securitycraft.tileentity.TileEntityKeypad;
import net.geforcemods.securitycraft.tileentity.TileEntityKeypadChest;
import net.geforcemods.securitycraft.tileentity.TileEntityKeypadFurnace;
import net.geforcemods.securitycraft.tileentity.TileEntityOwnable;
import net.geforcemods.securitycraft.tileentity.TileEntityPortableRadar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLever;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockUtils{

	public static void setBlockInBox(World par1World, int par2, int par3, int par4, Block par5){
		BlockUtils.setBlock(par1World, par2 + 1, par3 + 1, par4, par5);
		BlockUtils.setBlock(par1World, par2 + 1, par3 + 2, par4, par5);
		BlockUtils.setBlock(par1World, par2 + 1, par3 + 3, par4, par5);
		BlockUtils.setBlock(par1World, par2 + 1, par3 + 1, par4 + 1, par5);
		BlockUtils.setBlock(par1World, par2 + 1, par3 + 2, par4 + 1, par5);
		BlockUtils.setBlock(par1World, par2 + 1, par3 + 3, par4 + 1, par5);
		BlockUtils.setBlock(par1World, par2 - 1, par3 + 1, par4, par5);
		BlockUtils.setBlock(par1World, par2 - 1, par3 + 2, par4, par5);
		BlockUtils.setBlock(par1World, par2 - 1, par3 + 3, par4, par5);
		BlockUtils.setBlock(par1World, par2 - 1, par3 + 1, par4 + 1, par5);
		BlockUtils.setBlock(par1World, par2 - 1, par3 + 2, par4 + 1, par5);
		BlockUtils.setBlock(par1World, par2 - 1, par3 + 3, par4 + 1, par5);
		BlockUtils.setBlock(par1World, par2, par3 + 1, par4 + 1, par5);
		BlockUtils.setBlock(par1World, par2, par3 + 2, par4 + 1, par5);
		BlockUtils.setBlock(par1World, par2, par3 + 3, par4 + 1, par5);
		BlockUtils.setBlock(par1World, par2 + 1, par3 + 1, par4, par5);
		BlockUtils.setBlock(par1World, par2 + 1, par3 + 2, par4, par5);
		BlockUtils.setBlock(par1World, par2 + 1, par3 + 3, par4, par5);

		BlockUtils.setBlock(par1World, par2, par3 + 1, par4 - 1, par5);
		BlockUtils.setBlock(par1World, par2, par3 + 2, par4 - 1, par5);
		BlockUtils.setBlock(par1World, par2, par3 + 3, par4 - 1, par5);
		BlockUtils.setBlock(par1World, par2 + 1, par3 + 1, par4 - 1, par5);
		BlockUtils.setBlock(par1World, par2 + 1, par3 + 2, par4 - 1, par5);
		BlockUtils.setBlock(par1World, par2 + 1, par3 + 3, par4 - 1, par5);

		BlockUtils.setBlock(par1World, par2 - 1, par3 + 1, par4 - 1, par5);
		BlockUtils.setBlock(par1World, par2 - 1, par3 + 2, par4 - 1, par5);
		BlockUtils.setBlock(par1World, par2 - 1, par3 + 3, par4 - 1, par5);

		BlockUtils.setBlock(par1World, par2 + 1, par3 + 4, par4 + 1, par5);
		BlockUtils.setBlock(par1World, par2 + 1, par3 + 4, par4 - 1, par5);
		BlockUtils.setBlock(par1World, par2 - 1, par3 + 4, par4 + 1, par5);
		BlockUtils.setBlock(par1World, par2 - 1, par3 + 4, par4 - 1, par5);
	}

	/**
	 * Updates a block and notify's neighboring blocks of a change.
	 *
	 * Args: worldObj, pos, blockID, tickRate, shouldUpdate
	 *
	 *
	 */
	public static void updateAndNotify(World par1World, BlockPos pos, Block par5, int par6, boolean par7){
		if(par7)
			par1World.scheduleUpdate(pos, par5, par6);
		par1World.notifyBlockOfStateChange(pos.east(), par1World.getBlockState(pos).getBlock());
		par1World.notifyBlockOfStateChange(pos.west(), par1World.getBlockState(pos).getBlock());
		par1World.notifyBlockOfStateChange(pos.south(), par1World.getBlockState(pos).getBlock());
		par1World.notifyBlockOfStateChange(pos.north(), par1World.getBlockState(pos).getBlock());
		par1World.notifyBlockOfStateChange(pos.up(), par1World.getBlockState(pos).getBlock());
		par1World.notifyBlockOfStateChange(pos.down(), par1World.getBlockState(pos).getBlock());
	}

	public static void destroyBlock(World par1World, BlockPos pos, boolean par5){
		par1World.destroyBlock(pos, par5);
	}

	public static void destroyBlock(World par1World, int par2, int par3, int par4, boolean par5){
		par1World.destroyBlock(toPos(par2, par3, par4), par5);
	}

	public static boolean isAirBlock(World par1World, BlockPos pos){
		return par1World.getBlockState(pos).getBlock() == Blocks.AIR;
	}

	public static boolean isAirBlock(World par1World, int par2, int par3, int par4){
		return par1World.getBlockState(toPos(par2, par3, par4)).getBlock() == Blocks.AIR;
	}

	public static int getBlockMeta(World par1World, BlockPos pos){
		return par1World.getBlockState(pos).getBlock().getMetaFromState(par1World.getBlockState(pos));
	}

	public static int getBlockMeta(World par1World, int par2, int par3, int par4){
		return par1World.getBlockState(toPos(par2, par3, par4)).getBlock().getMetaFromState(par1World.getBlockState(toPos(par2, par3, par4)));
	}

	public static void setBlock(World par1World, BlockPos pos, Block block){
		par1World.setBlockState(pos, block.getDefaultState());
	}

	public static void setBlock(World par1World, int par2, int par3, int par4, Block block){
		setBlock(par1World, toPos(par2, par3, par4), block);
	}

	public static Block getBlock(World par1World, BlockPos pos){
		return par1World.getBlockState(pos).getBlock();
	}

	public static Block getBlock(World par1World, int par2, int par3, int par4){
		return par1World.getBlockState(toPos(par2, par3, par4)).getBlock();
	}

	public static void setBlockProperty(World par1World, BlockPos pos, PropertyBool property, boolean value) {
		setBlockProperty(par1World, pos, property, value, false);
	}

	public static void setBlockProperty(World par1World, BlockPos pos, PropertyBool property, boolean value, boolean retainOldTileEntity) {
		if(retainOldTileEntity){
			ItemStack[] modules = null;
			ItemStack[] inventory = null;
			int[] times = new int[4];
			String password = "";
			Owner owner = null;
			int cooldown = -1;

			if(par1World.getTileEntity(pos) instanceof CustomizableSCTE)
				modules = ((CustomizableSCTE) par1World.getTileEntity(pos)).itemStacks;

			if(par1World.getTileEntity(pos) instanceof TileEntityKeypadFurnace){
				inventory = ((TileEntityKeypadFurnace) par1World.getTileEntity(pos)).furnaceItemStacks;
				times[0] = ((TileEntityKeypadFurnace) par1World.getTileEntity(pos)).furnaceBurnTime;
				times[1] = ((TileEntityKeypadFurnace) par1World.getTileEntity(pos)).currentItemBurnTime;
				times[2] = ((TileEntityKeypadFurnace) par1World.getTileEntity(pos)).cookTime;
				times[3] = ((TileEntityKeypadFurnace) par1World.getTileEntity(pos)).totalCookTime;
			}

			if(par1World.getTileEntity(pos) instanceof TileEntityOwnable && ((TileEntityOwnable) par1World.getTileEntity(pos)).getOwner() != null)
				owner = ((TileEntityOwnable) par1World.getTileEntity(pos)).getOwner();

			if(par1World.getTileEntity(pos) instanceof TileEntityKeypad && ((TileEntityKeypad) par1World.getTileEntity(pos)).getPassword() != null)
				password = ((TileEntityKeypad) par1World.getTileEntity(pos)).getPassword();

			if(par1World.getTileEntity(pos) instanceof TileEntityKeypadFurnace && ((TileEntityKeypadFurnace) par1World.getTileEntity(pos)).getPassword() != null)
				password = ((TileEntityKeypadFurnace) par1World.getTileEntity(pos)).getPassword();

			if(par1World.getTileEntity(pos) instanceof TileEntityKeypadChest && ((TileEntityKeypadChest) par1World.getTileEntity(pos)).getPassword() != null)
				password = ((TileEntityKeypadChest) par1World.getTileEntity(pos)).getPassword();

			if(par1World.getTileEntity(pos) instanceof TileEntityPortableRadar && ((TileEntityPortableRadar) par1World.getTileEntity(pos)).getAttackCooldown() != 0)
				cooldown = ((TileEntityPortableRadar) par1World.getTileEntity(pos)).getAttackCooldown();

			TileEntity tileEntity = par1World.getTileEntity(pos);
			par1World.setBlockState(pos, par1World.getBlockState(pos).withProperty(property, value));
			par1World.setTileEntity(pos, tileEntity);

			if(modules != null)
				((CustomizableSCTE) par1World.getTileEntity(pos)).itemStacks = modules;

			if(inventory != null && par1World.getTileEntity(pos) instanceof TileEntityKeypadFurnace){
				((TileEntityKeypadFurnace) par1World.getTileEntity(pos)).furnaceItemStacks = inventory;
				((TileEntityKeypadFurnace) par1World.getTileEntity(pos)).furnaceBurnTime = times[0];
				((TileEntityKeypadFurnace) par1World.getTileEntity(pos)).currentItemBurnTime = times[1];
				((TileEntityKeypadFurnace) par1World.getTileEntity(pos)).cookTime = times[2];
				((TileEntityKeypadFurnace) par1World.getTileEntity(pos)).totalCookTime = times[3];
			}

			if(owner != null)
				((TileEntityOwnable) par1World.getTileEntity(pos)).getOwner().set(owner);

			if(!password.isEmpty() && par1World.getTileEntity(pos) instanceof TileEntityKeypad)
				((TileEntityKeypad) par1World.getTileEntity(pos)).setPassword(password);

			if(!password.isEmpty() && par1World.getTileEntity(pos) instanceof TileEntityKeypadFurnace)
				((TileEntityKeypadFurnace) par1World.getTileEntity(pos)).setPassword(password);

			if(!password.isEmpty() && par1World.getTileEntity(pos) instanceof TileEntityKeypadChest)
				((TileEntityKeypadChest) par1World.getTileEntity(pos)).setPassword(password);

			if(cooldown != -1 && par1World.getTileEntity(pos) instanceof TileEntityPortableRadar)
				((TileEntityPortableRadar) par1World.getTileEntity(pos)).setAttackCooldown(cooldown);
		}
		else
			par1World.setBlockState(pos, par1World.getBlockState(pos).withProperty(property, value));
	}

	public static void setBlockProperty(World par1World, int par2, int par3, int par4, PropertyBool property, boolean value) {
		ItemStack[] modules = null;
		if(par1World.getTileEntity(new BlockPos(par2, par3, par4)) instanceof CustomizableSCTE)
			modules = ((CustomizableSCTE) par1World.getTileEntity(toPos(par2, par3, par4))).itemStacks;

		TileEntity tileEntity = par1World.getTileEntity(toPos(par2, par3, par4));
		par1World.setBlockState(new BlockPos(par2, par3, par4), par1World.getBlockState(new BlockPos(par2, par3, par4)).withProperty(property, value));
		par1World.setTileEntity(new BlockPos(par2, par3, par4), tileEntity);

		if(modules != null)
			((CustomizableSCTE) par1World.getTileEntity(toPos(par2, par3, par4))).itemStacks = modules;
	}

	public static void setBlockProperty(World par1World, BlockPos pos, PropertyInteger property, int value) {
		par1World.setBlockState(pos, par1World.getBlockState(pos).withProperty(property, value));
	}

	public static void setBlockProperty(World par1World, BlockPos pos, PropertyEnum property, EnumFacing value) {
		par1World.setBlockState(pos, par1World.getBlockState(pos).withProperty(property, value));
	}

	public static boolean hasBlockProperty(World par1World, BlockPos pos, IProperty<?> property){
		try{
			par1World.getBlockState(pos).getValue(property);
			return true;
		}catch(IllegalArgumentException e){
			return false;
		}
	}

	public static boolean hasBlockProperty(World par1World, int par2, int par3, int par4, IProperty<?> property){
		return hasBlockProperty(par1World, toPos(par2, par3, par4), property);
	}

	public static boolean getBlockPropertyAsBoolean(World par1World, BlockPos pos, PropertyBool property){
		return par1World.getBlockState(pos).getValue(property).booleanValue();
	}

	public static boolean getBlockPropertyAsBoolean(World par1World, int par2, int par3, int par4, PropertyBool property){
		return par1World.getBlockState(toPos(par2, par3, par4)).getValue(property).booleanValue();
	}

	public static int getBlockPropertyAsInteger(World par1World, BlockPos pos, PropertyInteger property){
		return par1World.getBlockState(pos).getValue(property).intValue();
	}

	public static int getBlockPropertyAsInteger(World par1World, int par2, int par3, int par4, PropertyInteger property){
		return par1World.getBlockState(toPos(par2, par3, par4)).getValue(property).intValue();
	}

	public static EnumFacing getBlockPropertyAsEnum(World par1World, BlockPos pos, PropertyEnum<?> property){
		return ((EnumFacing) par1World.getBlockState(pos).getValue(property));
	}

	public static EnumFacing getBlockPropertyAsEnum(World par1World, int par2, int par3, int par4, PropertyEnum<?> property){
		return ((EnumFacing) par1World.getBlockState(toPos(par2, par3, par4)).getValue(property));
	}

	public static EnumFacing getBlockPropertyAsEnum(IBlockAccess par1World, BlockPos pos, PropertyEnum<?> property){
		return ((EnumFacing) par1World.getBlockState(pos).getValue(property));
	}

	public static EnumFacing getBlockPropertyAsEnum(IBlockAccess par1World, int par2, int par3, int par4, PropertyEnum<?> property){
		return ((EnumFacing) par1World.getBlockState(toPos(par2, par3, par4)).getValue(property));
	}

	public static BlockLever.EnumOrientation getBlockPropertyAsOrientation(World par1World, BlockPos pos, PropertyEnum<?> property){
		return ((BlockLever.EnumOrientation) par1World.getBlockState(pos).getValue(property));
	}

	public static BlockLever.EnumOrientation getBlockPropertyAsOrientation(World par1World, int par2, int par3, int par4, PropertyEnum<?> property){
		return ((BlockLever.EnumOrientation) par1World.getBlockState(toPos(par2, par3, par4)).getValue(property));
	}

	public static EnumFacing getBlockProperty(World par1World, BlockPos pos, PropertyDirection property) {
		return par1World.getBlockState(pos).getValue(property);
	}

	public static EnumFacing getBlockProperty(World par1World, int par2, int par3, int par4, PropertyDirection property) {
		return par1World.getBlockState(new BlockPos(par2, par3, par4)).getValue(property);
	}

	public static Material getBlockMaterial(World par1World, BlockPos pos){
		return par1World.getBlockState(pos).getMaterial();
	}

	/**
	 * returns an AABB with corners x1, y1, z1 and x2, y2, z2
	 */
	public static AxisAlignedBB fromBounds(double x1, double y1, double z1, double x2, double y2, double z2)
	{
		double d6 = Math.min(x1, x2);
		double d7 = Math.min(y1, y2);
		double d8 = Math.min(z1, z2);
		double d9 = Math.max(x1, x2);
		double d10 = Math.max(y1, y2);
		double d11 = Math.max(z1, z2);
		return new AxisAlignedBB(d6, d7, d8, d9, d10, d11);
	}

	/**
	 * Checks if the block at x, y, z is touching the specified block on any side.
	 */
	public static boolean blockSurroundedBy(World world, BlockPos pos, Block blockToCheckFor, boolean checkYAxis) {
		if(!checkYAxis && (world.getBlockState(pos.east()).getBlock() == blockToCheckFor || world.getBlockState(pos.west()).getBlock() == blockToCheckFor || world.getBlockState(pos.south()).getBlock() == blockToCheckFor || world.getBlockState(pos.north()).getBlock() == blockToCheckFor))
			return true;
		else if(checkYAxis && (world.getBlockState(pos.east()).getBlock() == blockToCheckFor || world.getBlockState(pos.west()).getBlock() == blockToCheckFor || world.getBlockState(pos.south()).getBlock() == blockToCheckFor || world.getBlockState(pos.north()).getBlock() == blockToCheckFor || world.getBlockState(pos.up()).getBlock() == blockToCheckFor || world.getBlockState(pos.down()).getBlock() == blockToCheckFor))
			return true;
		else
			return false;
	}

	public static ItemStack getItemInTileEntity(IInventory inventory, ItemStack item){
		for(int i = 0; i < inventory.getSizeInventory(); i++)
			if(inventory.getStackInSlot(i) != null)
				if(inventory.getStackInSlot(i) == item)
					return inventory.getStackInSlot(i);

		return null;
	}

	public static BlockPos toPos(int x, int y, int z){
		return new BlockPos(x, y, z);
	}

	public static int[] fromPos(BlockPos pos){
		return new int[]{pos.getX(), pos.getY(), pos.getZ()};
	}

}
