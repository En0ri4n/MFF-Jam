package fr.eno.farmutils.core;

import fr.eno.farmutils.ai.EntityAIMoveToFeeder;
import net.minecraft.entity.passive.EntityAnimal;

public class Patcher
{
	public static void patchEntitiesAnimal(EntityAnimal animal)
	{
		animal.tasks.addTask(8, new EntityAIMoveToFeeder(animal, 1d));
	}
}
