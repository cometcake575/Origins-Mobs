package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.cooldowns.CooldownAbility;
import com.starshootercity.cooldowns.Cooldowns;
import com.starshootercity.events.PlayerLeftClickEvent;
import com.starshootercity.originsmobs.OriginsMobs;
import io.papermc.paper.event.entity.EntityMoveEvent;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Split implements VisibleAbility, Listener, CooldownAbility {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("Turn your food points into a small slime to defend you!", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Split Ability", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:split");
    }

    @EventHandler
    public void onPlayerLeftClick(PlayerLeftClickEvent event) {
        if (event.getItem() != null) return;
        if (event.getPlayer().getFoodLevel() < 8) return;
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
            if (hasCooldown(event.getPlayer())) return;
            setCooldown(event.getPlayer());
            event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() - 8);
            Slime slime = (Slime) event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.SLIME);
            slime.setSize(2);
            slime.getPersistentDataContainer().set(slimeKey, PersistentDataType.STRING, event.getPlayer().getUniqueId().toString());
        });
    }

    private Player getPlayerFromSlime(Entity slime) {
        String s = slime.getPersistentDataContainer().get(slimeKey, PersistentDataType.STRING);
        if (s == null) return null;
        return Bukkit.getPlayer(UUID.fromString(s));
    }

    private final NamespacedKey slimeKey = new NamespacedKey(OriginsMobs.getInstance(), "slime-target");

    @EventHandler
    public void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event) {
        if (event.getReason() == EntityTargetEvent.TargetReason.CUSTOM) return;
        if (event.getEntity().getPersistentDataContainer().has(slimeKey)) {
            String s = event.getEntity().getPersistentDataContainer().get(slimeKey, PersistentDataType.STRING);
            if (s == null) return;
            if (event.getTarget() == null) return;
            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(s));
            if (player.getUniqueId() == event.getTarget().getUniqueId()) {
                event.setTarget(lastHitEntity.get(getPlayerFromSlime(event.getEntity())));
                targetEntity.put(event.getEntity(), lastHitEntity.get(getPlayerFromSlime(event.getEntity())));
            }
        }
    }

    Map<Player, LivingEntity> lastHitEntity = new HashMap<>();
    Map<Entity, LivingEntity> targetEntity = new HashMap<>();

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager().getPersistentDataContainer().has(slimeKey)) {
            Player player = getPlayerFromSlime(event.getDamager());
            if (player == null) return;
            if (event.getEntity().getUniqueId() == player.getUniqueId()) event.setCancelled(true);
        }
        Player player;
        if (event.getDamager() instanceof Player p) player = p;
        else if (event.getDamager() instanceof Projectile projectile) {
            if (projectile.getShooter() instanceof Player p) player = p;
            else return;
        } else return;
        if (event.getEntity() instanceof LivingEntity entity) {
            lastHitEntity.put(player, entity);
        }
    }

    @EventHandler
    public void onEntityMove(EntityMoveEvent event) {
        if (event.getEntity().getType() == EntityType.SLIME) {
            if (event.getEntity().getPersistentDataContainer().has(slimeKey)) {
                Entity entity = targetEntity.get(event.getEntity());
                if (entity != null) {
                    if (entity.getLocation().distance(event.getEntity().getLocation()) <= 1) {
                        event.getEntity().attack(entity);
                    }
                }
            }
        }
    }

    @Override
    public Cooldowns.CooldownInfo getCooldownInfo() {
        return new Cooldowns.CooldownInfo(600, "split");
    }
}
