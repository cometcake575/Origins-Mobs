package com.starshootercity.originsmobs.abilities;

import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.events.PlayerSwapOriginEvent;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.jetbrains.annotations.NotNull;

public class RideableCreature implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "Other players can ride you!";
    }

    @Override
    public String title() {
        return "Rideable Creature";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:rideable_creature");
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        runForAbility(event.getRightClicked(), player -> {
            event.getPlayer().swingMainHand();
            player.addPassenger(event.getPlayer());
        });
    }

    @EventHandler
    public void onPlayerSwapOrigin(PlayerSwapOriginEvent event) {
        for (Entity entity : event.getPlayer().getPassengers()) {
            event.getPlayer().removePassenger(entity);
        }
    }
}
