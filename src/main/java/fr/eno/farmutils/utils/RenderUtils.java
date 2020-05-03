package fr.eno.farmutils.utils;

import fr.eno.farmutils.References;
import net.minecraft.util.ResourceLocation;

public class RenderUtils
{	
	public static ResourceLocation getLoc(String path)
	{
		return new ResourceLocation(References.MOD_ID, path);
	}
}
