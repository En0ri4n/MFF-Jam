package fr.eno.farmutils.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockFarmland;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

public class TileBetterThanWater extends TileEntity implements ITickable
{
	private Random random = new Random();
	
	public TileBetterThanWater() {}
	
	public TileBetterThanWater(World world)
	{
		this.setWorld(world);
	}

	@Override
	public void update()
	{
		BlockPos posi = this.getRandomFarmLandPos();
		if(world.getBlockState(posi).getBlock() instanceof BlockFarmland)
		{
			if(this.getWorld().getBlockState(posi).getValue(BlockFarmland.MOISTURE) < 7)
			{
				this.getWorld().setBlockState(posi, Blocks.FARMLAND.getDefaultState().withProperty(BlockFarmland.MOISTURE, Integer.valueOf(7)), 2);
			}
		}
	}
	
	
	
	private BlockPos getRandomFarmLandPos()
	{
		List<BlockPos> list = getFarmsLandPos();
		
		if(!list.isEmpty() && list != null)
		{
			int rand = random.nextInt(list.size());
			return list.get(rand);
		}
		
		return new MutableBlockPos(0, 0, 0);
	}
	
	private List<BlockPos> getFarmsLandPos()
	{
		List<BlockPos> list = new ArrayList<BlockPos>();
		
		BlockPos.getAllInBox(this.getPos().add(-5, 0, -5), this.getPos().add(5, 0, 5)).forEach(blockPos ->
		{
			if(this.getWorld().getBlockState(blockPos).getBlock() instanceof BlockFarmland)
			{
				list.add(blockPos);
			}
		});
		
		return list;
	}
}