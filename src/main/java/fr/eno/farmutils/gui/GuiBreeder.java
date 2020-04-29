package fr.eno.farmutils.gui;

import java.awt.Color;

import fr.eno.farmutils.References;
import fr.eno.farmutils.container.ContainerMilker;
import fr.eno.farmutils.tileentity.TileBreeder;
import fr.eno.farmutils.tileentity.TileMilker;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiBreeder extends GuiContainer
{
	public GuiBreeder(InventoryPlayer inventory, TileBreeder tile)
	{
		super(new ContainerMilker(inventory, tile));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		this.drawDefaultBackground();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(new ResourceLocation(References.MOD_ID, "textures/gui/container/breeder.png"));
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        String title = I18n.format("block.breeder.name");
        this.drawCenteredString(this.fontRenderer, title, this.xSize / 2 - this.fontRenderer.getStringWidth(title) / 2, 6, 4210752);
	}
}
