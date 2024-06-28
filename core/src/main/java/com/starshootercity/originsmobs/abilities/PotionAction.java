package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.events.PlayerLeftClickEvent;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PotionAction implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("Get a random potion effect, based on the situation you are in.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Perfect Potion", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:potion_action");
    }

    private final Map<Player, Integer> lastUsedMagicTime = new HashMap<>();

    @EventHandler
    public void onPlayerLeftClick(PlayerLeftClickEvent event) {
        if (event.getItem() != null) return;
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
            if (Bukkit.getCurrentTick() - lastUsedMagicTime.getOrDefault(event.getPlayer(), Bukkit.getCurrentTick() - 600) < 600) return;
            event.getPlayer().getWorld().playSound(event.getPlayer(), Sound.ENTITY_WITCH_DRINK, SoundCategory.VOICE, 1, 1);
            lastUsedMagicTime.put(event.getPlayer(), Bukkit.getCurrentTick());
            PotionEffectType effectType;
            if (event.getPlayer().getFireTicks() > 0) {
                effectType = PotionEffectType.FIRE_RESISTANCE;
            } else if (event.getPlayer().getFallDistance() >= 4) {
                effectType = PotionEffectType.SLOW_FALLING;
            } else if (OriginsReborn.getNMSInvoker().isUnderWater(event.getPlayer())) {
                effectType = PotionEffectType.WATER_BREATHING;
            } else {
                effectType = PotionEffectType.SPEED;
            }
            event.getPlayer().addPotionEffect(new PotionEffect(effectType, 200, 0));
        });
    }
}
