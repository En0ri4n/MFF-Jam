package fr.eno.farmutils.items;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import fr.eno.farmutils.References;
import fr.eno.farmutils.Tabs;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockStem;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MOD_ID)
public class ItemWateringCan extends Item
{
	public ItemWateringCan()
	{
		this.setRegistryName(References.MOD_ID, "watering_can");
		this.setTranslationKey(this.getRegistryName().getPath());
		this.setCreativeTab(Tabs.ITEMS);
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flagIn)
	{
		if(world != null && stack.hasTagCompound())
			tooltip.add(TextFormatting.AQUA + "Water Level : " + stack.getTagCompound().getInteger("WaterLevel") + "/" + "10" + TextFormatting.RESET);
		tooltip.addAll(Arrays.asList("Right click a crops to grow him, but when you are in water,",
				"you don't need to recharge the watering can"));
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
	{
		stack.setTagCompound(new NBTTagCompound());
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
				NBTTagCompound nbt = stack.getTagCompound();
				if(nbt == null)
				{
					nbt = new NBTTagCompound();
					nbt.setInteger("WaterLevel", 9);
				}
				else if(nbt.getInteger("WaterLevel") > 0)
				{
					nbt.setInteger("WaterLevel", nbt.getInteger("WaterLevel") - 1);
				}
				
				stack.setTagCompound(nbt);
				
				if(stack.getTagCompound().getInteger("WaterLevel") <= 0)
				{
					player.sendMessage(new TextComponentString(TextFormatting.RED + "You didn't have water in your watering can !"));
				}
				
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
                    
                    ItemStack stack = player.getHeldItem(hand);
                    
                    NBTTagCompound nbt = stack.getTagCompound();
                    if(nbt == null) nbt = new NBTTagCompound();
                    nbt.setInteger("WaterLevel", 10);
                    player.setHeldItem(hand, stack);
                    
                    return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
                }
            }

            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
        }
	}
}
