package fr.eno.farmutils.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileFeeder extends TileEntity
{
	public TileFeeder() {}
	
	public TileFeeder(World world)
	{
		this.setWorld(world);
	}

}
