package net.sunbuilder.legendary_items.items;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;
import net.sunbuilder.legendary_items.LegendaryItems;
import net.sunbuilder.legendary_items.util.ModTags;

import java.util.List;

public class ModToolTiers {
    public static final Tier HARDENED_GOLD = TierSortingRegistry.registerTier(
            new ForgeTier(4, 1, 14f, 3f, 34,
                    ModTags.Blocks.NEEDS_HARDENED_GOLD_TOOL, () -> Ingredient.of(Items.GOLD_BLOCK)),
            new ResourceLocation(LegendaryItems.MOD_ID, "hardened_gold"), List.of(Tiers.DIAMOND), List.of());

}