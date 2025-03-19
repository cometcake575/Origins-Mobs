package com.starshootercity.originsmobs.abilities;

import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

public class WaterCombatant implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "You deal more damage while in water.";
    }

    @Override
    public String title() {
        return "Water Combatant";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:water_combatant");
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        runForAbility(event.getDamager(), player -> {
            if (player.isInWater()) {
                event.setDamage(event.getDamage() + 3);
            }
        });
    }
}
