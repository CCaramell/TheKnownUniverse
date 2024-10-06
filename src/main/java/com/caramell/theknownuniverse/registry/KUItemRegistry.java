package com.caramell.theknownuniverse.registry;

import com.caramell.theknownuniverse.TheKnownUniverse;
import com.caramell.theknownuniverse.swords.NahilsBlade;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class KUItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TheKnownUniverse.MOD_ID);

    public static final RegistryObject<SwordItem> NAHILS_BLADE = ITEMS.register("nahils_blade",
            () -> new NahilsBlade(SpellDataRegistryHolder.of(new SpellDataRegistryHolder(KUSpellRegistry.OBSCURA_ENTANGLE, 1))));


    public static void register() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}

