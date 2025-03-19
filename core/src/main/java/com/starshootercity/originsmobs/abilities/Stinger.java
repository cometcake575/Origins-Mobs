package com.starshootercity.originsmobs.abilities;

import com.starshootercity.abilities.types.VisibleAbility;
import com.starshootercity.originsmobs.OriginsMobs;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Stinger implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "When you punch someone with your fist, you poison them for a few seconds.";
    }

    @Override
    public String title() {
        return "Stinger";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:stinger");
    }

    private final Map<Player, Integer> lastStungTicks = new HashMap<>();

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            runForAbility(event.getDamager(), player -> {
                if (Bukkit.getCurrentTick() - lastStungTicks.getOrDefault(player, Bukkit.getCurrentTick() - 100) >= 100) {
                    lastStungTicks.put(player, Bukkit.getCurrentTick());
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON,
                            getConfigOption(OriginsMobs.getInstance(), duration, ConfigManager.SettingType.INTEGER),
                            getConfigOption(OriginsMobs.getInstance(), strength, ConfigManager.SettingType.INTEGER), false, true));
                }
            });
        }
    }

    private final String duration = "poison_duration";
    private final String strength = "poison_strength";

    @Override
    public void initialize(JavaPlugin plugin) {
        registerConfigOption(OriginsMobs.getInstance(), duration, Collections.singletonList("Duration of the poison effect in ticks"), ConfigManager.SettingType.INTEGER, 60);
        registerConfigOption(OriginsMobs.getInstance(), strength, Collections.singletonList("Strength of the poison effect"), ConfigManager.SettingType.INTEGER, 0);
    }
}
