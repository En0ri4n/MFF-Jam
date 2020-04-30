package fr.eno.farmutils.tileentity;

import java.util.ArrayList;
import java.util.List;

import fr.eno.farmutils.utils.FacingHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileMilker extends TileEntityLockable implements ITickable, ISidedInventory
{
	private int[] SLOTS_INPUT = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
	private int[] SLOTS_OUTPUT = new int[] {9, 10, 11, 12, 13, 14, 15, 16, 17};
	
    private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(18, ItemStack.EMPTY);
	private String customName;
    
	public TileMilker(World world)
	{
		this.setWorld(world);
	}
	
	public static void registerFixes(DataFixer fixer)
    {
        fixer.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(TileMilker.class, new String[] {"Items"}));
    }

	@Override
	public void update()
	{
		
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
    {
        return false;
    }
	
	public void milkCow()
	{
		if(cowPresentInRange())
		{
			boolean hasBucket = false;
			int slotBucket = 0;
			
			for(int slot = 0; slot < 9; slot++)
			{
				ItemStack stack = this.getStackInSlot(slot);
				
				if(!stack.isEmpty() && stack.getItem() == Items.BUCKET)
				{
					hasBucket = true;
					slotBucket = slot;
					break;
				}
			}
			
			if(hasBucket)
			{
				boolean flag = this.addItemInOutput(new ItemStack(Items.MILK_BUCKET));
				
				if(flag)
				{
					if(this.getStackInSlot(slotBucket).getCount() > 1)
						this.setInventorySlotContents(slotBucket, new ItemStack(this.getStackInSlot(slotBucket).getItem(), this.getStackInSlot(slotBucket).getCount() - 1));
					else
						this.setInventorySlotContents(slotBucket, ItemStack.EMPTY);
					
					this.getWorld().playSound((double) getPos().getX(), (double) getPos().getY(), (double) getPos().getZ(), SoundEvents.ENTITY_COW_MILK, SoundCategory.AMBIENT, 100, 1, false);
				}
			}
		}
	}
	
	private boolean addItemInOutput(ItemStack itemstack)
	{		
		for(int slot = 9; slot < 18; slot++)
		{
			ItemStack stack = this.getStackInSlot(slot);
			
			if(stack.isEmpty())
			{
				this.setInventorySlotContents(slot, itemstack);
				return true;
			}
		}
		
		return false;
	}
	
	private List<EntityCow> getCowsInRange()
	{		
		BlockPos start = this.getPos().up().east(2).north(2);
		BlockPos end = this.getPos().up().west(2).south(2).up(2);
		
		return this.getWorld().getEntitiesWithinAABB(EntityCow.class, new AxisAlignedBB(start, end));
	}
	
	private boolean cowPresentInRange()
	{
		List<EntityCow> list = new ArrayList<EntityCow>();
		list.addAll(getCowsInRange());
		
		if(!list.isEmpty())
			return true;
		
		return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.stacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.stacks);

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
		return hasCustomName() ? this.customName : "tile.milker.name";
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
		if (side == EnumFacing.UP || FacingHelper.isHorizontalFace(side))
        {
            return SLOTS_INPUT;
        }
        else
        {
            return SLOTS_OUTPUT;
        }
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		if(index >= 0 && index <= 8 && itemStackIn.getItem() == Items.BUCKET && FacingHelper.isHorizontalFace(direction))
		{
			return true;
		}
		
		return false; 
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		if(index >= 9 && index <= 17 && direction == EnumFacing.DOWN)
		{
			return true;
		}
		
		return false;
	}
}