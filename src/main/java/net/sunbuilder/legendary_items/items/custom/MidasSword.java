package net.sunbuilder.legendary_items.items.custom;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class MidasSword extends SwordItem {
    public static final String GOLD_COUNT_KEY = "gold count";
    public static final int MAX_GOLD_COUNT = 16000;
    private static final int GOLD_PER_BONUS_DAMAGE = 2000;
    private static final UUID MIDAS_DAMAGE_BONUS_UUID = UUID.randomUUID();

    public MidasSword(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create();

        // Apply the Midas Sword bonus only when in the main hand
        if (slot == EquipmentSlot.MAINHAND) {
            CompoundTag nbt = stack.getTag();
            if (nbt != null && nbt.contains(GOLD_COUNT_KEY)) {
                int goldCount = nbt.getInt(GOLD_COUNT_KEY);
                int damageBonus = getDamageBonus(goldCount);

                // Retrieve the current Midas damage bonus, if it exists
                AttributeModifier currentModifier = modifiers.get(Attributes.ATTACK_DAMAGE).stream()
                        .filter(modifier -> modifier.getId().equals(MIDAS_DAMAGE_BONUS_UUID))
                        .findFirst()
                        .orElse(null);

                // Check if the current damage bonus is different from the new one
                if (currentModifier == null || currentModifier.getAmount() != damageBonus) {
                    if (currentModifier != null) {
                        // Remove the old bonus modifier
                        modifiers.remove(Attributes.ATTACK_DAMAGE, MIDAS_DAMAGE_BONUS_UUID);
                    }

                    // Add the new bonus modifier
                    modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(MIDAS_DAMAGE_BONUS_UUID, "Midas damage bonus", damageBonus, AttributeModifier.Operation.ADDITION));
                }
            } else {
                // If there is no gold count in the NBT, ensure no Midas damage bonus is applied
                modifiers.remove(Attributes.ATTACK_DAMAGE, MIDAS_DAMAGE_BONUS_UUID);
            }
        }
        modifiers.putAll(super.getAttributeModifiers(slot, stack));

        return modifiers;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(Component.literal("A weapon that gets stronger the more gold gets applied to it in an anvil.").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC));
        tooltip.add(Component.literal("Drops some gold upon killing an enemy. ").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC));
        CompoundTag nbt = stack.getTag();
        if (nbt != null && nbt.contains(GOLD_COUNT_KEY)) {
            int goldCount = nbt.getInt(GOLD_COUNT_KEY);
            int damageBonus = getDamageBonus(goldCount);
            tooltip.add(Component.literal("Gold: " + goldCount).withStyle(ChatFormatting.GOLD));
            tooltip.add(Component.literal("Damage Bonus: " + damageBonus).withStyle(ChatFormatting.GOLD));
        }
    }

    public static void addGold(ItemStack stack, int gold) {
        CompoundTag nbt = stack.getOrCreateTag();
        int currentGold = nbt.getInt(GOLD_COUNT_KEY);
        int newGold = Math.min(currentGold + gold, MAX_GOLD_COUNT);
        nbt.putInt(GOLD_COUNT_KEY, newGold);
        stack.setTag(nbt);
    }

    public static int getDamageBonus(int gold) {
        return gold / GOLD_PER_BONUS_DAMAGE;
    }
}
