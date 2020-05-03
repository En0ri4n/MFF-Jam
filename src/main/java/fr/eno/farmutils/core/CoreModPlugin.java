package fr.eno.farmutils.core;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;

@MCVersion(value = "1.12.2")
public class CoreModPlugin implements IFMLLoadingPlugin
{	
	@Override
	public String[] getASMTransformerClass()
	{
		// In args : -Dfml.coreMods.load=fr.eno.farmutils.core.CoreModPlugin
		return new String[] { ModClassTransformer.class.getName() };
	}

	@Override
	public String getModContainerClass()
	{
		return null;
	}

	@Override
	public String getSetupClass()
	{
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {}

	@Override
	public String getAccessTransformerClass()
	{
		return null;// ModClassTransformer.class.getName();
	}
}