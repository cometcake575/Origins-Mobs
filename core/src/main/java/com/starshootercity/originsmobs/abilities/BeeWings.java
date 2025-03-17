package com.starshootercity.originsmobs.abilities;

import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.cooldowns.CooldownAbility;
import com.starshootercity.cooldowns.Cooldowns;
import com.starshootercity.originsmobs.OriginsMobs;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BeeWings implements VisibleAbility, Listener, CooldownAbility {
    @Override
    public String description() {
        return "You can use your tiny bee wings to descend slower as an ability.";
    }

    @Override
    public String title() {
        return "Bee Wings";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:bee_wings");
    }

    private final Map<Player, Integer> lastToggledSneak = new HashMap<>();

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        runForAbility(event.getPlayer(), player -> {
            if (!event.isSneaking()) return;
            if (hasCooldown(player)) return;
            if (Bukkit.getCurrentTick() - lastToggledSneak.getOrDefault(player, Bukkit.getCurrentTick() - 11) <= 10) {
                setCooldown(player);
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, getConfigOption(OriginsMobs.getInstance(), duration, ConfigManager.SettingType.INTEGER), 0, false, true));
            } else {
                lastToggledSneak.put(player, Bukkit.getCurrentTick());
            }
        });
    }

    private final String duration = "duration";

    @Override
    public void initialize() {
        registerConfigOption(OriginsMobs.getInstance(), duration, Collections.singletonList("The duration in ticks of the slow falling effect"), ConfigManager.SettingType.INTEGER, 100);
    }

    @Override
    public Cooldowns.CooldownInfo getCooldownInfo() {
        return new Cooldowns.CooldownInfo(300, "bee_wings");
    }
}
