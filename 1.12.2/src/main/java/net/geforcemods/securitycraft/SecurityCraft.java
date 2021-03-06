package net.geforcemods.securitycraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.xcompwiz.lookingglass.api.hook.WorldViewAPI2;

import net.geforcemods.securitycraft.commands.CommandModule;
import net.geforcemods.securitycraft.commands.CommandSC;
import net.geforcemods.securitycraft.gui.GuiHandler;
import net.geforcemods.securitycraft.imc.lookingglass.IWorldViewHelper;
import net.geforcemods.securitycraft.imc.versionchecker.VersionUpdateChecker;
import net.geforcemods.securitycraft.misc.EnumCustomModules;
import net.geforcemods.securitycraft.misc.SCManualPage;
import net.geforcemods.securitycraft.misc.SCSounds;
import net.geforcemods.securitycraft.network.ClientProxy;
import net.geforcemods.securitycraft.network.ServerProxy;
import net.geforcemods.securitycraft.tabs.CreativeTabSCDecoration;
import net.geforcemods.securitycraft.tabs.CreativeTabSCExplosives;
import net.geforcemods.securitycraft.tabs.CreativeTabSCTechnical;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.registries.GameData;

@Mod(modid = SecurityCraft.MODID, name = "SecurityCraft", version = SecurityCraft.VERSION, guiFactory = "net.geforcemods.securitycraft.gui.SecurityCraftGuiFactory", dependencies = SecurityCraft.DEPENDENCIES, updateJSON = SecurityCraft.UPDATEJSONURL, acceptedMinecraftVersions = "[1.12]")
public class SecurityCraft {
	public static boolean debug;
	public static final String MODID = "securitycraft";
	private static final String MOTU = "Finally! Cameras!";
	//TODO ********************************* This is v1.8.4.1 for MC 1.12.2!
	protected static final String VERSION = "v1.8.4.1";
	protected static final String DEPENDENCIES = "required-after:forge@[14.21.1.2387,)";
	protected static final String UPDATEJSONURL = "https://www.github.com/Geforce132/SecurityCraft/raw/master/Updates/Forge.json";
	@SidedProxy(clientSide = "net.geforcemods.securitycraft.network.ClientProxy", serverSide = "net.geforcemods.securitycraft.network.ServerProxy")
	public static ServerProxy serverProxy;
	@Instance("securitycraft")
	public static SecurityCraft instance = new SecurityCraft();
	public static ConfigHandler config = new ConfigHandler();
	public static SimpleNetworkWrapper network;
	public static SCEventHandler eventHandler = new SCEventHandler();
	private GuiHandler guiHandler = new GuiHandler();
	public WorldViewAPI2 lookingGlass;
	public HashMap<String, Object[]> cameraUsePositions = new HashMap<String, Object[]>();
	public ArrayList<SCManualPage> manualPages = new ArrayList<SCManualPage>();
	private NBTTagCompound savedModule;
	public static Configuration configFile;
	public static CreativeTabs tabSCTechnical = new CreativeTabSCTechnical();
	public static CreativeTabs tabSCMine = new CreativeTabSCExplosives();
	public static CreativeTabs tabSCDecoration = new CreativeTabSCDecoration();

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event){
		event.registerServerCommand(new CommandSC());
		event.registerServerCommand(new CommandModule());
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		log("Starting to load....");
		log("Loading config file....");
		log(SecurityCraft.VERSION + " of SecurityCraft is for a post MC-1.6.4 version! Configuration files are useless for setting anything besides options.");
		SecurityCraft.configFile = new Configuration(event.getSuggestedConfigurationFile());
		SecurityCraft.config.setupConfiguration();
		log("Config file loaded.");
		log("Setting up network....");
		SecurityCraft.network = NetworkRegistry.INSTANCE.newSimpleChannel(SecurityCraft.MODID);
		RegistrationHandler.registerPackets(SecurityCraft.network);
		log("Network setup.");

		log("Loading mod content....");
		SetupHandler.setupBlocks();
		SetupHandler.setupMines();
		SetupHandler.setupItems();
		log("Finished loading mod content.");
		log("Regisering mod content... (PT 1/2)");
		RegistrationHandler.registerContent();
		RegistrationHandler.registerTileEntities();
		SecurityCraft.serverProxy.registerResourceLocations();
		serverProxy.registerTextureFiles();

		for(int i = 0; i < SCSounds.values().length; i++)
		{
			SCSounds.values()[i].event.setRegistryName(SCSounds.values()[i].path);
			GameData.register_impl(SCSounds.values()[i].event);
		}

		ModMetadata modMeta = event.getModMetadata();
		modMeta.authorList = Arrays.asList(new String[] {
				"Geforce, bl4ckscor3"
		});
		modMeta.autogenerated = false;
		modMeta.credits = "Thanks to all of you guys for your support!";
		modMeta.description = "Adds a load of things to keep your house safe with.\nIf you like this mod, hit the green arrow\nin the corner of the forum thread!\nPlease visit the URL above for help. \n \nMessage of the update: \n" + MOTU;
		modMeta.url = "http://geforcemods.net";
		modMeta.logoFile = "/scLogo.png";
	}

	@EventHandler
	public void init(FMLInitializationEvent event){
		log("Setting up inter-mod stuff...");
		FMLInterModComms.sendMessage("waila", "register", "net.geforcemods.securitycraft.imc.waila.WailaDataProvider.callbackRegister");

		if(config.checkForUpdates) {
			NBTTagCompound vcUpdateTag = VersionUpdateChecker.getNBTTagCompound();
			if(vcUpdateTag != null)
				FMLInterModComms.sendRuntimeMessage(MODID, "VersionChecker", "addUpdate", vcUpdateTag);
		}

		log("Registering mod content... (PT 2/2)");
		NetworkRegistry.INSTANCE.registerGuiHandler(this, guiHandler);
		RegistrationHandler.registerEntities();
		EnumCustomModules.refresh();
		serverProxy.registerRenderThings();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		MinecraftForge.EVENT_BUS.register(SecurityCraft.eventHandler);
		log("Mod finished loading correctly! :D");
	}

	public Object[] getUsePosition(String playerName) {
		return cameraUsePositions.get(playerName);
	}

	public void setUsePosition(String playerName, double x, double y, double z, float yaw, float pitch) {
		cameraUsePositions.put(playerName, new Object[]{x, y, z, yaw, pitch});
	}

	public boolean hasUsePosition(String playerName) {
		return cameraUsePositions.containsKey(playerName);
	}

	public void removeUsePosition(String playerName){
		cameraUsePositions.remove(playerName);
	}

	public NBTTagCompound getSavedModule() {
		return savedModule;
	}

	public void setSavedModule(NBTTagCompound savedModule) {
		this.savedModule = savedModule;
	}

	/**
	 * Get the IWorldView object for the specified key.
	 */
	public IWorldViewHelper getViewFromCoords(String coords){
		return ((ClientProxy) SecurityCraft.serverProxy).worldViews.get(coords);
	}

	/**
	 * Do we have an IWorldView object for the given key already saved?
	 */
	public boolean hasViewForCoords(String coords){
		return ((ClientProxy) SecurityCraft.serverProxy).worldViews.containsKey(coords);
	}

	/**
	 * Remove the IWorldView object for the specified key.
	 */
	public void removeViewForCoords(String coords){
		((ClientProxy) SecurityCraft.serverProxy).worldViews.remove(coords);
	}

	/**
	 * Prints a String to the console. Only will print if SecurityCraft is in debug mode.
	 */
	public static void log(String par1) {
		log(par1, false);
	}

	public static void log(String par1, boolean isSevereError) {
		if(SecurityCraft.debug)
			System.out.println(isSevereError ? "{SecurityCraft} {" + FMLCommonHandler.instance().getEffectiveSide() + "} {Severe}: " + par1 : "[SecurityCraft] [" + FMLCommonHandler.instance().getEffectiveSide() + "] " + par1);
	}

	public static String getVersion() {
		return VERSION;
	}
}
