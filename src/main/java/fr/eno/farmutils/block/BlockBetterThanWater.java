package fr.eno.farmutils.block;

import java.util.Arrays;
import java.util.List;

import fr.eno.farmutils.References;
import fr.eno.farmutils.tileentity.TileBetterThanWater;
import fr.eno.farmutils.utils.Tabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBetterThanWater extends Block
{
	private static final AxisAlignedBB BTW_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);

	public BlockBetterThanWater()
	{
		super(new Material(MapColor.BLUE));
		this.setRegistryName(References.MOD_ID, "better_than_water_block");
		this.setTranslationKey(this.getRegistryName().getPath());
		this.setCreativeTab(Tabs.BLOCKS);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		tooltip.addAll(Arrays.asList("With this block, you can have a plantation of 5x5",
				"Combined with sprinkler for optimal performance"));
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
    {
		return new TileBetterThanWater(world);
    }
 
    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BTW_AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
}
