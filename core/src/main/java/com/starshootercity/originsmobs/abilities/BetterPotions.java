package com.starshootercity.originsmobs.abilities;

import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsmobs.OriginsMobs;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class BetterPotions implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "You consume potions better than most, Potions will last longer when you drink them.";
    }

    @Override
    public String title() {
        return "Better Potions";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:better_potions");
    }

    @EventHandler
    public void onEntityPotionEffect(EntityPotionEffectEvent event) {
        if (event.getCause() == EntityPotionEffectEvent.Cause.POTION_DRINK) {
            runForAbility(event.getEntity(), player -> {
                if (event.getNewEffect() == null) return;
                PotionEffect effect = event.getNewEffect().withDuration((int) (event.getNewEffect().getDuration() *
                                        getConfigOption(OriginsMobs.getInstance(), durationMultiplier, ConfigManager.SettingType.FLOAT)));
                event.setCancelled(true);
                player.addPotionEffect(effect);
            });
        }
    }

    private final String durationMultiplier = "duration_multiplier";

    @Override
    public void initialize() {
        registerConfigOption(OriginsMobs.getInstance(), durationMultiplier, Collections.singletonList("The amount to multiply a potion's duration by"), ConfigManager.SettingType.FLOAT, 2f);
    }
}
