package fr.eno.farmutils.gui;

import fr.eno.farmutils.References;
import fr.eno.farmutils.container.ContainerCharger;
import fr.eno.farmutils.tileentity.TileCharger;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiCharger extends GuiContainer
{
    @SuppressWarnings("unused")
	private final TileCharger tileCharger;
    
	public GuiCharger(InventoryPlayer inventory, TileCharger tile)
	{
		super(new ContainerCharger(inventory, tile));
		this.tileCharger = tile;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = I18n.format("farmutils.title.charger");
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		this.drawDefaultBackground();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(new ResourceLocation(References.MOD_ID, "textures/gui/container/charger.png"));
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        
        tickTest++;
        
        int max = 1000;
        int width = 19;
        int value = tickTest;
        int k = max - value;
        int fHeight = max - k;
        int x = 7 + this.width / 2 - this.xSize / 2;
        int y = k + 6 + this.height / 2 - this.ySize / 2;
        
        this.drawTexturedModalRect(x, y, 176, 0, width, fHeight);
        
        if(tickTest >= max)
        	tickTest = 0;
	}
	
	private int tickTest = 0;
}
