package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.ParticleAbility;
import com.starshootercity.originsmobs.OriginsMobs;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.Particle;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class WitchParticles implements ParticleAbility {
    @Override
    public Particle getParticle() {
        return OriginsMobs.getNMSInvoker().getWitchParticle();
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:witch_particles");
    }

    @Override
    public int getFrequency() {
        return getConfigOption(OriginsReborn.getInstance(), frequency, ConfigManager.SettingType.INTEGER);
    }

    private final String frequency = "frequency";

    @Override
    public void initialize() {
        registerConfigOption(OriginsReborn.getInstance(), frequency, Collections.singletonList("How often (in ticks) the particles should appear"), ConfigManager.SettingType.INTEGER, 4);
    }
}
