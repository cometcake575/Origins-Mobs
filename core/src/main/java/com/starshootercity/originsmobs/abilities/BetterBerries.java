package com.starshootercity.originsmobs.abilities;

import com.starshootercity.abilities.types.VisibleAbility;
import com.starshootercity.originsmobs.OriginsMobs;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class BetterBerries implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "Berries taste extra delicious to you!";
    }

    @Override
    public String title() {
        return "Better Berries";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:better_berries");
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() != Material.SWEET_BERRIES) return;
        runForAbility(event.getPlayer(), player -> {
            event.getPlayer().setFoodLevel(Math.min(event.getPlayer().getFoodLevel() +
                    getConfigOption(OriginsMobs.getInstance(), foodIncrease, ConfigManager.SettingType.INTEGER), 20));
            event.getPlayer().setSaturation(Math.min(event.getPlayer().getSaturation() +
                    getConfigOption(OriginsMobs.getInstance(), saturationIncrease, ConfigManager.SettingType.FLOAT), event.getPlayer().getFoodLevel()));
        });
    }

    private final String foodIncrease = "food_increase";
    private final String saturationIncrease = "saturation_increase";

    @Override
    public void initialize(JavaPlugin plugin) {
        registerConfigOption(OriginsMobs.getInstance(), foodIncrease, Collections.singletonList("The amount to increase the food given by Sweet Berries by"), ConfigManager.SettingType.INTEGER, 2);
        registerConfigOption(OriginsMobs.getInstance(), saturationIncrease, Collections.singletonList("The amount to increase the saturation given by Sweet Berries by"), ConfigManager.SettingType.FLOAT, 1f);
    }
}
