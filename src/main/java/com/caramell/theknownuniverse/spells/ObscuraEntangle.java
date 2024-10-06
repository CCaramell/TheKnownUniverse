package com.caramell.theknownuniverse.spells;
import com.caramell.theknownuniverse.TheKnownUniverse;

import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData;
import io.redspace.ironsspellbooks.entity.spells.void_tentacle.VoidTentacle;
import io.redspace.ironsspellbooks.entity.spells.root.RootEntity;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.spells.eldritch.AbstractEldritchSpell;
import io.redspace.ironsspellbooks.util.ModTags;
import net.minecraft.network.chat.Component;
import io.redspace.ironsspellbooks.util.Log;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;


import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class ObscuraEntangle extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(TheKnownUniverse.MOD_ID, "obscura_entangle");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.theknownuniverse.damage", Utils.stringTruncation(getSpellPower(spellLevel, caster), 2)),
                Component.translatable("ui.theknownuniverse.radius", Utils.stringTruncation(getRings(spellLevel, caster) * 1.3f, 1))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.LEGENDARY)
            .setSchoolResource(SchoolRegistry.ELDRITCH_RESOURCE)
            .setMaxLevel(10)
            .setCooldownSeconds(30)
            .build();

    public ObscuraEntangle() {
        this.manaCostPerLevel = 15;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 5;
        this.castTime = 10;
        this.baseManaCost = 30;
    }

    @Override
    public CastType getCastType() {
        return CastType.INSTANT;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    @Override
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(SoundRegistry.VOID_TENTACLES_START.get());
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(SoundRegistry.VOID_TENTACLES_FINISH.get());
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        Utils.preCastTargetHelper(level, entity, playerMagicData, this, 32, .15f, false);
        return true;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        int rings = getRings(spellLevel, entity);
        int count = 2;
        Vec3 center = null;
        if (playerMagicData.getAdditionalCastData() instanceof TargetEntityCastData castTargetingData) {
            var target = castTargetingData.getTarget((ServerLevel) level);

            if (Log.SPELL_DEBUG) {
                TheKnownUniverse.LOGGER.debug("RootSpell.onCast.1 targetEntity:{}", target);
            }
            if (target != null && !target.getType().is(ModTags.CANT_ROOT)) {
                center = target.position();
                if (Log.SPELL_DEBUG) {
                    TheKnownUniverse.LOGGER.debug("RootSpell.onCast.2 targetEntity:{}", target);
                }
                Vec3 spawn = target.position();
                RootEntity rootEntity = new RootEntity(level, entity);
                rootEntity.setDuration(getDuration(spellLevel, entity));
                rootEntity.setTarget(target);
                rootEntity.moveTo(spawn);
                level.addFreshEntity(rootEntity);
                target.stopRiding();
                target.startRiding(rootEntity, true);
            }
        }
        if (center == null) {
            center = Utils.raycastForEntity(level, entity, 48, true, .15f).getLocation();
            center = Utils.moveToRelativeGroundLevel(level, center, 6);
        }
        level.playSound(entity instanceof Player player ? player : null, center.x, center.y, center.z, SoundRegistry.VOID_TENTACLES_FINISH.get(), SoundSource.AMBIENT, 1, 1);

        for (int r = 0; r < rings; r++) {
            float tentacles = count + r * 2;
            for (int i = 0; i < tentacles; i++) {
                Vec3 random = new Vec3(Utils.getRandomScaled(1), Utils.getRandomScaled(1), Utils.getRandomScaled(1));
                Vec3 spawn = center.add(new Vec3(0, 0, 1.3 * (r + 1)).yRot(((6.281f / tentacles) * i))).add(random);

                spawn = Utils.moveToRelativeGroundLevel(level, spawn, 8);
                if (!level.getBlockState(BlockPos.containing(spawn).below()).isAir()) {
                    VoidTentacle tentacle = new VoidTentacle(level, entity, getDamage(spellLevel, entity));
                    tentacle.moveTo(spawn);
                    tentacle.setYRot(Utils.random.nextInt(360));
                    level.addFreshEntity(tentacle);
                }
            }
        }
        //In order to trigger sculk sensors
        level.gameEvent(null, GameEvent.ENTITY_ROAR, center);
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    @Nullable
    private LivingEntity findTarget(LivingEntity caster) {
        var target = Utils.raycastForEntity(caster.level(), caster, 32, true, 0.35f);
        if (target instanceof EntityHitResult entityHit && entityHit.getEntity() instanceof LivingEntity livingTarget) {
            return livingTarget;
        } else {
            return null;
        }
    }

    public int getDuration(int spellLevel, LivingEntity caster) {
        return (int) (getSpellPower(spellLevel, caster) * 20);
    }

    private float getDamage(int spellLevel, LivingEntity entity) {
        return getSpellPower(spellLevel, entity);
    }

    private int getRings(int spellLevel, LivingEntity entity) {
        return 1 + spellLevel;
    }
}
