package com.starshootercity.originsmobs.abilities;

import com.starshootercity.abilities.types.Ability;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Illager implements Ability, Listener {
    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:illager");
    }

    private final List<EntityType> ILLAGERS = List.of(
            EntityType.ILLUSIONER,
            EntityType.EVOKER,
            EntityType.VINDICATOR,
            EntityType.RAVAGER,
            EntityType.VINDICATOR,
            EntityType.WITCH,
            EntityType.VEX,
            EntityType.PILLAGER
    );

    @EventHandler
    public void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event) {
        if (ILLAGERS.contains(event.getEntity().getType())) {
            if (event.getTarget() == null) return;
            runForAbility(event.getTarget(), player -> event.setCancelled(true));
        }
    }
}
