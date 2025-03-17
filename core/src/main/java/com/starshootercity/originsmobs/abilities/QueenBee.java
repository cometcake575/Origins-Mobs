package com.starshootercity.originsmobs.abilities;

import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.jetbrains.annotations.NotNull;

public class QueenBee implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "When you collect honey, the bees won't try to attack you.";
    }

    @Override
    public String title() {
        return "Queen Bee";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:queen_bee");
    }

    @EventHandler
    public void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event) {
        if (event.getEntity().getType() != EntityType.BEE) return;
        if (event.getReason() == EntityTargetEvent.TargetReason.CLOSEST_PLAYER) {
            if (event.getTarget() == null) return;
            runForAbility(event.getTarget(), player -> event.setCancelled(true));
        }
    }
}
