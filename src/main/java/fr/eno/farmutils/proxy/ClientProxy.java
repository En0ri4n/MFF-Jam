package fr.eno.farmutils.proxy;

import fr.eno.farmutils.gui.GuiOverlayPoweredItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
    public ClientProxy()
    {
    	
    }

    @Override
    public void preinit(FMLPreInitializationEvent e)
    {
        super.preinit(e);
    }

    @Override
    public void init(FMLInitializationEvent e)
    {
        super.init(e);
        MinecraftForge.EVENT_BUS.register(new GuiOverlayPoweredItem());
    }

    @Override
    public void postinit(FMLPostInitializationEvent e)
    {
        super.postinit(e);
    }
}
