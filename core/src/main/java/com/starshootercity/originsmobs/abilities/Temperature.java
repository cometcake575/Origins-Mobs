package com.starshootercity.originsmobs.abilities;

import com.starshootercity.abilities.types.CooldownAbility;
import com.starshootercity.cooldowns.Cooldowns;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Temperature implements CooldownAbility {
    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:temperature");
    }

    private static final Map<Player, Integer> playerTemperatureMap = new HashMap<>();

    public static Temperature INSTANCE = new Temperature();

    public int getTemperature(Player player) {
        return playerTemperatureMap.getOrDefault(player, 0);
    }

    public void setTemperature(Player player, int amount) {
        playerTemperatureMap.put(player, Math.max(0, Math.min(amount, 100)));
        setCooldown(player, getTemperature(player));
    }

    @Override
    public Cooldowns.CooldownInfo getCooldownInfo() {
        return new Cooldowns.CooldownInfo(100, "temperature", true, true);
    }
}
