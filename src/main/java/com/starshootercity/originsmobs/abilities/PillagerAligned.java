package com.starshootercity.originsmobs.abilities;

import com.destroystokyo.paper.entity.ai.Goal;
import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftMob;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PillagerAligned implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("Villagers don't like you, and pillagers like you!", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Pillager Aligned", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:pillager_aligned");
    }

    @EventHandler
    public void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event) {
        if (event.getEntity().getType() == EntityType.PILLAGER) {
            AbilityRegister.runForAbility(event.getTarget(), getKey(), () -> event.setCancelled(true));
        }
    }

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Villager villager) {
            AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
                event.setCancelled(true);
                villager.shakeHead();
            });
        }
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof IronGolem golem) {
            fixGolem(golem);
        }
    }

    @EventHandler
    public void onEntitiesLoad(EntitiesLoadEvent event) {
        for (Entity entity : event.getEntities()) {
            if (entity instanceof IronGolem golem) {
                fixGolem(golem);
            }
        }
    }

    public void fixGolem(IronGolem golem) {
        Goal<Mob> goal = new NearestAttackableTargetGoal<>(
                ((CraftMob) golem).getHandle(),
                Player.class,
                10,
                true,
                false,
                livingEntity -> {
                    if (livingEntity.getBukkitEntity() instanceof org.bukkit.entity.Player player) {
                        return AbilityRegister.hasAbility(player, getKey());
                    } else return false;
                }).asPaperVanillaGoal();
        Bukkit.getMobGoals().addGoal(golem, 3, goal);
    }
}
