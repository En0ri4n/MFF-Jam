package fr.eno.farmutils.handler;

import java.util.Random;

import fr.eno.farmutils.References;
import net.minecraft.block.BlockCrops;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = References.MOD_ID)
public class FarmingHandler
{
	@SubscribeEvent
	public static void onSeedClicked(BlockEvent.BreakEvent event)
	{
		if(event.getState().getBlock() instanceof BlockCrops && !event.getWorld().isRemote && event.getPlayer().getHeldItemMainhand().getItem() instanceof ItemHoe)
		{
			event.setCanceled(true);
			
			boolean isGrowed = event.getState().getValue(BlockCrops.AGE).intValue() >= 7;
			
			if(isGrowed)
			{
				World world = event.getWorld();
				BlockPos pos = event.getPos();
				
				BlockCrops crops = (BlockCrops) event.getState().getBlock();
				
				NonNullList<ItemStack> drops = NonNullList.<ItemStack>create();
				crops.getDrops(drops, world, pos, world.getBlockState(pos), new Random().nextInt(3));
				world.setBlockState(pos, crops.getDefaultState());
				
				drops.forEach(itemstack ->
				{
					EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), itemstack);
					item.setDefaultPickupDelay();
					item.motionY = 0.2;
					event.getWorld().spawnEntity(item);
				});
				
				event.getPlayer().getHeldItemMainhand().damageItem(1, event.getPlayer());
			}
		}
	}
}
