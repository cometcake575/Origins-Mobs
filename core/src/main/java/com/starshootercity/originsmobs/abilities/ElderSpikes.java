package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsmobs.OriginsMobs;
import net.kyori.adventure.key.Key;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class ElderSpikes implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("Spikes that have a chance to damage attackers!", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Elder Spikes", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:elder_spikes");
    }

    private final Random random = new Random();

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        AbilityRegister.runForAbility(event.getEntity(), getKey(), () -> {
            if (random.nextDouble() > 0.75) return;
            OriginsMobs.getNMSInvoker().dealThornsDamage(event.getDamager(), 4, event.getEntity());
        });
    }
}
