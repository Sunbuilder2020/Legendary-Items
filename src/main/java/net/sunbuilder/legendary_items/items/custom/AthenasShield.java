package net.sunbuilder.legendary_items.items.custom;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sunbuilder.legendary_items.LegendaryItems;
import net.sunbuilder.legendary_items.items.ModItems;
import net.sunbuilder.legendary_items.items.client.AthenasShieldRenderer;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.RenderUtils;

import java.util.function.Consumer;

public class AthenasShield extends ShieldItem implements GeoItem {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private static float athenasShieldKnockbackStrenght = 1f;
    private static int athenasShieldKnockbackChance = 15;
    private static int athenasShieldBlockChance = 25;

    public AthenasShield(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object itemStack) {
        return RenderUtils.getCurrentTick();
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private AthenasShieldRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if(this.renderer == null) {
                    renderer = new AthenasShieldRenderer();
                }

                return this.renderer;
            }
        });
    }

    public static boolean isShieldBlocking(ItemStack item, LivingEntity player) {
        if(player == null || !(player instanceof Player)) {
            return false;
        }

        return player.isUsingItem() && player.getUseItem().equals(item);
    }

    @Mod.EventBusSubscriber(modid = LegendaryItems.MOD_ID)
    public class AthenasShieldEvents {
        @SubscribeEvent
        public static void onShieldBlock(ShieldBlockEvent event) {
            if(event.getEntity().getUseItem().getItem() != null && event.getEntity().getUseItem().getItem() == ModItems.ATHENAS_SHIELD.get()) {
                dealknockback(event);
            }
        }

        @SubscribeEvent
        public static void onLivingDamage(LivingDamageEvent event) {
            LivingEntity defender = event.getEntity();
            ItemStack mainHand = defender.getItemInHand(InteractionHand.MAIN_HAND);
            ItemStack offHand = defender.getItemInHand(InteractionHand.OFF_HAND);
            float random = event.getEntity().getRandom().nextInt(0, 100);

            if(random <= athenasShieldBlockChance && !event.getSource().isIndirect()) {
                if ((mainHand.getItem() == ModItems.ATHENAS_SHIELD.get()) || (offHand.getItem() == ModItems.ATHENAS_SHIELD.get())) {

                    ShieldBlockEvent shieldBlockEvent = new ShieldBlockEvent(defender, event.getSource(), event.getAmount());
                    MinecraftForge.EVENT_BUS.post(shieldBlockEvent);

                    event.setAmount(0);

                    dealknockback(shieldBlockEvent);
                }
            }
        }

        public static void dealknockback(ShieldBlockEvent event) {
            float random = event.getEntity().getRandom().nextInt(0, 100);

            if(random <= athenasShieldKnockbackChance && !event.getDamageSource().isCreativePlayer() && !event.getDamageSource().isIndirect()) {
                LivingEntity defender = event.getEntity();
                LivingEntity attacker = (LivingEntity) event.getDamageSource().getEntity();

                double attackerPosX = attacker.getX();
                double attackerPosZ = attacker.getZ();
                double defenderPosX = defender.getX();
                double defenderPosZ = defender.getZ();

                double deltaX = attackerPosX - defenderPosX;
                double deltaZ = attackerPosZ - defenderPosZ;

                double magnitude = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
                double motionX = -(deltaX / magnitude);
                double motionZ = -(deltaZ / magnitude);

                attacker.knockback(athenasShieldKnockbackStrenght, motionX, motionZ);
            }
        }
    }
}
