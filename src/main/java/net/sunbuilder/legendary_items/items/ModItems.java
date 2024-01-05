package net.sunbuilder.legendary_items.items;

import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sunbuilder.legendary_items.LegendaryItems;
import net.sunbuilder.legendary_items.items.custom.AthenasShield;
import net.sunbuilder.legendary_items.items.custom.MidasSword;


public class ModItems {
    public static final DeferredRegister<Item> Items =
            DeferredRegister.create(ForgeRegistries.ITEMS, LegendaryItems.MOD_ID);

    public static final RegistryObject<Item> MIDAS_SWORD = Items.register("midas_sword",
            () -> new MidasSword(ModToolTiers.HARDENED_GOLD, 0, -2.4f, new Item.Properties().setNoRepair().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> ATHENAS_SHIELD = Items.register("athenas_shield",
            () -> new AthenasShield(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        Items.register(eventBus);
    }
}
