package net.sunbuilder.legendary_items.items.client;

import net.sunbuilder.legendary_items.items.client.AthenasShieldModel;
import net.sunbuilder.legendary_items.items.custom.AthenasShield;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class AthenasShieldRenderer extends GeoItemRenderer<AthenasShield> {
    public AthenasShieldRenderer() {
        super(new AthenasShieldModel());
    }
}
