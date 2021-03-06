package net.geforcemods.securitycraft.blocks;

import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.api.IIntersectable;
import net.geforcemods.securitycraft.api.TileEntitySCTE;
import net.geforcemods.securitycraft.imc.waila.ICustomWailaDisplay;
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
public class BlockFakeLavaBase extends BlockStaticLiquid implements IIntersectable, ICustomWailaDisplay {

	public BlockFakeLavaBase(Material p_i45429_1_){
		super(p_i45429_1_);
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

	/**
	 * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
	 */
	public void onEntityCollision(World par1World, BlockPos pos, Entity par5Entity){
		if(!par1World.isRemote)
			if(par5Entity instanceof EntityPlayer){
				((EntityPlayer) par5Entity).heal(4);
				((EntityPlayer) par5Entity).extinguish();
			}
	}

	@Override
	public void onEntityIntersected(World world, BlockPos pos, Entity entity) {
		if(!world.isRemote)
			if(entity instanceof EntityPlayer){
				((EntityPlayer) entity).heal(4);
				((EntityPlayer) entity).extinguish();
			}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getItem(World par1World, BlockPos pos, IBlockState state){
		return ItemStack.EMPTY;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntitySCTE().intersectsEntities();
	}

	@Override
	public ItemStack getDisplayStack(World world, IBlockState state, BlockPos pos)
	{
		return new ItemStack(Blocks.LAVA);
	}

	@Override
	public boolean shouldShowSCInfo(World world, IBlockState state, BlockPos pos)
	{
		return false;
	}

}