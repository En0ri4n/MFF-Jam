package fr.eno.farmutils.init;

import fr.eno.farmutils.References;
import fr.eno.farmutils.block.BlockBetterThanWater;
import fr.eno.farmutils.block.BlockBreeder;
import fr.eno.farmutils.block.BlockCharger;
import fr.eno.farmutils.block.BlockFeeder;
import fr.eno.farmutils.block.BlockMilker;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = References.MOD_ID)
public class InitBlocks
{
	public static final Block MILKER = new BlockMilker();
	public static final Block BREEDER = new BlockBreeder();
	public static final Block BETTER_THAN_WATER_BLOCK = new BlockBetterThanWater();
	public static final Block CHARGER = new BlockCharger();
	public static final Block FEEDER = new BlockFeeder();
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		IForgeRegistry<Block> reg = event.getRegistry();
		
		reg.register(MILKER);
		reg.register(BREEDER);
		reg.register(BETTER_THAN_WATER_BLOCK);
		reg.register(CHARGER);
		reg.register(FEEDER);
	}
}
