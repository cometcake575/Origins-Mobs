package com.starshootercity.originsmobs.abilities;

import com.starshootercity.abilities.ParticleAbility;
import com.starshootercity.originsmobs.OriginsMobs;
import net.kyori.adventure.key.Key;
import org.bukkit.Particle;
import org.jetbrains.annotations.NotNull;

public class WitchParticles implements ParticleAbility {
    @Override
    public Particle getParticle() {
        return OriginsMobs.getNMSInvoker().getWitchParticle();
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
