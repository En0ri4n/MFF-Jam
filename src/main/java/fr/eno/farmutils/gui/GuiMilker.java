package fr.eno.farmutils.gui;

import fr.eno.farmutils.References;
import fr.eno.farmutils.container.ContainerMilker;
import fr.eno.farmutils.tileentity.TileMilker;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiMilker extends GuiContainer
{
	private TileMilker tileMilker;
	
	public GuiMilker(InventoryPlayer inventory, TileMilker tileMilker)
	{
		super(new ContainerMilker(inventory, tileMilker));
		this.tileMilker = tileMilker;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		int x2 = this.width / 2 + this.xSize / 2 - 23;
		int x = 7 + this.width / 2 - this.xSize / 2;
		int y = 6 + this.height / 2 - this.ySize / 2;
		
		boolean flag = mouseX > x2 && mouseX < x2 + 18 && mouseY > y && mouseY < y + 70;
		boolean flag1 = mouseX > x && mouseX < x + 18 && mouseY > y && mouseY < y + 70;
		
		if (flag1 || flag)
		{
			this.drawHoveringText("Photons : " + this.tileMilker.getField(0) + "/" + this.tileMilker.getField(1), mouseX, mouseY);
		}
		
		this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String s = I18n.format("farmutils.title.milker");
		this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);	
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		this.drawDefaultBackground();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(References.MOD_ID, "textures/gui/container/milker.png"));
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
		
		double max = this.tileMilker.getField(1);
		double energy = this.tileMilker.getField(0);
		int textureHeight = 70;
		int width = 19;
		int fHeight = textureHeight - MathHelper.floor(energy / max * textureHeight);
		int x = 5 + this.width / 2 - this.xSize / 2;
		int y = 9 + this.height / 2 - this.ySize / 2;
		this.drawTexturedModalRect(x, y, 176, 0, width, fHeight);
		x = this.width / 2 + this.xSize / 2 - 23;
		this.drawTexturedModalRect(x, y, 176, 0, width, fHeight);
	}

}
