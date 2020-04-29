package fr.eno.farmutils.utils;

import net.minecraft.util.EnumFacing;

public class FacingHelper
{
	public static boolean isHorizontalFace(EnumFacing facing)
	{
		if(facing == EnumFacing.EAST || facing == EnumFacing.WEST || facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH)
		{
			return true;
		}
		
		return false;
	}
}
