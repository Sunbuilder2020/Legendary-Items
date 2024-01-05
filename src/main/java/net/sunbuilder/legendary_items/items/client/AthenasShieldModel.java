package net.sunbuilder.legendary_items.items.client;

import net.minecraft.resources.ResourceLocation;
import net.sunbuilder.legendary_items.LegendaryItems;
import net.sunbuilder.legendary_items.items.custom.AthenasShield;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class AthenasShieldModel extends GeoModel<AthenasShield> {
    @Override
    public ResourceLocation getModelResource(AthenasShield animatable) {
        return new ResourceLocation(LegendaryItems.MOD_ID, "geo/athenas_shield.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AthenasShield animatable) {
        return new ResourceLocation(LegendaryItems.MOD_ID, "textures/item/athenas_shield_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AthenasShield animatable) {
        return new ResourceLocation(LegendaryItems.MOD_ID, "animations/athenas_shield.animation.json");
    }


}
