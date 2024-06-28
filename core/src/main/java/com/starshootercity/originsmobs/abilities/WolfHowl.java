package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.events.PlayerLeftClickEvent;
import net.kyori.adventure.key.Key;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WolfHowl implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You can use the left click key when holding nothing to howl, and give speed and strength to nearby wolves and yourself.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Howl", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:wolf_howl");
    }

    @EventHandler
    public void onPlayerLeftClick(PlayerLeftClickEvent event) {
        if (event.getClickedBlock() != null) return;
        if (event.getItem() != null) return;
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
            event.getPlayer().getWorld().playSound(event.getPlayer(), Sound.ENTITY_WOLF_HOWL, SoundCategory.PLAYERS, 1, 0.5f);
            for (Entity entity : event.getPlayer().getNearbyEntities(5, 5, 5)) {
                if (entity instanceof LivingEntity livingEntity) {
                    if (livingEntity.getType() == EntityType.WOLF || livingEntity == event.getPlayer()) {
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 400, 0, false, true));
                        livingEntity.addPotionEffect(new PotionEffect(OriginsReborn.getNMSInvoker().getStrengthEffect(), 400, 0, false, true));
                    }
                }
            }
        });
    }
}
