package fr.eno.farmutils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class Tabs
{
	public static final CreativeTabs BLOCKS = new CreativeTabs("farmutils.blocks_tab")
	{		
		@Override
		public ItemStack createIcon()
		{
			return new ItemStack(Blocks.COBBLESTONE);
		}
	};
	
	public static final CreativeTabs ITEMS = new CreativeTabs("farmutils.items_tab")
	{		
		@Override
		public ItemStack createIcon()
		{
			return new ItemStack(Items.WOODEN_HOE);
		}
	};
}
