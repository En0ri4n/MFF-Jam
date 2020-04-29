package fr.eno.farmutils.init;

import fr.eno.farmutils.References;
import fr.eno.farmutils.Tabs;
import fr.eno.farmutils.items.ItemBlockBasic;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = References.MOD_ID)
public class InitItems
{
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Item> event)
	{
		IForgeRegistry<Item> reg = event.getRegistry();
		
		reg.register(getItem(InitBlocks.MILKER));
		reg.register(getItem(InitBlocks.BREEDER));
		reg.register(getItem(InitBlocks.BETTER_THAN_WATER_BLOCK));
	}
	
	private static ItemBlock getItem(Block block)
	{
		ItemBlock item = new ItemBlockBasic(block);
		item.setCreativeTab(Tabs.BLOCKS);
		return item;
	}
	
	private static Item getItem(Item item)
	{
		item.setCreativeTab(Tabs.ITEMS);
		
		return item;
	}
}
