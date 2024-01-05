package net.sunbuilder.legendary_items.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sunbuilder.legendary_items.LegendaryItems;
import net.sunbuilder.legendary_items.items.custom.MidasSword;

@Mod.EventBusSubscriber(modid = LegendaryItems.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        if ((left.getItem() instanceof MidasSword) && isGold(right)) {
            if(getGoldCount(left) < MidasSword.MAX_GOLD_COUNT) {
                int gold = getPowerOfGold(right, right.getCount());

                ItemStack output = left.copy();
                MidasSword.addGold(output, gold);

                event.setOutput(output);
                event.setCost(gold / 128 + 1);
                event.setMaterialCost(right.getCount());
                String newName = event.getName();

                if (!newName.isEmpty()) {
                    output.setHoverName(Component.literal(newName));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        Entity entity = event.getEntity();
        Entity sourceEntity = event.getSource().getEntity();
        if(!(entity == null) && sourceEntity instanceof LivingEntity && !(sourceEntity == null) && sourceEntity instanceof LivingEntity) {
            ItemStack heldItem = ((LivingEntity) sourceEntity).getMainHandItem();
            if(heldItem.getItem() instanceof MidasSword) {
                float random = event.getEntity().getRandom().nextFloat();

                if(random < 0.8) {
                    dropItem((LivingEntity) entity, new ItemStack(Items.GOLD_NUGGET, ((LivingEntity) sourceEntity).getRandom().nextInt(1, 5)));
                }
                if(random < 0.2) {
                    dropItem((LivingEntity) entity, new ItemStack(Items.GOLD_INGOT, ((LivingEntity) sourceEntity).getRandom().nextInt(1, 3)));
                }
                if(random < 0.02) {
                    dropItem((LivingEntity) entity, new ItemStack(Items.GOLD_BLOCK, 1));
                }
            }
        }
    }

    private static void dropItem(LivingEntity entity, ItemStack stack) {
        Level level = entity.level();
        if (!level.isClientSide) {
            ItemEntity goldDrop = new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), stack);
            level.addFreshEntity(goldDrop);
        }
    }

    private static boolean isGold(ItemStack stack) {
        return stack.is(Items.GOLD_NUGGET) || stack.is(Items.GOLD_INGOT) || stack.is(Items.GOLD_BLOCK);
    }

    private static int getPowerOfGold(ItemStack stack, int count) {
        if(stack.is(Items.GOLD_NUGGET)) {
            return count;
        } else if(stack.is(Items.GOLD_INGOT)) {
            return 9 * count;
        } else if(stack.is(Items.GOLD_BLOCK)) {
            return 81 * count;
        } else {
            return 0;
        }
    }

    private static int getGoldCount(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (nbt != null && nbt.contains(MidasSword.GOLD_COUNT_KEY)) {
            return nbt.getInt(MidasSword.GOLD_COUNT_KEY);
        } else {
            return 0;
        }
    }
}
