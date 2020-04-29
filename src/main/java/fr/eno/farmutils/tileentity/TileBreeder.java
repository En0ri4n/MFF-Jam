package fr.eno.farmutils.tileentity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import fr.eno.farmutils.utils.FacingHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.WeightedRandom.Item;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileBreeder extends TileEntityLockable implements ITickable, ISidedInventory
{
	private int tick = 0;
	
	private int[] SLOTS_INPUT = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
	
    private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(9, ItemStack.EMPTY);
	private String customName;
    private boolean doBreed;
    
	public TileBreeder(World world)
	{
		this.setWorld(world);
	}
	
	public static void registerFixes(DataFixer fixer)
    {
        fixer.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(TileBreeder.class, new String[] {"Items"}));
    }

	@Override
	public void update()
	{
		tick++;
		
		if(tick == 30 * 20 && doBreed)
		{
			breedAnimal();
		}
		
		if(tick >= 30 * 20)
			tick = 0;		
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
    {
        return false;
    }
	
	public void breedAnimal()
	{
		if(isAnimalPresentInRange())
		{
			EntityAnimal animal = getRandomAnimal();
			
			if(animal.isInLove())
				return;
			
			boolean hasBreedItem = false;
			int slotBreed = 0;
			ItemStack item = ItemStack.EMPTY;
			
			for(int slot = 0; slot < 9; slot++)
			{
				ItemStack stack = this.getStackInSlot(slot);
				
				if(!stack.isEmpty() && this.getBreedingItems(animal).contains(stack.getItem()))
				{
					hasBreedItem = true;
					item = stack;
					slotBreed = slot;
					break;
				}
			}
			
			if(hasBreedItem && !item.isEmpty())
			{				
				if(this.getStackInSlot(slotBreed).getCount() > 1)
					this.setInventorySlotContents(slotBreed, new ItemStack(this.getStackInSlot(slotBreed).getItem(), this.getStackInSlot(slotBreed).getCount() - 1));
				else
					this.setInventorySlotContents(slotBreed, ItemStack.EMPTY);
				
				animal.setInLove((EntityPlayer) null);
			}
		}
	}
	
	private EntityAnimal getRandomAnimal()
	{
		List<EntityAnimal> list = this.getAnimalInRange();
		return list.get(new Random().nextInt(list.size() - 1));
	}
	
	private List<net.minecraft.item.Item> getBreedingItems(EntityAnimal entity)
	{
		final List<net.minecraft.item.Item> items = new ArrayList<net.minecraft.item.Item>();
		items.clear();
		
		if(entity instanceof EntityPig)
		{
			items.add(Items.POTATO);
			items.add(Items.CARROT);
		}
		else if(entity instanceof EntitySheep)
		{
			items.add(Items.WHEAT);
		}
		else if(entity instanceof EntityChicken)
		{
			items.addAll(Arrays.asList(Items.BEETROOT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.WHEAT_SEEDS));
		}
		
		return items;
	}
	
	private List<EntityAnimal> getAnimalInRange()
	{		
		BlockPos start = this.getPos().up().east(2).north(2);
		BlockPos end = this.getPos().up().west(2).south(2).up(2);
		
		return this.getWorld().getEntitiesWithinAABB(EntityAnimal.class, new AxisAlignedBB(start, end));
	}
	
	private boolean isAnimalPresentInRange()
	{
		List<EntityAnimal> list = new ArrayList<EntityAnimal>();
		list.addAll(getAnimalInRange());
		
		if(!list.isEmpty())
			return true;
		
		return false;
	}
	
	public void activeBreed()
	{
		this.doBreed = true;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.stacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.stacks);
		
		this.doBreed = compound.getBoolean("doBreed");
		
		if(compound.hasKey("CustomName", 8))
		{
			this.customName = compound.getString("CustomName");
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		
		ItemStackHelper.saveAllItems(compound, stacks);
		
		compound.setBoolean("doBreed", doBreed);
		
		if(this.hasCustomName())
		{
			compound.setString("CustomName", this.customName);
		}
		return compound;
	}

	public void setCustomName(String name)
	{
		this.customName = name;
	}

	@Override
	public boolean hasCustomName()
	{
		return this.customName != null && !this.customName.isEmpty();
	}

	@Override
	public String getName()
	{
		return hasCustomName() ? this.customName : "tile.breeder.name";
	}

	@Override
	public int getSizeInventory()
	{
		return stacks.size();
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack stack : this.stacks)
		{
			if (!stack.isEmpty())
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.stacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return ItemStackHelper.getAndSplit(this.stacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(stacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.stacks.set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit())
		{
			stack.setCount(this.getInventoryStackLimit());
		}
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player)	{}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return true;
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{
	}

	@Override
	public int getFieldCount()
	{
		return 1;
	}

	@Override
	public void clear()
	{
		for (int i = 0; i < this.stacks.size(); i++)
		{
			this.stacks.set(i, ItemStack.EMPTY);
		}
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return null;
	}

	@Override
	public String getGuiID()
	{
		return null;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return SLOTS_INPUT;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		if(index >= 0 && index <= 8 && FacingHelper.isHorizontalFace(direction))
		{
			return true;
		}
		
		return false; 
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{		
		return false;
	}
}