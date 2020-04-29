package fr.eno.farmutils.proxy;

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
    }

    @Override
    public void postinit(FMLPostInitializationEvent e)
    {
        super.postinit(e);
    }
}
