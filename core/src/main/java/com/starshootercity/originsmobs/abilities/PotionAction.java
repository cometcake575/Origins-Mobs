package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.types.CooldownAbility;
import com.starshootercity.abilities.types.VisibleAbility;
import com.starshootercity.cooldowns.Cooldowns;
import com.starshootercity.events.PlayerLeftClickEvent;
import com.starshootercity.originsmobs.OriginsMobs;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class PotionAction implements VisibleAbility, Listener, CooldownAbility {
    @Override
    public String description() {
        return "Get a random potion effect, based on the situation you are in.";
    }

    @Override
    public String title() {
        return "Perfect Potion";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:potion_action");
    }

    @EventHandler
    public void onPlayerLeftClick(PlayerLeftClickEvent event) {
        if (event.getItem() != null) return;
        runForAbility(event.getPlayer(), player -> {
            if (hasCooldown(player)) return;
            setCooldown(player);
            player.getWorld().playSound(player, Sound.ENTITY_WITCH_DRINK, SoundCategory.VOICE, 1, 1);
            PotionEffectType effectType;
            if (player.getFireTicks() > 0) {
                effectType = PotionEffectType.FIRE_RESISTANCE;
            } else if (player.getFallDistance() >= 4) {
                effectType = PotionEffectType.SLOW_FALLING;
            } else if (OriginsReborn.getNMSInvoker().isUnderWater(player)) {
                effectType = PotionEffectType.WATER_BREATHING;
            } else {
                effectType = PotionEffectType.SPEED;
            }
            player.addPotionEffect(new PotionEffect(effectType,
                    getConfigOption(OriginsMobs.getInstance(), potionDuration, ConfigManager.SettingType.INTEGER), 0));
        });
    }

    private final String potionDuration = "potion_duration";

    @Override
    public void initialize(JavaPlugin plugin) {
        registerConfigOption(OriginsMobs.getInstance(), potionDuration, Collections.singletonList("Duration of the potion effect in ticks"), ConfigManager.SettingType.INTEGER, 200);
    }

    @Override
    public Cooldowns.CooldownInfo getCooldownInfo() {
        return new Cooldowns.CooldownInfo(600, "potion_action");
    }
}
