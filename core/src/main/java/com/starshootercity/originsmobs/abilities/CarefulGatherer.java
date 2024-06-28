package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CarefulGatherer implements VisibleAbility, Listener {

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("Sweet Berry Bushes don't hurt you at all.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Careful Gatherer", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:careful_gatherer");
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByBlockEvent event) {
        if (event.getDamager() == null) return;
        if (event.getDamager().getType() != Material.SWEET_BERRY_BUSH) return;
        AbilityRegister.runForAbility(event.getEntity(), getKey(), () -> event.setCancelled(true));
    }
}
