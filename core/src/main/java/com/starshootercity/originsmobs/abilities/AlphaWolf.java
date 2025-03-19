package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.jetbrains.annotations.NotNull;

public class AlphaWolf implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "Wolves you tame are stronger!";
    }

    @Override
    public String title() {
        return "Alpha Wolf";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:alpha_wolf");
    }

    @EventHandler
    public void onEntityTame(EntityTameEvent event) {
        if (event.getEntity().getType() == EntityType.WOLF) {
            if (event.getOwner() instanceof Player p) {
                runForAbility(p, player -> makeWolfStronger(event.getEntity()));
            }
        }
    }

    @EventHandler
    public void onEntityBreed(EntityBreedEvent event) {
        if (event.getEntity().getType() == EntityType.WOLF) {
            if (event.getBreeder() == null) return;
            runForAbility(event.getBreeder(), player -> makeWolfStronger(event.getEntity()));
        }
    }

    private void makeWolfStronger(LivingEntity wolf) {
        AttributeInstance maxHealth = wolf.getAttribute(OriginsReborn.getNMSInvoker().getMaxHealthAttribute());
        if (maxHealth != null) {
            maxHealth.setBaseValue(maxHealth.getDefaultValue() + 10);
        }
        AttributeInstance attackDamage = wolf.getAttribute(OriginsReborn.getNMSInvoker().getAttackDamageAttribute());
        if (attackDamage != null) {
            attackDamage.setBaseValue(attackDamage.getDefaultValue() + 2);
        }
    }
}
