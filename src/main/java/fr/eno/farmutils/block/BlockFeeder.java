package fr.eno.farmutils.block;

import java.util.Arrays;
import java.util.List;

import fr.eno.farmutils.References;
import fr.eno.farmutils.Tabs;
import fr.eno.farmutils.tileentity.TileFeeder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFeeder extends Block
{
	public static final PropertyBool EMPTY = PropertyBool.create("empty");
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool LEFT = PropertyBool.create("left");
	
	private final List<Item> effective = Arrays.asList(Items.CARROT, Items.POTATO, Items.WHEAT, Items.BEETROOT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.WHEAT_SEEDS);
	
	public BlockFeeder()
	{
		super(Material.WOOD);
		this.setRegistryName(References.MOD_ID, "feeder");
		this.setTranslationKey(this.getRegistryName().getPath());
		this.setCreativeTab(Tabs.BLOCKS);
		this.setDefaultState(this.getBlockState().getBaseState().withProperty(EMPTY, true).withProperty(FACING, EnumFacing.NORTH).withProperty(LEFT, true));
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		tooltip.addAll(Arrays.asList("This block can set in love animals.",
				"Fill it with carrots, wheat, potatoes, etc.",
				"PS: I added an AI (not perfect) so you can reply bugs found on github :)"));
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack held = player.getHeldItem(hand);
		
		if(!world.isRemote && hand.equals(EnumHand.MAIN_HAND))
		{
			if(effective.contains(held.getItem()))
			{
				if(world.getTileEntity(pos) instanceof TileFeeder)
				{
					TileFeeder feeder = (TileFeeder) world.getTileEntity(pos);
					
					boolean flag = feeder.addObject();
					
					if(flag)
					{
						if(!player.capabilities.isCreativeMode)
							held.shrink(1);
						
						player.setHeldItem(hand, held);
					}
					
					if(feeder.getSize() > 0 && state.getValue(LEFT).booleanValue())
					{
						world.setBlockState(pos, this.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(LEFT, true).withProperty(EMPTY, false), 2);
						return true;
					}
					else if(feeder.getSize() > 0 && !state.getValue(LEFT).booleanValue())
					{
						world.setBlockState(pos, this.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(LEFT, false).withProperty(EMPTY, false), 2);
						return true;
					}
				}
			}
		}		
		
		return false;
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		if(!world.isRemote)
		{
			if(state.getValue(LEFT))
				world.setBlockToAir(pos.offset(EnumFacing.byHorizontalIndex(state.getValue(FACING).getHorizontalIndex() == 0 ? 3 : state.getValue(FACING).getHorizontalIndex() - 1)));
			else
				world.setBlockToAir(pos.offset(EnumFacing.byHorizontalIndex(state.getValue(FACING).getHorizontalIndex() == 0 ? 3 : state.getValue(FACING).getHorizontalIndex() + 1)));
		}
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		int mirror = placer.getHorizontalFacing().getHorizontalIndex();
		int face = 0;
		
		 switch(mirror)
		 {
			 case 0:
				 face = 2;
				 break;
			 case 1:
				 face = 3;
				 break;
			 case 2:
				 face = 0;
				 break;
			 case 3:
				 face = 1;
				 break;
		 }
		
		 if(!world.isRemote)
		 {
			 world.setBlockState(pos, state.withProperty(FACING, EnumFacing.byHorizontalIndex(face)), 2);
				world.setBlockState(pos.offset(EnumFacing.byHorizontalIndex(face == 0 ? 3 : face - 1)), state.withProperty(LEFT, false).withProperty(FACING, EnumFacing.byHorizontalIndex(face)));
		 }
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{		
		return world.getBlockState(pos.offset(EnumFacing.NORTH)).getMaterial().isReplaceable()
				&& world.getBlockState(pos.offset(EnumFacing.SOUTH)).getMaterial().isReplaceable()
				&& world.getBlockState(pos.offset(EnumFacing.EAST)).getMaterial().isReplaceable()
				&& world.getBlockState(pos.offset(EnumFacing.WEST)).getMaterial().isReplaceable();
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
		switch(meta)
		{
			case 0:
				return this.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(LEFT, true).withProperty(EMPTY, true);
			case 1:
				return this.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(LEFT, false).withProperty(EMPTY, true);
			case 2:
				return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH).withProperty(LEFT, true).withProperty(EMPTY, true);
			case 3:
				return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH).withProperty(LEFT, false).withProperty(EMPTY, true);
			case 4:
				return this.getDefaultState().withProperty(FACING, EnumFacing.EAST).withProperty(LEFT, true).withProperty(EMPTY, true);
			case 5:
				return this.getDefaultState().withProperty(FACING, EnumFacing.EAST).withProperty(LEFT, false).withProperty(EMPTY, true);
			case 6:
				return this.getDefaultState().withProperty(FACING, EnumFacing.WEST).withProperty(LEFT, true).withProperty(EMPTY, true);
			case 7:
				return this.getDefaultState().withProperty(FACING, EnumFacing.WEST).withProperty(LEFT, false).withProperty(EMPTY, true);
			case 8:
				return this.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(LEFT, true).withProperty(EMPTY, false);
			case 9:
				return this.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(LEFT, false).withProperty(EMPTY, false);
			case 10:
				return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH).withProperty(LEFT, true).withProperty(EMPTY, false);
			case 11:
				return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH).withProperty(LEFT, false).withProperty(EMPTY, false);
			case 12:
				return this.getDefaultState().withProperty(FACING, EnumFacing.EAST).withProperty(LEFT, true).withProperty(EMPTY, false);
			case 13:
				return this.getDefaultState().withProperty(FACING, EnumFacing.EAST).withProperty(LEFT, false).withProperty(EMPTY, false);
			case 14:
				return this.getDefaultState().withProperty(FACING, EnumFacing.WEST).withProperty(LEFT, true).withProperty(EMPTY, false);
			case 15:
				return this.getDefaultState().withProperty(FACING, EnumFacing.WEST).withProperty(LEFT, false).withProperty(EMPTY, false);
		}
        return this.getDefaultState();
    }
	
	@SuppressWarnings("incomplete-switch")
	@Override
    public int getMetaFromState(IBlockState state)
    {
		boolean empty = state.getValue(EMPTY).booleanValue();
		boolean left = state.getValue(LEFT).booleanValue();
		
		switch(state.getValue(FACING))
		{
			case NORTH:
				if(empty && left)
					return 0;
				if(!empty && left)
					return 1;
				if(empty && !left)
					return 8;
				if(!empty && !left)
					return 9;
				break;
			case SOUTH:
				if(empty && left)
					return 2;
				if(!empty && left)
					return 3;
				if(empty && !left)
					return 10;
				if(!empty && !left)
					return 11;
				break;
			case EAST:
				if(empty && left)
					return 4;
				if(!empty && left)
					return 5;
				if(empty && !left)
					return 12;
				if(!empty && !left)
					return 13;
				break;
			case WEST:
				if(empty && left)
					return 6;
				if(!empty && left)
					return 7;
				if(empty && !left)
					return 14;
				if(!empty && !left)
					return 15;
				break;
			
		}
        return 0;
    }
	
	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {EMPTY, LEFT, FACING});
    }
}
