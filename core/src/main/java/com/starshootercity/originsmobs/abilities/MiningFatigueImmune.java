package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.types.Ability;
import net.kyori.adventure.key.Key;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.jetbrains.annotations.NotNull;

public class MiningFatigueImmune implements Ability, Listener {
    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:mining_fatigue_immune");
    }

    @EventHandler
    public void onEntityPotionEffect(EntityPotionEffectEvent event) {
        runForAbility(event.getEntity(), player -> {
            if (event.getNewEffect() != null) {
                if (event.getNewEffect().getType().equals(OriginsReborn.getNMSInvoker().getMiningFatigueEffect())) {
                    event.setCancelled(true);
                }
            }
        });
    }
}
