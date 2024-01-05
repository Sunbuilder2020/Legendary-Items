package net.sunbuilder.legendary_items;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.sunbuilder.legendary_items.items.ModCreativeModeTabs;
import net.sunbuilder.legendary_items.items.ModItems;
import net.sunbuilder.legendary_items.items.custom.AthenasShield;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;
import software.bernie.geckolib.loading.json.raw.ModelProperties;

import java.rmi.registry.Registry;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LegendaryItems.MOD_ID)
public class LegendaryItems {
    public static final String MOD_ID = "legendary_items";
    private static final Logger LOGGER = LogUtils.getLogger();

    public LegendaryItems() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        GeckoLib.initialize();

        ModItems.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ItemProperties.register(ModItems.ATHENAS_SHIELD.get(), new ResourceLocation("blocking"), (itemStack, world, entity, seed) ->
                    AthenasShield.isShieldBlocking(itemStack, entity) ? 1.0F : 0.0F);
        }
    }
}
