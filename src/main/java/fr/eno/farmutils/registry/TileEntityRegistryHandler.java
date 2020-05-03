package fr.eno.farmutils.registry;

import fr.eno.farmutils.References;
import fr.eno.farmutils.tileentity.TileBetterThanWater;
import fr.eno.farmutils.tileentity.TileBreeder;
import fr.eno.farmutils.tileentity.TileCharger;
import fr.eno.farmutils.tileentity.TileFeeder;
import fr.eno.farmutils.tileentity.TileMilker;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityRegistryHandler
{
	public static void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileMilker.class, getLoc("milker"));
		GameRegistry.registerTileEntity(TileBreeder.class, getLoc("breeder"));
		GameRegistry.registerTileEntity(TileBetterThanWater.class, getLoc("better_than_water"));
		GameRegistry.registerTileEntity(TileCharger.class, getLoc("charger"));
		GameRegistry.registerTileEntity(TileFeeder.class, getLoc("feeder"));
	}
	
	private static ResourceLocation getLoc(String path)
	{
		return new ResourceLocation(References.MOD_ID, path);
	}

}
