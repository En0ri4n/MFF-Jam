package fr.eno.farmutils.items;

import java.util.Random;

import fr.eno.farmutils.References;
import fr.eno.farmutils.Tabs;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockStem;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MOD_ID)
public class ItemWateringCan extends Item
{
	public ItemWateringCan()
	{
		this.setRegistryName(References.MOD_ID, "watering_can");
		this.setCreativeTab(Tabs.ITEMS);
		this.setMaxDamage(10);
		this.setHasSubtypes(true);
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if(tab == this.getCreativeTab())
		{
			items.add(new ItemStack(this, 1, 0));
		}
	}
	
	@Override
	public String getTranslationKey(ItemStack stack)
	{
		String name = "";
		switch(stack.getMetadata())
		{
			case 0:
				name = "watering_can";
				break;
			case 1:
				name = "broken_watering_can";
				break;
		}
		
		return "item." + name;
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(world.getBlockState(pos).getBlock() instanceof BlockCrops || world.getBlockState(pos).getBlock() instanceof BlockStem)
		{
			ItemStack stack = player.getHeldItem(hand);
			
			if(world.getBlockState(pos).getMaterial() == Material.WATER || player.capabilities.isCreativeMode)
			{
				growPlant(world, pos);
				
				return EnumActionResult.SUCCESS;
			}
			else
			{
				if(stack.getMaxDamage() - stack.getItemDamage() <= 1)
				{
					player.setHeldItem(hand, new ItemStack(this, 1, 1));
				}
				
				player.getHeldItem(hand).damageItem(1, player);
				growPlant(world, pos);
				
				return EnumActionResult.SUCCESS;
			}
		}
		
		return EnumActionResult.FAIL;
	}
	
	public void growPlant(World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
		
		if(state.getValue(BlockCrops.AGE).intValue() < 7)
		{
			int age = state.getValue(BlockCrops.AGE).intValue() + new Random().nextInt(3) + 1;
			world.setBlockState(pos, state.withProperty(BlockCrops.AGE, age > 7 ? 7 : age));
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
        ItemStack itemstack = player.getHeldItem(hand);
		RayTraceResult raytraceresult = this.rayTrace(world, player, true);

        if (raytraceresult == null)
        {
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
        }
        else
        {
            if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK)
            {
                BlockPos blockpos = raytraceresult.getBlockPos();

                if (world.getBlockState(blockpos).getMaterial() == Material.WATER)
                {
                    world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                    
                    player.setHeldItem(hand, new ItemStack(this, 1, 0));
                    
                    return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
                }
            }

            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
        }
	}
}
