package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AlphaWolf implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("Wolves you tame are stronger!", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Alpha Wolf", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:alpha_wolf");
    }

    @EventHandler
    public void onEntityTame(EntityTameEvent event) {
        if (event.getEntity().getType() == EntityType.WOLF) {
            if (event.getOwner() instanceof Player player) {
                AbilityRegister.runForAbility(player, getKey(), () -> makeWolfStronger(event.getEntity()));
            }
        }
    }

    @EventHandler
    public void onEntityBreed(EntityBreedEvent event) {
        if (event.getEntity().getType() == EntityType.WOLF) {
            if (event.getBreeder() == null) return;
            AbilityRegister.runForAbility(event.getBreeder(), getKey(), () -> makeWolfStronger(event.getEntity()));
        }
    }

    private void makeWolfStronger(LivingEntity wolf) {
        AttributeInstance maxHealth = wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (maxHealth != null) {
            maxHealth.setBaseValue(maxHealth.getDefaultValue() + 10);
        }
        AttributeInstance attackDamage = wolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
        if (attackDamage != null) {
            attackDamage.setBaseValue(attackDamage.getDefaultValue() + 2);
        }
    }
}
