package fr.eno.farmutils.block;

import java.util.Arrays;
import java.util.List;

import fr.eno.farmutils.References;
import fr.eno.farmutils.tileentity.TileFeeder;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFeeder extends Block
{
	public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 15);
	private final List<Item> effective = Arrays.asList(Items.CARROT, Items.POTATO, Items.WHEAT, Items.BEETROOT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.WHEAT_SEEDS);
	
	public BlockFeeder()
	{
		super(Material.WOOD);
		this.setRegistryName(References.MOD_ID, "feeder");
		this.setDefaultState(this.getBlockState().getBaseState().withProperty(LEVEL, 0));
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		Item held = player.getHeldItem(hand).getItem();
		
		if(effective.contains(held))
		{
			if(state.getValue(LEVEL) < 100)
			{
				player.getHeldItem(hand).shrink(1);
				world.setBlockState(pos, state.withProperty(LEVEL, state.getValue(LEVEL) + 1), 2);
			}
		}
		
		return false;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
    {
		return new TileFeeder(world);
    }
 
    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(LEVEL, Integer.valueOf(meta));
    }
	
	@Override
    public int getMetaFromState(IBlockState state)
    {
        return ((Integer)state.getValue(LEVEL)).intValue();
    }
	
	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {LEVEL});
    }
}
