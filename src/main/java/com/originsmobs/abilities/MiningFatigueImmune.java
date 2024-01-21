package com.originsmobs.abilities;

import com.starshootercity.abilities.Ability;
import com.starshootercity.abilities.AbilityRegister;
import net.kyori.adventure.key.Key;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class MiningFatigueImmune implements Ability, Listener {
    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:mining_fatigue_immune");
    }

    @EventHandler
    public void onEntityPotionEffect(EntityPotionEffectEvent event) {
        AbilityRegister.runForAbility(event.getEntity(), getKey(), () -> {
            if (event.getNewEffect() != null) {
                if (event.getNewEffect().getType() == PotionEffectType.SLOW_DIGGING) {
                    event.setCancelled(true);
                }
            }
        });
    }
}
