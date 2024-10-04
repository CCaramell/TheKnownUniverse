package com.caramell.theknownuniverse.item;

import com.caramell.theknownuniverse.TheKnownUniverse;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TheKnownUniverse.MOD_ID);

    public static final RegistryObject<Item> NAHILS_BLADE = ITEMS.register("nahils_blade",
            () -> new Item(new Item.Properties()));

    public static void register() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}

