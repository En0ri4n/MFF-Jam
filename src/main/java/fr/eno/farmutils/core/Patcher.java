package fr.eno.farmutils.core;

import fr.eno.farmutils.EntityAIMoveToFeeder;
import net.minecraft.entity.passive.EntityCow;

public class Patcher
{
	public static void patchEntityCow(EntityCow cow)
	{
		cow.tasks.addTask(8, new EntityAIMoveToFeeder(cow, 1d));
	}
}
