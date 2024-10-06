package com.caramell.theknownuniverse;

import com.caramell.theknownuniverse.registry.KUItemRegistry;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TheKnownUniverse.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class KUCreativeModeTabs {

    @SubscribeEvent
    public static void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(KUItemRegistry.NAHILS_BLADE.get());
        }
    }

}
