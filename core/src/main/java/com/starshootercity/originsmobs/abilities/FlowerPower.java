package com.starshootercity.originsmobs.abilities;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.abilities.types.VisibleAbility;
import com.starshootercity.originsmobs.OriginsMobs;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Tag;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class FlowerPower implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "When near multiple flowers, you gain regeneration.";
    }

    @Override
    public String title() {
        return "Flower Power";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:flower_power");
    }

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        if (event.getTickNumber() % 40 != 0) return;
        for (Player p : Bukkit.getOnlinePlayers()) {
            runForAbility(p, player -> {
                int num = 0;
                int ran = getConfigOption(OriginsMobs.getInstance(), range, ConfigManager.SettingType.INTEGER);
                for (int x = -ran; x <= ran; x++) {
                    for (int y = -ran; y <= ran; y++) {
                        for (int z = -ran; z <= ran; z++) {
                            Location loc = player.getLocation().clone().add(new Vector(x, y, z));
                            if (loc.distance(player.getLocation()) > 3) continue;
                            if (Tag.FLOWERS.isTagged(loc.getBlock().getType())) num += 1;
                        }
                    }
                }
                if (num >= getConfigOption(OriginsMobs.getInstance(), requiredFlowers, ConfigManager.SettingType.INTEGER)) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,
                            200,
                            getConfigOption(OriginsMobs.getInstance(), strength, ConfigManager.SettingType.INTEGER), false, true));
                }
            });
        }
    }

    private final String range = "range";
    private final String requiredFlowers = "required_flowers";
    private final String strength = "regeneration_strength";

    @Override
    public void initialize(JavaPlugin plugin) {
        registerConfigOption(OriginsMobs.getInstance(), range, Collections.singletonList("Range to check for flowers in"), ConfigManager.SettingType.INTEGER, 3);
        registerConfigOption(OriginsMobs.getInstance(), requiredFlowers, Collections.singletonList("Required number of flowers"), ConfigManager.SettingType.INTEGER, 3);
        registerConfigOption(OriginsMobs.getInstance(), strength, Collections.singletonList("Strength of the regeneration"), ConfigManager.SettingType.INTEGER, 0);
    }
}
