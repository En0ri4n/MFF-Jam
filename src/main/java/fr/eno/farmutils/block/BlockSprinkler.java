package fr.eno.farmutils.block;

import java.util.Arrays;
import java.util.List;

import fr.eno.farmutils.References;
import fr.eno.farmutils.Tabs;
import fr.eno.farmutils.tileentity.TileSprinkler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSprinkler extends Block
{
	public static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 15);
	
	public BlockSprinkler()
	{
		super(Material.ROCK);
		this.setRegistryName(References.MOD_ID, "sprinkler");
		this.setTranslationKey(this.getRegistryName().getPath());
		this.setCreativeTab(Tabs.BLOCKS);
		this.setDefaultState(this.getBlockState().getBaseState().withProperty(ROTATION, Integer.valueOf(0)));
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		tooltip.addAll(Arrays.asList("Place this block on a better than water block for increase speed of crops in 5x5 area"));
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileSprinkler(world);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(ROTATION).intValue();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(ROTATION, meta);
	}
	
	@Override
	public BlockStateContainer getBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {ROTATION});
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
}
