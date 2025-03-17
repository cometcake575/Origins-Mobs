package com.starshootercity.originsmobs.abilities;

import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsmobs.OriginsMobs;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WarpedFungusEater implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "You can eat warped fungus to recover some hunger, along with a small speed boost.";
    }

    @Override
    public String title() {
        return "Fungus Hunger";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:warped_fungus_eater");
    }

    private final Map<Player, Integer> lastInteractedTicks = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        runForAbility(event.getPlayer(), player -> {
            if (lastInteractedTicks.getOrDefault(player, -1) == Bukkit.getCurrentTick()) return;
            lastInteractedTicks.put(player, Bukkit.getCurrentTick());
            if (event.getAction().isRightClick()) {
                if (event.getItem() == null) return;
                if (event.getItem().getType() == Material.WARPED_FUNGUS) {
                    if (event.getHand() != null) {
                        if (event.getHand() == EquipmentSlot.OFF_HAND) player.swingOffHand();
                        else player.swingMainHand();
                    }
                    event.getItem().setAmount(event.getItem().getAmount() - 1);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
                            getConfigOption(OriginsMobs.getInstance(), speedDuration, ConfigManager.SettingType.INTEGER),
                            getConfigOption(OriginsMobs.getInstance(), speedStrength, ConfigManager.SettingType.INTEGER), false, true));
                    player.setFoodLevel(Math.min(player.getFoodLevel() +
                            getConfigOption(OriginsMobs.getInstance(), foodIncrease, ConfigManager.SettingType.INTEGER), 20));
                    player.setSaturation(Math.min(player.getSaturation() +
                            getConfigOption(OriginsMobs.getInstance(), saturationIncrease, ConfigManager.SettingType.FLOAT), player.getFoodLevel()));
                }
            }
        });
    }

    private final String speedStrength = "speed_strength";
    private final String speedDuration = "speed_duration";
    private final String saturationIncrease = "saturation_increase";
    private final String foodIncrease = "food_increase";

    @Override
    public void initialize() {
        registerConfigOption(OriginsMobs.getInstance(), speedDuration, Collections.singletonList("Duration of the speed effect in ticks"), ConfigManager.SettingType.INTEGER, 200);
        registerConfigOption(OriginsMobs.getInstance(), speedStrength, Collections.singletonList("Strength of the speed effect"), ConfigManager.SettingType.INTEGER, 0);
        registerConfigOption(OriginsMobs.getInstance(), foodIncrease, Collections.singletonList("Amount to increase hunger level by"), ConfigManager.SettingType.INTEGER, 1);
        registerConfigOption(OriginsMobs.getInstance(), saturationIncrease, Collections.singletonList("Amount to increase saturation by"), ConfigManager.SettingType.FLOAT, 1f);
    }
}
