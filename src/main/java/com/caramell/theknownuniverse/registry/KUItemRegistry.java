package com.caramell.theknownuniverse.registry;

import com.caramell.theknownuniverse.TheKnownUniverse;
import com.caramell.theknownuniverse.swords.NahilsBlade;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import com.caramell.theknownuniverse.spells.ObscuraEntangle;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class KUItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TheKnownUniverse.MOD_ID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static final RegistryObject<Item> NAHILS_BLADE = ITEMS.register("nahils_blade",
            () -> new NahilsBlade(SpellDataRegistryHolder.of(new SpellDataRegistryHolder(KUSpellRegistry.OBSCURA_ENTANGLE, 5))));



}

