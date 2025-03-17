package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.cooldowns.CooldownAbility;
import com.starshootercity.cooldowns.Cooldowns;
import com.starshootercity.events.PlayerLeftClickEvent;
import com.starshootercity.originsmobs.OriginsMobs;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class ElderMagic implements VisibleAbility, Listener, CooldownAbility {
    @Override
    public String description() {
        return "You can cast a spell on nearby players to slow down their mining speed.";
    }

    @Override
    public String title() {
        return "Elder Magic";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:elder_magic");
    }

    @EventHandler
    public void onPlayerLeftClick(PlayerLeftClickEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR) return;
        runForAbility(event.getPlayer(), p -> {
            if (hasCooldown(p)) return;
            setCooldown(p);
            int ran = getConfigOption(OriginsMobs.getInstance(), range, ConfigManager.SettingType.INTEGER);
            for (Entity entity : p.getNearbyEntities(ran, ran, ran)) {
                if (entity instanceof Player player) {
                    if (hasAbility(player)) return;
                    player.addPotionEffect(new PotionEffect(OriginsReborn.getNMSInvoker().getMiningFatigueEffect(),
                            getConfigOption(OriginsMobs.getInstance(), duration, ConfigManager.SettingType.INTEGER),
                            getConfigOption(OriginsMobs.getInstance(), strength, ConfigManager.SettingType.INTEGER), false, true));
                    player.spawnParticle(OriginsMobs.getNMSInvoker().getElderGuardianParticle(), player.getLocation(), 1);
                    player.playSound(player, Sound.ENTITY_ELDER_GUARDIAN_CURSE, SoundCategory.HOSTILE, 1, 1);
                }
            }
        });
    }

    private final String range = "range";
    private final String duration = "mining_fatigue_duration";
    private final String strength = "mining_fatigue_strength";

    @Override
    public void initialize() {
        registerConfigOption(OriginsMobs.getInstance(), range, Collections.singletonList("The range of the effect"), ConfigManager.SettingType.DOUBLE, 5d);
        registerConfigOption(OriginsMobs.getInstance(), duration, Collections.singletonList("The duration in ticks of the effect"), ConfigManager.SettingType.INTEGER, 600);
        registerConfigOption(OriginsMobs.getInstance(), strength, Collections.singletonList("The strength of the effect"), ConfigManager.SettingType.INTEGER, 1);
    }

    @Override
    public Cooldowns.CooldownInfo getCooldownInfo() {
        return new Cooldowns.CooldownInfo(600, "elder_magic");
    }
}
