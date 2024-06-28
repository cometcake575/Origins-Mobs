package com.starshootercity.originsmobs.abilities;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.OriginsReborn;
import com.starshootercity.SavedPotionEffect;
import com.starshootercity.ShortcutUtils;
import com.starshootercity.abilities.Ability;
import com.starshootercity.abilities.AbilityRegister;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class WaterVision implements Ability, Listener {
    Map<Player, SavedPotionEffect> storedEffects = new HashMap<>();

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            AbilityRegister.runForAbility(player, getKey(), () -> {
                if (OriginsReborn.getNMSInvoker().isUnderWater(player)) {
                    PotionEffect effect = player.getPotionEffect(PotionEffectType.NIGHT_VISION);
                    boolean ambient = false;
                    boolean showParticles = false;
                    if (effect != null) {
                        ambient = effect.isAmbient();
                        showParticles = effect.hasParticles();
                        if (effect.getAmplifier() != -1) {
                            storedEffects.put(player, new SavedPotionEffect(effect, Bukkit.getCurrentTick()));
                            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                        }
                    }
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, ShortcutUtils.infiniteDuration(), -1, ambient, showParticles));
                } else {
                    if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                        PotionEffect effect = player.getPotionEffect(PotionEffectType.NIGHT_VISION);
                        if (effect != null) {
                            if (effect.getAmplifier() == -1) player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                        }
                    }
                    if (storedEffects.containsKey(player)) {
                        SavedPotionEffect effect = storedEffects.get(player);
                        storedEffects.remove(player);
                        PotionEffect potionEffect = effect.effect();
                        int time = potionEffect.getDuration() - (Bukkit.getCurrentTick() - effect.currentTime());
                        if (time > 0) {
                            player.addPotionEffect(new PotionEffect(
                                    potionEffect.getType(),
                                    time,
                                    potionEffect.getAmplifier(),
                                    potionEffect.isAmbient(),
                                    potionEffect.hasParticles()
                            ));
                        }
                    }
                }
            });
        }
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() == Material.MILK_BUCKET) {
            storedEffects.remove(event.getPlayer());
        }
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:water_vision");
    }
}
