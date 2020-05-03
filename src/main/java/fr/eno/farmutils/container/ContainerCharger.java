package fr.eno.farmutils.container;

import fr.eno.farmutils.container.slot.SlotOnly;
import fr.eno.farmutils.container.slot.SlotOutput;
import fr.eno.farmutils.tileentity.TileCharger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCharger extends Container
{
	private final IInventory chargerInventory;

    public ContainerCharger(IInventory playerInventory, TileCharger chargerInventory)
    {
        this.chargerInventory = chargerInventory;

        this.addSlotToContainer(new SlotOnly(chargerInventory, 0, 46 + 8, 27 + 8));
        this.addSlotToContainer(new SlotOutput(chargerInventory, 1, 111 + 8, 27 + 8));

        for (int k = 0; k < 3; ++k)
        {
            for (int i1 = 0; i1 < 9; ++i1)
            {
                this.addSlotToContainer(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for (int l = 0; l < 9; ++l)
        {
            this.addSlotToContainer(new Slot(playerInventory, l, 8 + l * 18, 142));
        }
    }

    /**
     * Determines whether supplied player can use this container
     */
    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return this.chargerInventory.isUsableByPlayer(playerIn);
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        return index == 0 && this.inventorySlots.get(index) != null ? this.inventorySlots.get(index).getStack() : ItemStack.EMPTY;
    }
}