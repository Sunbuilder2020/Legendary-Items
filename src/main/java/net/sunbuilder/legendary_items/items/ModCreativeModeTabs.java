package net.sunbuilder.legendary_items.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.sunbuilder.legendary_items.LegendaryItems;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LegendaryItems.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_MODE_TABS.register("legendary_items",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.MIDAS_SWORD.get()))
                    .title(Component.translatable("creativetab.legendary_items"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.MIDAS_SWORD.get());
                        pOutput.accept(ModItems.ATHENAS_SHIELD.get());
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
