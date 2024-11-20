package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.cooldowns.CooldownAbility;
import com.starshootercity.cooldowns.Cooldowns;
import com.starshootercity.events.PlayerLeftClickEvent;
import net.kyori.adventure.key.Key;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PotionAction implements VisibleAbility, Listener, CooldownAbility {
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

    @EventHandler
    public void onPlayerLeftClick(PlayerLeftClickEvent event) {
        if (event.getItem() != null) return;
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
            if (hasCooldown(event.getPlayer())) return;
            setCooldown(event.getPlayer());
            event.getPlayer().getWorld().playSound(event.getPlayer(), Sound.ENTITY_WITCH_DRINK, SoundCategory.VOICE, 1, 1);
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

    @Override
    public Cooldowns.CooldownInfo getCooldownInfo() {
        return new Cooldowns.CooldownInfo(600, "potion_action");
    }
}
