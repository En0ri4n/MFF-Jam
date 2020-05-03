package fr.eno.farmutils.block;

import java.util.Arrays;
import java.util.List;

import fr.eno.farmutils.FarmingUtilities;
import fr.eno.farmutils.References;
import fr.eno.farmutils.Tabs;
import fr.eno.farmutils.tileentity.TileCharger;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
		this.setTranslationKey(this.getRegistryName().getPath());
		this.setCreativeTab(Tabs.BLOCKS);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		tooltip.addAll(Arrays.asList("With this block, you can recharge your powered tools",
				"PS: only hoe is implemented and the energy is photons from sun"));
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
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
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
