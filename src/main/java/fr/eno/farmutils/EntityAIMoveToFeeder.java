package fr.eno.farmutils;

import fr.eno.farmutils.block.BlockFeeder;
import fr.eno.farmutils.tileentity.TileFeeder;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIMoveToFeeder extends EntityAIMoveToBlock
{
	private EntityAnimal animal;
	
	public EntityAIMoveToFeeder(EntityAnimal animal, double speedIn)
	{
		super(animal, speedIn, 10);
		this.animal = animal;
	}

	@Override
	protected boolean shouldMoveTo(World worldIn, BlockPos pos)
	{
		Block block = worldIn.getBlockState(pos).getBlock();

        if (block instanceof BlockFeeder)
        {
        	boolean flag = !worldIn.getBlockState(pos).getValue(BlockFeeder.EMPTY).booleanValue();
            return flag;
        }

        return false;
	}
	
	@Override
	public void updateTask()
	{
		super.updateTask();
		this.animal.getLookHelper().setLookPosition((double)this.destinationBlock.getX() + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)this.destinationBlock.getZ() + 0.5D, 10.0F, (float)this.animal.getVerticalFaceSpeed());

		if(this.getIsAboveDestination())
		{
			World world = this.animal.world;
			BlockPos dest = this.destinationBlock;
			
			if(world.getTileEntity(dest) instanceof TileFeeder)
			{
				TileFeeder tile = (TileFeeder) world.getTileEntity(dest);
				boolean flag = tile.removeObject(5);
				
				if(!flag)
					return;
				
				if(this.animal.getHealth() < this.animal.getMaxHealth())
				{
					this.animal.heal(this.animal.getMaxHealth() / 2);
					world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, this.animal.posX, this.animal.posY, this.animal.posZ, 0.5d, 0.5d, 0.5d, 1, 10);
				}
				else
				{
					this.animal.setInLove((EntityPlayer) null);
					world.spawnParticle(EnumParticleTypes.HEART, this.animal.posX, this.animal.posY, this.animal.posZ, 0.5d, 0.5d, 0.5d, 1, 10);
				}
				
				this.runDelay = 30 * 20;
				this.resetTask();
			}
		}
	}
	
	@Override
	public void startExecuting()
	{
		super.startExecuting();
		this.maxStayTicks = 30;
	}
	
	@Override
	public boolean shouldContinueExecuting()
    {
        return super.shouldContinueExecuting();
    }
}
