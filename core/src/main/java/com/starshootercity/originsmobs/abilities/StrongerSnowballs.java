package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.types.VisibleAbility;
import com.starshootercity.originsmobs.OriginsMobs;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class StrongerSnowballs implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "Snowballs you throw are packed with ice, and deal 1 damage!";
    }

    @Override
    public String title() {
        return "Stronger Snowballs";
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
            runForAbility(player, p -> event.getEntity().getPersistentDataContainer().set(strongSnowballKey, OriginSwapper.BooleanPDT.BOOLEAN, true));
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getHitEntity() instanceof LivingEntity entity) {
            if (Boolean.TRUE.equals(event.getEntity().getPersistentDataContainer().get(strongSnowballKey, OriginSwapper.BooleanPDT.BOOLEAN))) {
                OriginsReborn.getNMSInvoker().dealFreezeDamage(entity, 1);
                Vector vector = event.getEntity().getVelocity();
                if (!getConfigOption(OriginsMobs.getInstance(), doKnockback, ConfigManager.SettingType.BOOLEAN)) return;
                OriginsReborn.getNMSInvoker().knockback(entity, 0.5, -vector.getX(), -vector.getZ());
            }
        }
    }

    private final String doKnockback = "do_knockback";

    @Override
    public void initialize(JavaPlugin plugin) {
        registerConfigOption(OriginsMobs.getInstance(), doKnockback, Collections.singletonList("Should snowballs do knockback"), ConfigManager.SettingType.BOOLEAN, true);
    }
}
