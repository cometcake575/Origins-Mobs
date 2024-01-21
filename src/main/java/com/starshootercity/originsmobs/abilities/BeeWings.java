package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeeWings implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You can use your tiny bee wings to descend slower as an ability.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Bee Wings", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:bee_wings");
    }

    private final Map<Player, Integer> lastToggledSneak = new HashMap<>();
    private final Map<Player, Integer> beeWingsCooldown = new HashMap<>();

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
            if (Bukkit.getCurrentTick() - beeWingsCooldown.getOrDefault(event.getPlayer(), Bukkit.getCurrentTick() - 300) < 300) return;
            if (!event.isSneaking()) return;
            if (Bukkit.getCurrentTick() - lastToggledSneak.getOrDefault(event.getPlayer(), Bukkit.getCurrentTick() - 11) <= 10) {
                beeWingsCooldown.put(event.getPlayer(), Bukkit.getCurrentTick());
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 100, 0, false, true));
            } else {
                lastToggledSneak.put(event.getPlayer(), Bukkit.getCurrentTick());
            }
        });
    }
}
