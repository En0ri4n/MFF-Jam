package fr.eno.farmutils.container.slot;

import fr.eno.farmutils.items.ItemEnergyStorage;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotOnly extends Slot
{	
	public SlotOnly(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return stack.getItem() instanceof ItemEnergyStorage;
	}

}
