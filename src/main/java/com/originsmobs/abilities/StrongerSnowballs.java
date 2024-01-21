package com.originsmobs.abilities;

import com.starshootercity.DamageApplier;
import com.starshootercity.OriginSwapper;
import com.originsmobs.OriginsMobs;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StrongerSnowballs implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("Snowballs you throw are packed with ice, and deal 1 damage!", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Stronger Snowballs", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:stronger_snowballs");
    }

    private final NamespacedKey strongSnowballKey = new NamespacedKey(OriginsMobs.getInstance(), "strong-snowball");

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (event.getEntity().getType() != EntityType.SNOWBALL) return;
        if (event.getEntity().getShooter() instanceof Player player) {
            AbilityRegister.runForAbility(player, getKey(), () -> event.getEntity().getPersistentDataContainer().set(strongSnowballKey, PersistentDataType.BOOLEAN, true));
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getHitEntity() instanceof LivingEntity entity) {
            if (Boolean.TRUE.equals(event.getEntity().getPersistentDataContainer().get(strongSnowballKey, PersistentDataType.BOOLEAN))) {
                DamageApplier.damage(event.getHitEntity(), DamageApplier.DamageSource.FREEZING, 1);
                Vector vector = event.getEntity().getVelocity();
                entity.knockback(0.5, -vector.getX(), -vector.getZ());
            }
        }
    }
}
