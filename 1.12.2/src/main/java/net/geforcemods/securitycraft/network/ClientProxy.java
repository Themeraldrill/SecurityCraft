package net.geforcemods.securitycraft.network;

import java.util.HashMap;

import net.geforcemods.securitycraft.RegistrationHandler;
import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.SecurityCraft;
import net.geforcemods.securitycraft.entity.EntityBouncingBetty;
import net.geforcemods.securitycraft.entity.EntityIMSBomb;
import net.geforcemods.securitycraft.imc.lookingglass.IWorldViewHelper;
import net.geforcemods.securitycraft.misc.KeyBindings;
import net.geforcemods.securitycraft.renderers.ItemKeypadChestRenderer;
import net.geforcemods.securitycraft.renderers.RenderBouncingBetty;
import net.geforcemods.securitycraft.renderers.RenderIMSBomb;
import net.geforcemods.securitycraft.renderers.TileEntityFrameRenderer;
import net.geforcemods.securitycraft.renderers.TileEntityKeypadChestRenderer;
import net.geforcemods.securitycraft.renderers.TileEntitySecurityCameraRenderer;
import net.geforcemods.securitycraft.tileentity.TileEntityFrame;
import net.geforcemods.securitycraft.tileentity.TileEntityKeypadChest;
import net.geforcemods.securitycraft.tileentity.TileEntitySecurityCamera;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientProxy extends ServerProxy{

	public HashMap<String, IWorldViewHelper> worldViews = new HashMap<String, IWorldViewHelper>();

	/**
	 * Register the texture files used by blocks with metadata/variants with the ModelBakery.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void registerTextureFiles() {
		ModelBakery.registerItemVariants(findItem(SecurityCraft.MODID, "reinforced_planks"),
				new ResourceLocation("securitycraft:reinforced_planks_oak"),
				new ResourceLocation("securitycraft:reinforced_planks_spruce"),
				new ResourceLocation("securitycraft:reinforced_planks_birch"),
				new ResourceLocation("securitycraft:reinforced_planks_jungle"),
				new ResourceLocation("securitycraft:reinforced_planks_acacia"),
				new ResourceLocation("securitycraft:reinforced_planks_dark_oak"));
		ModelBakery.registerItemVariants(findItem(SecurityCraft.MODID, "reinforced_stained_glass"),
				new ResourceLocation("securitycraft:reinforced_stained_glass_white"),
				new ResourceLocation("securitycraft:reinforced_stained_glass_orange"),
				new ResourceLocation("securitycraft:reinforced_stained_glass_magenta"),
				new ResourceLocation("securitycraft:reinforced_stained_glass_light_blue"),
				new ResourceLocation("securitycraft:reinforced_stained_glass_yellow"),
				new ResourceLocation("securitycraft:reinforced_stained_glass_lime"),
				new ResourceLocation("securitycraft:reinforced_stained_glass_pink"),
				new ResourceLocation("securitycraft:reinforced_stained_glass_gray"),
				new ResourceLocation("securitycraft:reinforced_stained_glass_silver"),
				new ResourceLocation("securitycraft:reinforced_stained_glass_cyan"),
				new ResourceLocation("securitycraft:reinforced_stained_glass_purple"),
				new ResourceLocation("securitycraft:reinforced_stained_glass_blue"),
				new ResourceLocation("securitycraft:reinforced_stained_glass_brown"),
				new ResourceLocation("securitycraft:reinforced_stained_glass_green"),
				new ResourceLocation("securitycraft:reinforced_stained_glass_red"),
				new ResourceLocation("securitycraft:reinforced_stained_glass_black"));
		ModelBakery.registerItemVariants(findItem(SecurityCraft.MODID, "reinforced_sandstone"),
				new ResourceLocation("securitycraft:reinforced_sandstone_normal"),
				new ResourceLocation("securitycraft:reinforced_sandstone_chiseled"),
				new ResourceLocation("securitycraft:reinforced_sandstone_smooth"));
		ModelBakery.registerItemVariants(findItem(SecurityCraft.MODID, "reinforced_wood_slabs"),
				new ResourceLocation("securitycraft:reinforced_wood_slabs_oak"),
				new ResourceLocation("securitycraft:reinforced_wood_slabs_spruce"),
				new ResourceLocation("securitycraft:reinforced_wood_slabs_birch"),
				new ResourceLocation("securitycraft:reinforced_wood_slabs_jungle"),
				new ResourceLocation("securitycraft:reinforced_wood_slabs_acacia"),
				new ResourceLocation("securitycraft:reinforced_wood_slabs_darkoak"));
		ModelBakery.registerItemVariants(findItem(SecurityCraft.MODID, "reinforced_stone_slabs"),
				new ResourceLocation("securitycraft:reinforced_stone_slabs_stone"),
				new ResourceLocation("securitycraft:reinforced_stone_slabs_cobblestone"),
				new ResourceLocation("securitycraft:reinforced_stone_slabs_sandstone"),
				new ResourceLocation("securitycraft:reinforced_stone_slabs_stonebrick"),
				new ResourceLocation("securitycraft:reinforced_stone_slabs_brick"),
				new ResourceLocation("securitycraft:reinforced_stone_slabs_netherbrick"),
				new ResourceLocation("securitycraft:reinforced_stone_slabs_quartz"));
		ModelBakery.registerItemVariants(findItem(SecurityCraft.MODID, "reinforced_stone_slabs2"),
				new ResourceLocation("securitycraft:reinforced_stone_slabs2_red_sandstone"),
				new ResourceLocation("securitycraft:reinforced_stone_slabs2_purpur"));
		ModelBakery.registerItemVariants(findItem(SecurityCraft.MODID, "reinforced_stone_brick"),
				new ResourceLocation("securitycraft:reinforced_stone_brick_default"),
				new ResourceLocation("securitycraft:reinforced_stone_brick_mossy"),
				new ResourceLocation("securitycraft:reinforced_stone_brick_cracked"),
				new ResourceLocation("securitycraft:reinforced_stone_brick_chiseled"));
		ModelBakery.registerItemVariants(findItem(SecurityCraft.MODID, "reinforced_stained_hardened_clay"),
				new ResourceLocation("securitycraft:reinforced_stained_hardened_clay_white"),
				new ResourceLocation("securitycraft:reinforced_stained_hardened_clay_orange"),
				new ResourceLocation("securitycraft:reinforced_stained_hardened_clay_magenta"),
				new ResourceLocation("securitycraft:reinforced_stained_hardened_clay_light_blue"),
				new ResourceLocation("securitycraft:reinforced_stained_hardened_clay_yellow"),
				new ResourceLocation("securitycraft:reinforced_stained_hardened_clay_lime"),
				new ResourceLocation("securitycraft:reinforced_stained_hardened_clay_pink"),
				new ResourceLocation("securitycraft:reinforced_stained_hardened_clay_gray"),
				new ResourceLocation("securitycraft:reinforced_stained_hardened_clay_silver"),
				new ResourceLocation("securitycraft:reinforced_stained_hardened_clay_cyan"),
				new ResourceLocation("securitycraft:reinforced_stained_hardened_clay_purple"),
				new ResourceLocation("securitycraft:reinforced_stained_hardened_clay_blue"),
				new ResourceLocation("securitycraft:reinforced_stained_hardened_clay_brown"),
				new ResourceLocation("securitycraft:reinforced_stained_hardened_clay_green"),
				new ResourceLocation("securitycraft:reinforced_stained_hardened_clay_red"),
				new ResourceLocation("securitycraft:reinforced_stained_hardened_clay_black"));
		ModelBakery.registerItemVariants(findItem(SecurityCraft.MODID, "reinforced_logs"),
				new ResourceLocation("securitycraft:reinforced_logs_oak"),
				new ResourceLocation("securitycraft:reinforced_logs_spruce"),
				new ResourceLocation("securitycraft:reinforced_logs_birch"),
				new ResourceLocation("securitycraft:reinforced_logs_jungle"));
		ModelBakery.registerItemVariants(findItem(SecurityCraft.MODID, "reinforced_logs2"),
				new ResourceLocation("securitycraft:reinforced_logs2_acacia"),
				new ResourceLocation("securitycraft:reinforced_logs2_big_oak"));
		ModelBakery.registerItemVariants(findItem(SecurityCraft.MODID, "reinforced_metals"),
				new ResourceLocation("securitycraft:reinforced_metals_gold"),
				new ResourceLocation("securitycraft:reinforced_metals_iron"),
				new ResourceLocation("securitycraft:reinforced_metals_diamond"),
				new ResourceLocation("securitycraft:reinforced_metals_emerald"));
		ModelBakery.registerItemVariants(findItem(SecurityCraft.MODID, "reinforced_compressed_blocks"),
				new ResourceLocation("securitycraft:reinforced_compressed_blocks_lapis"),
				new ResourceLocation("securitycraft:reinforced_compressed_blocks_coal"));
		ModelBakery.registerItemVariants(findItem(SecurityCraft.MODID, "reinforced_wool"),
				new ResourceLocation("securitycraft:reinforced_wool_white"),
				new ResourceLocation("securitycraft:reinforced_wool_orange"),
				new ResourceLocation("securitycraft:reinforced_wool_magenta"),
				new ResourceLocation("securitycraft:reinforced_wool_light_blue"),
				new ResourceLocation("securitycraft:reinforced_wool_yellow"),
				new ResourceLocation("securitycraft:reinforced_wool_lime"),
				new ResourceLocation("securitycraft:reinforced_wool_pink"),
				new ResourceLocation("securitycraft:reinforced_wool_gray"),
				new ResourceLocation("securitycraft:reinforced_wool_silver"),
				new ResourceLocation("securitycraft:reinforced_wool_cyan"),
				new ResourceLocation("securitycraft:reinforced_wool_purple"),
				new ResourceLocation("securitycraft:reinforced_wool_blue"),
				new ResourceLocation("securitycraft:reinforced_wool_brown"),
				new ResourceLocation("securitycraft:reinforced_wool_green"),
				new ResourceLocation("securitycraft:reinforced_wool_red"),
				new ResourceLocation("securitycraft:reinforced_wool_black"));
		ModelBakery.registerItemVariants(findItem(SecurityCraft.MODID, "reinforced_quartz"),
				new ResourceLocation("securitycraft:reinforced_quartz_default"),
				new ResourceLocation("securitycraft:reinforced_quartz_chiseled"),
				new ResourceLocation("securitycraft:reinforced_quartz_pillar"));
		ModelBakery.registerItemVariants(findItem(SecurityCraft.MODID, "reinforced_prismarine"),
				new ResourceLocation("securitycraft:reinforced_prismarine_default"),
				new ResourceLocation("securitycraft:reinforced_prismarine_bricks"),
				new ResourceLocation("securitycraft:reinforced_prismarine_dark"));
		ModelBakery.registerItemVariants(findItem(SecurityCraft.MODID, "reinforced_red_sandstone"),
				new ResourceLocation("securitycraft:reinforced_red_sandstone_default"),
				new ResourceLocation("securitycraft:reinforced_red_sandstone_chiseled"),
				new ResourceLocation("securitycraft:reinforced_red_sandstone_smooth"));
		ModelBakery.registerItemVariants(findItem(SecurityCraft.MODID, "reinforced_purpur"),
				new ResourceLocation("securitycraft:reinforced_purpur_default"),
				new ResourceLocation("securitycraft:reinforced_purpur_pillar"));
		ModelBakery.registerItemVariants(findItem(SecurityCraft.MODID, "reinforced_concrete"),
				new ResourceLocation("securitycraft:reinforced_concrete_white"),
				new ResourceLocation("securitycraft:reinforced_concrete_orange"),
				new ResourceLocation("securitycraft:reinforced_concrete_magenta"),
				new ResourceLocation("securitycraft:reinforced_concrete_light_blue"),
				new ResourceLocation("securitycraft:reinforced_concrete_yellow"),
				new ResourceLocation("securitycraft:reinforced_concrete_lime"),
				new ResourceLocation("securitycraft:reinforced_concrete_pink"),
				new ResourceLocation("securitycraft:reinforced_concrete_gray"),
				new ResourceLocation("securitycraft:reinforced_concrete_silver"),
				new ResourceLocation("securitycraft:reinforced_concrete_cyan"),
				new ResourceLocation("securitycraft:reinforced_concrete_purple"),
				new ResourceLocation("securitycraft:reinforced_concrete_blue"),
				new ResourceLocation("securitycraft:reinforced_concrete_brown"),
				new ResourceLocation("securitycraft:reinforced_concrete_green"),
				new ResourceLocation("securitycraft:reinforced_concrete_red"),
				new ResourceLocation("securitycraft:reinforced_concrete_black"));
		ModelBakery.registerItemVariants(findItem(SecurityCraft.MODID, "reinforced_stone"),
				new ResourceLocation("securitycraft:reinforced_stone_default"),
				new ResourceLocation("securitycraft:reinforced_stone_granite"),
				new ResourceLocation("securitycraft:reinforced_stone_smooth_granite"),
				new ResourceLocation("securitycraft:reinforced_stone_diorite"),
				new ResourceLocation("securitycraft:reinforced_stone_smooth_diorite"),
				new ResourceLocation("securitycraft:reinforced_stone_andesite"),
				new ResourceLocation("securitycraft:reinforced_stone_smooth_andesite"));

		Item fakeWater = findItem(SecurityCraft.MODID, "bogus_water");
		ModelBakery.registerItemVariants(fakeWater);
		ModelLoader.setCustomMeshDefinition(fakeWater, stack -> new ModelResourceLocation("securitycraft:fake_liquids", "water"));
		ModelLoader.setCustomStateMapper(SCContent.bogusWater, new StateMapperBase()
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				return new ModelResourceLocation("securitycraft:fake_liquids", "water");
			}
		});

		Item fakeWaterFlowing = findItem(SecurityCraft.MODID, "bogus_water_flowing");
		ModelBakery.registerItemVariants(fakeWaterFlowing);
		ModelLoader.setCustomMeshDefinition(fakeWaterFlowing, stack -> new ModelResourceLocation("securitycraft:fake_liquids", "water_flowing"));
		ModelLoader.setCustomStateMapper(SCContent.bogusWaterFlowing, new StateMapperBase()
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				return new ModelResourceLocation("securitycraft:fake_liquids", "water_flowing");
			}
		});

		Item fakeLava = findItem(SecurityCraft.MODID, "bogus_Lava");
		ModelBakery.registerItemVariants(fakeLava);
		ModelLoader.setCustomMeshDefinition(fakeLava, stack -> new ModelResourceLocation("securitycraft:fake_liquids", "lava"));
		ModelLoader.setCustomStateMapper(SCContent.bogusLava, new StateMapperBase()
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				return new ModelResourceLocation("securitycraft:fake_liquids", "lava");
			}
		});

		Item fakeLavaFlowing = findItem(SecurityCraft.MODID, "bogus_lava_flowing");
		ModelBakery.registerItemVariants(fakeLavaFlowing);
		ModelLoader.setCustomMeshDefinition(fakeLavaFlowing, stack -> new ModelResourceLocation("securitycraft:fake_liquids", "lava_flowing"));
		ModelLoader.setCustomStateMapper(SCContent.bogusLavaFlowing, new StateMapperBase()
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				return new ModelResourceLocation("securitycraft:fake_liquids", "lava_flowing");
			}
		});
	}

	private Item findItem(String modid, String resourceName)
	{
		return Item.REGISTRY.getObject(new ResourceLocation(modid + ":" + resourceName));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerResourceLocations() {
		RegistrationHandler.registerResourceLocations();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerRenderThings(){
		KeyBindings.init();

		RenderingRegistry.registerEntityRenderingHandler(EntityBouncingBetty.class, new RenderBouncingBetty(Minecraft.getMinecraft().getRenderManager()));
		RenderingRegistry.registerEntityRenderingHandler(EntityIMSBomb.class, new RenderIMSBomb(Minecraft.getMinecraft().getRenderManager()));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityKeypadChest.class, new TileEntityKeypadChestRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySecurityCamera.class, new TileEntitySecurityCameraRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFrame.class, new TileEntityFrameRenderer());

		TileEntityItemStackRenderer.instance = new ItemKeypadChestRenderer();

		Block[] blocksToTint = {
				SCContent.reinforcedBrick,
				SCContent.reinforcedCobblestone,
				SCContent.reinforcedCompressedBlocks,
				SCContent.reinforcedConcrete,
				SCContent.reinforcedDirt,
				SCContent.reinforcedDoubleStoneSlabs,
				SCContent.reinforcedDoubleStoneSlabs2,
				SCContent.reinforcedDoubleWoodSlabs,
				SCContent.reinforcedEndStoneBricks,
				SCContent.reinforcedHardenedClay,
				SCContent.reinforcedMetals,
				SCContent.reinforcedMossyCobblestone,
				SCContent.reinforcedNetherBrick,
				SCContent.reinforcedNewLogs,
				SCContent.reinforcedOldLogs,
				SCContent.reinforcedPrismarine,
				SCContent.reinforcedPurpur,
				SCContent.reinforcedQuartz,
				SCContent.reinforcedRedNetherBrick,
				SCContent.reinforcedRedSandstone,
				SCContent.reinforcedSandstone,
				SCContent.reinforcedStainedHardenedClay,
				SCContent.reinforcedStairsAcacia,
				SCContent.reinforcedStairsBirch,
				SCContent.reinforcedStairsBrick,
				SCContent.reinforcedStairsCobblestone,
				SCContent.reinforcedStairsDarkoak,
				SCContent.reinforcedStairsJungle,
				SCContent.reinforcedStairsNetherBrick,
				SCContent.reinforcedStairsOak,
				SCContent.reinforcedStairsPurpur,
				SCContent.reinforcedStairsQuartz,
				SCContent.reinforcedStairsRedSandstone,
				SCContent.reinforcedStairsSandstone,
				SCContent.reinforcedStairsSpruce,
				SCContent.reinforcedStairsStone,
				SCContent.reinforcedStairsStoneBrick,
				SCContent.reinforcedStone,
				SCContent.reinforcedStoneBrick,
				SCContent.reinforcedStoneSlabs,
				SCContent.reinforcedStoneSlabs2,
				SCContent.reinforcedWoodPlanks,
				SCContent.reinforcedWoodSlabs,
				SCContent.reinforcedWool
		};
		//registering reinforced blocks color overlay for world
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((state, worldIn, pos, tintIndex) -> 0x999999, blocksToTint);
		//same thing for inventory
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler((IItemColor)(stack, tintIndex) -> 0x999999, blocksToTint);
	}
}
