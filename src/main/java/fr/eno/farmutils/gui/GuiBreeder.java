package fr.eno.farmutils.gui;

import fr.eno.farmutils.References;
import fr.eno.farmutils.container.ContainerBreeder;
import fr.eno.farmutils.tileentity.TileBreeder;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiBreeder extends GuiContainer
{
	private TileBreeder tileBreeder;

	public GuiBreeder(InventoryPlayer inventory, TileBreeder tile)
	{
		super(new ContainerBreeder(inventory, tile));
		this.tileBreeder = tile;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		int x = 53 + this.width / 2 - this.xSize / 2;
		int y = 59 + this.height / 2 - this.ySize / 2;
		
		if (mouseX > x && mouseX < x + 70 && mouseY > y && mouseY < y + 19)
		{
			this.drawHoveringText("Photons : " + this.tileBreeder.getField(0) + "/" + this.tileBreeder.getField(1), mouseX, mouseY);
		}
		
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String s = I18n.format("farmutils.title.breeder");
		this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);	
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
		
		double max = this.tileBreeder.getField(1);
		double energy = this.tileBreeder.getField(0);
		int height = 19;
		int width = 70;
		int fWidth = MathHelper.floor(energy / max * width);
		int x = 53 + this.width / 2 - this.xSize / 2;
		int y = 59 + this.height / 2 - this.ySize / 2;
		this.drawTexturedModalRect(x, y, 176, 0, fWidth, height);
	}
}
