package fr.eno.farmutils;

import fr.eno.farmutils.block.BlockFeeder;
import fr.eno.farmutils.init.InitBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIMoveToFeeder extends EntityAIMoveToBlock
{
	public EntityAIMoveToFeeder(EntityCreature creature, double speedIn, int length)
	{
		super(creature, speedIn, length);
	}

	@Override
	protected boolean shouldMoveTo(World worldIn, BlockPos pos)
	{
		Block block = worldIn.getBlockState(pos).getBlock();

        if (block == InitBlocks.FEEDER)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos);
            block = iblockstate.getBlock();

            if (block instanceof BlockFeeder)
            {
                return true;
            }

            if (iblockstate.getMaterial() == Material.AIR)
            {
                return true;
            }
        }

        return false;
	}

}
