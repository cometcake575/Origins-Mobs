package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.originsmobs.OriginsMobs;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.events.PlayerLeftClickEvent;
import io.papermc.paper.event.entity.EntityMoveEvent;
import net.kyori.adventure.key.Key;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Split implements VisibleAbility, Listener {
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

    private final Map<Player, Integer> lastSplitTime = new HashMap<>();

    @EventHandler
    public void onPlayerLeftClick(PlayerLeftClickEvent event) {
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
            if (Bukkit.getCurrentTick() - lastSplitTime.getOrDefault(event.getPlayer(), Bukkit.getCurrentTick() - 600) < 600) return;
            lastSplitTime.put(event.getPlayer(), Bukkit.getCurrentTick());
            ServerPlayer player = ((CraftPlayer) event.getPlayer()).getHandle();
            player.hurt(player.damageSources().magic(), 8);
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
}
