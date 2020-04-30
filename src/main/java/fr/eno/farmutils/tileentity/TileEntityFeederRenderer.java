package fr.eno.farmutils.tileentity;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class TileEntityFeederRenderer extends TileEntitySpecialRenderer<TileFeeder>
{
	@Override
	public void render(TileFeeder te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		this.bindTexture(new ResourceLocation("textures/gui/container/furnace.png"));
	}
}
