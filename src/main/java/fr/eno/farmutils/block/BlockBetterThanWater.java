package fr.eno.farmutils.block;

import fr.eno.farmutils.References;
import fr.eno.farmutils.tileentity.TileBetterThanWater;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
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
		super(Material.WATER);
		this.setRegistryName(References.MOD_ID, "better_than_water_block");
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
