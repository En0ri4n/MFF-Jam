package fr.eno.farmutils.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileFeeder extends TileEntity
{
	private int maxSize;
	private int stored;
	
	public TileFeeder() {}
	
	public TileFeeder(World world)
	{
		this.setWorld(world);
		this.maxSize = 100;
		this.stored = 0;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		
		compound.setInteger("Stored", this.stored);
		compound.setInteger("MaxStorage", this.maxSize);
		
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		
		this.stored = compound.getInteger("Stored");
		this.maxSize = compound.getInteger("MaxStorage");		
	}
	
	public boolean addObject()
	{
		if(this.stored < this.maxSize)
		{
			this.stored++;
			return true;
		}
		
		return false;
	}
	
	public int getSize()
	{
		return this.stored;
	}
}
