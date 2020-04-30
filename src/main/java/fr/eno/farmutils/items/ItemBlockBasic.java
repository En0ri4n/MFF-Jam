package fr.eno.farmutils.items;

import fr.eno.farmutils.References;
import fr.eno.farmutils.Tabs;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockBasic extends ItemBlock
{
	public ItemBlockBasic(Block block)
	{
		super(block);
		this.setRegistryName(References.MOD_ID, block.getRegistryName().getPath());
		this.setCreativeTab(Tabs.BLOCKS);
	}
}
