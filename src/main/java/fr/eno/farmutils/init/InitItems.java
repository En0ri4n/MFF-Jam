package fr.eno.farmutils.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.eno.farmutils.References;
import fr.eno.farmutils.items.ItemBlockBasic;
import fr.eno.farmutils.items.ItemPoweredHoe;
import fr.eno.farmutils.items.ItemWateringCan;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = References.MOD_ID)
public class InitItems
{
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	public static final ItemBlock MILKER = new ItemBlockBasic(InitBlocks.MILKER);
	public static final ItemBlock BREEDER = new ItemBlockBasic(InitBlocks.BREEDER);
	public static final ItemBlock BETTER_THAN_WATER_BLOCK = new ItemBlockBasic(InitBlocks.BETTER_THAN_WATER_BLOCK);
	public static final ItemBlock CHARGER = new ItemBlockBasic(InitBlocks.CHARGER);
	public static final ItemBlock FEEDER = new ItemBlockBasic(InitBlocks.FEEDER);
	public static final ItemBlock SPRINKLER = new ItemBlockBasic(InitBlocks.SPRINKLER);
	public static final Item WATERING_CAN = new ItemWateringCan();
	public static final Item POWERED_HOE = new ItemPoweredHoe();
	
	private static void preInit()
	{
		ITEMS.addAll(Arrays.asList(MILKER,
				BREEDER,
				BETTER_THAN_WATER_BLOCK,
				CHARGER,
				FEEDER,
				SPRINKLER,
				WATERING_CAN,
				POWERED_HOE));
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Item> event)
	{
		IForgeRegistry<Item> reg = event.getRegistry();
		
		preInit();
		
		for(Item item : ITEMS)
		{			
			reg.register(item);
		}
	}
}
