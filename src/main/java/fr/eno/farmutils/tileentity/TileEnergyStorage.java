package fr.eno.farmutils.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEnergyStorage extends TileEntity implements IEnergyStorage
{
	private int maxStorage;
	private int energyStored;
	private int maxEnergyReceive;
	private int maxEnergyExtract;
	
	public TileEnergyStorage(int maxStorage, int maxEnergyReceive, int maxEnergyExtract)
	{
		this.maxStorage = maxStorage;
		this.maxEnergyReceive = maxEnergyReceive;
		this.maxEnergyExtract = maxEnergyExtract;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		super.readFromNBT(compound);
		compound.setInteger("EnergyStored", this.energyStored);
		compound.setInteger("MaxStorage", this.maxStorage);
		compound.setInteger("MaxReceive", this.maxEnergyReceive);
		compound.setInteger("MaxExtract", this.maxEnergyExtract);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		this.energyStored = compound.getInteger("EnergyStored");
		this.maxStorage = compound.getInteger("MaxStorage");
		this.maxEnergyReceive = compound.getInteger("MaxReceive");
		this.maxEnergyExtract = compound.getInteger("MaxExtract");
	}
	
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate)
	{
		if(canReceive(maxReceive) && !simulate)
		{
			return maxReceive;
		}
		
		return 0;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate)
	{
		if(canExtract(maxExtract) && !simulate)
		{
			return maxExtract;
		}
		
		return 0;
	}

	@Override
	public int getEnergyStored()
	{
		return this.energyStored;
	}

	@Override
	public int getMaxEnergyStored()
	{
		return this.maxStorage;
	}
	
	public boolean canExtract(int size)
	{
		return size <= this.energyStored;
	}

	@Override
	public boolean canExtract()
	{
		return false;
	}
	
	public boolean canReceive(int size)
	{
		return maxStorage <= size + energyStored;
	}

	@Override
	public boolean canReceive()
	{
		return false;
	}

}
