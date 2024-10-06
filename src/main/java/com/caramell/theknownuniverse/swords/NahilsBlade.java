package com.caramell.theknownuniverse.swords;

import io.redspace.ironsspellbooks.api.item.weapons.MagicSwordItem;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.item.weapons.ExtendedWeaponTiers;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;


import java.util.Map;
import java.util.UUID;

public class NahilsBlade extends MagicSwordItem {
    public NahilsBlade(SpellDataRegistryHolder[] imbuedSpells) {
        super(ExtendedWeaponTiers.AMETHYST, 15, -1.0f, imbuedSpells,
                Map.of(
                     //  AttributeRegistry.COOLDOWN_REDUCTION.get(), new AttributeModifier(UUID.fromString("212b5a66-2b43-4c18-ab05-6de0cc4d64d3"), "Weapon Modifier", .15, AttributeModifier.Operation.MULTIPLY_BASE)
                ),
                ItemPropertiesHelper.equipment(1).rarity(Rarity.EPIC));
    }
}
