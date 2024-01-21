package com.starshootercity.originsmobs.abilities;

import com.destroystokyo.paper.MaterialTags;
import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.OriginSwapper;
import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Undead implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You are undead, and burn in the daylight. You also take more damage from smite.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Undead", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:undead");
    }

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent ignored) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            AbilityRegister.runForAbility(player, getKey(),
                    () -> {
                        Block block = player.getWorld().getHighestBlockAt(player.getLocation());
                        while ((MaterialTags.GLASS.isTagged(block) || (MaterialTags.GLASS_PANES.isTagged(block)) && block.getY() >= player.getY())) {
                            block = block.getRelative(BlockFace.DOWN);
                        }
                        boolean height = block.getY() < player.getY();
                        String overworld = OriginsReborn.getInstance().getConfig().getString("worlds.world");
                        if (overworld == null) {
                            overworld = "world";
                            OriginsReborn.getInstance().getConfig().set("worlds.world", "world");
                            OriginsReborn.getInstance().saveConfig();
                        }
                        boolean isInOverworld = player.getWorld() == Bukkit.getWorld(overworld);
                        boolean day = player.getWorld().isDayTime();
                        if (height && isInOverworld && day && !player.isInWaterOrRainOrBubbleColumn()) {
                            player.setFireTicks(Math.max(player.getFireTicks(), 60));
                        }
                    });
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        AbilityRegister.runForAbility(event.getEntity(), getKey(), () -> {
            if (event.getDamager() instanceof LivingEntity entity) {
                int level = entity.getActiveItem().getEnchantmentLevel(Enchantment.DAMAGE_UNDEAD);
                event.setDamage(event.getDamage() + (2.5 * level));
            }
        });
    }
}
