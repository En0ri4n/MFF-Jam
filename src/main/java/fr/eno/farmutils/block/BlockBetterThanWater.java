package fr.eno.farmutils.block;

import fr.eno.farmutils.References;
import fr.eno.farmutils.tileentity.TileBetterThanWater;
import fr.eno.farmutils.tileentity.TileMilker;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBetterThanWater extends Block
{
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
}
