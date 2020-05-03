package fr.eno.farmutils;

import fr.eno.farmutils.block.BlockFeeder;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIMoveToFeeder extends EntityAIMoveToBlock
{
	public EntityAIMoveToFeeder(EntityCreature creature, double speedIn)
	{
		super(creature, speedIn, 5);
	}

	@Override
	protected boolean shouldMoveTo(World worldIn, BlockPos pos)
	{
		Block block = worldIn.getBlockState(pos).getBlock();

        if (block instanceof BlockFeeder)
        {
            return true;
        }

        return false;
	}

}
