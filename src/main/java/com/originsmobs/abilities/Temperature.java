package com.originsmobs.abilities;

import com.starshootercity.abilities.Ability;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Temperature implements Ability {
    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:temperature");
    }

    private static final Map<Player, Integer> playerTemperatureMap = new HashMap<>();

    public static int getTemperature(Player player) {
        return playerTemperatureMap.getOrDefault(player, 0);
    }

    public static void setTemperature(Player player, int amount) {
        playerTemperatureMap.put(player, Math.max(0, Math.min(amount, 100)));
    }
}
