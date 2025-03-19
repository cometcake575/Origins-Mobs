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
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class WolfHowl implements VisibleAbility, Listener, CooldownAbility {
    @Override
    public String description() {
        return "You can use the left click key when holding nothing to howl, and give speed and strength to nearby wolves and yourself.";
    }

    @Override
    public String title() {
        return "Howl";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:wolf_howl");
    }

    @EventHandler
    public void onPlayerLeftClick(PlayerLeftClickEvent event) {
        if (event.getClickedBlock() != null) return;
        if (event.getItem() != null) return;
        runForAbility(event.getPlayer(), player -> {
            if (hasCooldown(player)) return;
            setCooldown(player);
            player.getWorld().playSound(player, Sound.ENTITY_WOLF_HOWL, SoundCategory.PLAYERS, 1, 0.5f);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 400, 0, false, true));
            player.addPotionEffect(new PotionEffect(OriginsReborn.getNMSInvoker().getStrengthEffect(), 400, 0, false, true));
            double ran = getConfigOption(OriginsMobs.getInstance(), range, ConfigManager.SettingType.DOUBLE);
            for (Entity entity : player.getNearbyEntities(ran, ran, ran)) {
                if (entity instanceof LivingEntity livingEntity) {
                    if (livingEntity.getType() == EntityType.WOLF) {
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 400, 0, false, true));
                        livingEntity.addPotionEffect(new PotionEffect(OriginsReborn.getNMSInvoker().getStrengthEffect(), 400, 0, false, true));
                    }
                }
            }
        });
    }

    private final String range = "range";

    @Override
    public void initialize(JavaPlugin plugin) {
        registerConfigOption(OriginsMobs.getInstance(), range, Collections.singletonList("Range to check for wolves"), ConfigManager.SettingType.DOUBLE, 5d);
    }

    @Override
    public Cooldowns.CooldownInfo getCooldownInfo() {
        return new Cooldowns.CooldownInfo(900, "wolf_howl");
    }
}
