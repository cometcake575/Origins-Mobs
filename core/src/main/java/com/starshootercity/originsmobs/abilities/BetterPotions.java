package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BetterPotions implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You consume potions better than most, Potions will last longer when you drink them.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Better Potions", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:better_potions");
    }

    @EventHandler
    public void onEntityPotionEffect(EntityPotionEffectEvent event) {
        if (event.getCause() == EntityPotionEffectEvent.Cause.POTION_DRINK) {
            if (event.getEntity() instanceof Player player) {
                AbilityRegister.runForAbility(player, getKey(), () -> {
                    if (event.getNewEffect() == null) return;
                    PotionEffect effect = event.getNewEffect().withDuration(event.getNewEffect().getDuration() * 2);
                    event.setCancelled(true);
                    player.addPotionEffect(effect);
                });
            }
        }
    }
}
