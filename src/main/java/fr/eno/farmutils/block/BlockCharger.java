package fr.eno.farmutils.block;

import fr.eno.farmutils.FarmingUtilities;
import fr.eno.farmutils.References;
import fr.eno.farmutils.tileentity.TileCharger;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCharger extends Block
{
	public BlockCharger()
	{
		super(Material.ROCK);
		this.setRegistryName(References.MOD_ID, "charger");
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (world.isRemote)
        {
            return true;
        }
        else
        {
            TileEntity tileentity = world.getTileEntity(pos);

            if (tileentity instanceof TileCharger)
            {
                player.openGui(FarmingUtilities.INSTANCE, 0, world, pos.getX(), pos.getY(), pos.getZ());
            }

            return true;
        }
    }
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
    {
		return new TileCharger(world);
    }
 
    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }
}
