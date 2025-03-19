package com.starshootercity.originsmobs.abilities;

import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.jetbrains.annotations.NotNull;

public class CarefulGatherer implements VisibleAbility, Listener {

    @Override
    public String description() {
        return "Sweet Berry Bushes don't hurt you at all.";
    }

    @Override
    public String title() {
        return "Careful Gatherer";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:careful_gatherer");
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByBlockEvent event) {
        if (event.getDamager() == null) return;
        if (event.getDamager().getType() != Material.SWEET_BERRY_BUSH) return;
        runForAbility(event.getEntity(), player -> event.setCancelled(true));
    }
}
