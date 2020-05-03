package fr.eno.farmutils.utils;

import fr.eno.farmutils.init.InitBlocks;
import fr.eno.farmutils.init.InitItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class Tabs
{
	public static final CreativeTabs BLOCKS = new CreativeTabs("farmutils.blocks_tab")
	{		
		@Override
		public ItemStack createIcon()
		{
			return new ItemStack(InitBlocks.MILKER);
		}
	};
	
	public static final CreativeTabs ITEMS = new CreativeTabs("farmutils.items_tab")
	{		
		@Override
		public ItemStack createIcon()
		{
			return new ItemStack(InitItems.POWERED_HOE);
		}
	};
}
