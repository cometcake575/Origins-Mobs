package com.starshootercity.originsmobs.abilities;

import com.destroystokyo.paper.entity.ai.Goal;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsmobs.OriginsMobs;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class PillagerAligned implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "Villagers don't like you, and pillagers like you!";
    }

    @Override
    public String title() {
        return "Pillager Aligned";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:pillager_aligned");
    }

    @EventHandler
    public void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event) {
        if (event.getEntity().getType() == EntityType.PILLAGER) {
            if (event.getTarget() == null) return;
            runForAbility(event.getTarget(), player -> event.setCancelled(true));
        }
    }

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Villager villager) {
            runForAbility(event.getPlayer(), player -> {
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
        if (!getConfigOption(OriginsMobs.getInstance(), golemsAttack, ConfigManager.SettingType.BOOLEAN)) return;
        Goal<Mob> goal = OriginsMobs.getNMSInvoker().getIronGolemAttackGoal(golem, this::hasAbility);
        Bukkit.getMobGoals().addGoal(golem, 3, goal);
    }

    private final String golemsAttack = "golems_attack";

    @Override
    public void initialize() {
        registerConfigOption(OriginsMobs.getInstance(), golemsAttack, Collections.singletonList("Whether Iron Golems should attack the player"), ConfigManager.SettingType.BOOLEAN, true);
    }
}
