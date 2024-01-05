package net.sunbuilder.legendary_items.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.sunbuilder.legendary_items.LegendaryItems;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_HARDENED_GOLD_TOOL = tag("needs_hardened_gold_tool");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(LegendaryItems.MOD_ID, name));
        }
    }

    public static class Items {
        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(LegendaryItems.MOD_ID, name));
        }
    }
}