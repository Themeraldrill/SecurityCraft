package net.geforcemods.securitycraft.blocks;

import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.api.IIntersectable;
import net.geforcemods.securitycraft.api.TileEntitySCTE;
import net.geforcemods.securitycraft.imc.waila.ICustomWailaDisplay;
import net.geforcemods.securitycraft.misc.CustomDamageSources;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//TODO: look at this class for changed method signatures
public class BlockFakeWaterBase extends BlockStaticLiquid implements IIntersectable, ICustomWailaDisplay {

	public BlockFakeWaterBase(Material par2Material)
	{
		super(par2Material);
	}

	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		if (!checkForMixing(worldIn, pos, state))
			updateLiquid(worldIn, pos, state);
	}

	private void updateLiquid(World worldIn, BlockPos p_176370_2_, IBlockState p_176370_3_)
	{
		BlockDynamicLiquid blockdynamicliquid = getFlowingBlock(this.material);
		worldIn.setBlockState(p_176370_2_, blockdynamicliquid.getDefaultState().withProperty(LEVEL, p_176370_3_.getValue(LEVEL)), 2);
		worldIn.scheduleUpdate(p_176370_2_, blockdynamicliquid, tickRate(worldIn));
	}

	public static BlockDynamicLiquid getFlowingBlock(Material materialIn)
	{
		if (materialIn == Material.WATER)
			return (BlockDynamicLiquid) SCContent.bogusWaterFlowing;
		else if (materialIn == Material.LAVA)
			return (BlockDynamicLiquid) SCContent.bogusLavaFlowing;
		else
			throw new IllegalArgumentException("Invalid material");
	}

	@Override
	public void onEntityIntersected(World world, BlockPos pos, Entity entity) {
		if(!world.isRemote)
			if(entity instanceof EntityPlayer && !((EntityPlayer) entity).capabilities.isCreativeMode)
				((EntityPlayer) entity).attackEntityFrom(CustomDamageSources.fakeWater, 5F);
			else
				entity.attackEntityFrom(CustomDamageSources.fakeWater, 5F);
	}

	/**
	 * Gets an item for the block being called on. Args: world, x, y, z
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getItem(World p_149694_1_, BlockPos pos, IBlockState state)
	{
		return ItemStack.EMPTY;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntitySCTE().intersectsEntities();
	}

	@Override
	public ItemStack getDisplayStack(World world, IBlockState state, BlockPos pos)
	{
		return new ItemStack(Blocks.WATER);
	}

	@Override
	public boolean shouldShowSCInfo(World world, IBlockState state, BlockPos pos)
	{
		return false;
	}
}
