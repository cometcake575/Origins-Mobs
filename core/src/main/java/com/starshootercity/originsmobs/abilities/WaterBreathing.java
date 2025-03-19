package com.starshootercity.originsmobs.abilities;

import com.starshootercity.abilities.types.Ability;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

public class WaterBreathing implements Ability, Listener {
    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:water_breathing");
    }

    @EventHandler
    public void onEntityAirChange(EntityAirChangeEvent event) {
        if (event.getEntity() instanceof Player player) {
            runForAbility(player, p -> event.setAmount(p.getMaximumAir()));
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getCause().equals(EntityDamageEvent.DamageCause.DROWNING)) runForAbility(event.getEntity(), player -> event.setCancelled(true));
    }
}
