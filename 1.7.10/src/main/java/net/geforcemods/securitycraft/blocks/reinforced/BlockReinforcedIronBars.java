package net.geforcemods.securitycraft.blocks.reinforced;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.geforcemods.securitycraft.tileentity.TileEntityOwnable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockReinforcedIronBars extends BlockPane implements ITileEntityProvider, IReinforcedBlock {

	public BlockReinforcedIronBars(String par2Str, String par3Str, Material par4Material, boolean par5) {
		super(par2Str, par3Str, par4Material, par5);
		ObfuscationReflectionHelper.setPrivateValue(Block.class, this, Block.soundTypeMetal, 32);
	}

	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		par1World.setBlock(par2, par3, par4, Blocks.iron_bars);
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, Block par5Block, int par6){
		super.breakBlock(par1World, par2, par3, par4, par5Block, par6);
		par1World.removeTileEntity(par2, par3, par4);
	}

	@Override
	public boolean onBlockEventReceived(World par1World, int par2, int par3, int par4, int par5, int par6){
		super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
		TileEntity tileentity = par1World.getTileEntity(par2, par3, par4);
		return tileentity != null ? tileentity.receiveClientEvent(par5, par6) : false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_){
		return Item.getItemFromBlock(this);
	}

	/**
	 * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public Item getItemDropped(int par1, Random par2Random, int par3){
		return Item.getItemFromBlock(this);
	}

	@Override
	public TileEntity createNewTileEntity(World par1, int par2) {
		return new TileEntityOwnable();
	}

	@Override
	public List<Block> getVanillaBlocks()
	{
		return Arrays.asList(new Block[] {
				Blocks.iron_bars
		});
	}

	@Override
	public int getAmount()
	{
		return 1;
	}
}
