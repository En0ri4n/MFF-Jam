package fr.eno.farmutils.gui;

import java.awt.Color;

import fr.eno.farmutils.items.ItemEnergyStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiOverlayPoweredItem
{
	@SubscribeEvent
	public void onOverlay(RenderGameOverlayEvent event)
	{
		Item held = Minecraft.getMinecraft().player.getHeldItemMainhand().getItem();
		
		if(held instanceof ItemEnergyStorage && event.getType().equals(ElementType.CHAT))
		{
			ItemEnergyStorage item = (ItemEnergyStorage) held;
			this.drawEnergyBar(item.getEnergyStored(), item.getMaxEnergyStored());
		}
	}

	private void drawEnergyBar(int energyStored, int maxEnergyStored)
	{
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		
		int width = 15;
		int height = 50;
		
		int x = res.getScaledWidth() - width - 3;
		int y = res.getScaledHeight() - height - 3;
		Gui.drawRect(x, y, x + width, y + height, Color.BLACK.getRGB());
		Gui.drawRect(x + 1, y + 1, x + width - 1, y + height - 1, Color.RED.getRGB());
				
		double max = maxEnergyStored;
		double energy = energyStored;
		int fHeight = height - MathHelper.floor((energy / max) * height);
		
		if(energyStored < 200)
			Gui.drawRect(x + 1, y + 1, x + width - 1, y + fHeight - 1, Color.LIGHT_GRAY.getRGB());
	}
}
