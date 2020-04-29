package fr.eno.farmutils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.eno.farmutils.gui.GuiHandler;
import fr.eno.farmutils.proxy.CommonProxy;
import fr.eno.farmutils.registry.NetworkRegistryHandler;
import fr.eno.farmutils.registry.TileEntityRegistryHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = References.MOD_ID, name = References.MOD_NAME, version = References.VERSION)
public class FarmingUtilities
{
	@Mod.Instance(References.MOD_ID)
	public static FarmingUtilities INSTANCE;
	
	@SidedProxy(clientSide = References.CLIENT_PROXY, serverSide = References.SERVER_PROXY)
	public static CommonProxy proxy;
	
	public static Logger LOGGER = LogManager.getLogger(References.MOD_NAME);
	public static SimpleNetworkWrapper network;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
	    proxy.preinit(event);
	    network = NetworkRegistry.INSTANCE.newSimpleChannel(References.MOD_ID);
	    NetworkRegistryHandler.registerMessages(network);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
	    proxy.init(event);
	    
	    TileEntityRegistryHandler.registerTileEntities();
	    NetworkRegistry.INSTANCE.registerGuiHandler(References.MOD_ID, new GuiHandler());
	}
	
	@Mod.EventHandler
	public void postinit(FMLPostInitializationEvent event)
	{
	    proxy.postinit(event);
	}	
}
