package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stinger implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("When you punch someone with your fist, you poison them for a few seconds.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Stinger", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:stinger");
    }

    private final Map<Player, Integer> lastStungTicks = new HashMap<>();

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof LivingEntity entity && event.getDamager() instanceof Player player) {
            AbilityRegister.runForAbility(player, getKey(), () -> {
                if (Bukkit.getCurrentTick() - lastStungTicks.getOrDefault(player, Bukkit.getCurrentTick() - 100) >= 100) {
                    lastStungTicks.put(player, Bukkit.getCurrentTick());
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 60, 0, false, true));
                }
            });
        }
    }
}
