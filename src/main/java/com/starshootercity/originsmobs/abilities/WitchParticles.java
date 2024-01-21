package com.starshootercity.originsmobs.abilities;

import com.starshootercity.abilities.ParticleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Particle;
import org.jetbrains.annotations.NotNull;

public class WitchParticles implements ParticleAbility {
    @Override
    public Particle getParticle() {
        return Particle.SPELL_WITCH;
    }

    @Override
    public int getFrequency() {
        return 4;
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:witch_particles");
    }
}
