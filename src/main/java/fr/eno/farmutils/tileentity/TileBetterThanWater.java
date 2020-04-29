package fr.eno.farmutils.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockCrops;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

public class TileBetterThanWater extends TileEntity implements ITickable
{
	private int tick = 0;
	private Random random = new Random();
	
	public TileBetterThanWater(World world)
	{
		this.setWorld(world);
	}

	@Override
	public void update()
	{
		tick++;
		
		if(tick == 2 * 20)
		{
			BlockPos pos = getCropsPos().get(random.nextInt(getCropsPos().size() - 1));
			
			this.getWorld().scheduleUpdate(pos, this.getWorld().getBlockState(pos).getBlock(), 0);			
			
			tick = 0;
		}
	}
	
	private Iterable<MutableBlockPos> getFarmsLandPos()
	{
		return BlockPos.getAllInBoxMutable(pos.add(-5, 0, -5), pos.add(5, 1, 5));
	}
	
	private List<BlockPos> getCropsPos()
	{
		List<BlockPos> posi = new ArrayList<BlockPos>();
		
		for(MutableBlockPos positions : getFarmsLandPos())
		{
			if(this.getWorld().getBlockState(positions.up()).getBlock() instanceof BlockCrops)
			{
				posi.add(positions.up());
			}
		}
		
		return posi;
	}
}