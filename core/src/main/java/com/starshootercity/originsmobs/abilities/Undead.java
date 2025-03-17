package com.starshootercity.originsmobs.abilities;

import com.destroystokyo.paper.MaterialTags;
import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsmobs.OriginsMobs;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Undead implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "You are undead, and burn in the daylight. You also take more damage from smite.";
    }

    @Override
    public String title() {
        return "Undead";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:undead");
    }

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent ignored) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            runForAbility(p, player -> {
                        Block block = player.getWorld().getHighestBlockAt(player.getLocation());
                        while ((MaterialTags.GLASS.isTagged(block) || (MaterialTags.GLASS_PANES.isTagged(block)) && block.getY() >= player.getLocation().getY())) {
                            block = block.getRelative(BlockFace.DOWN);
                        }
                        boolean height = block.getY() < player.getLocation().getY();
                        String overworld = OriginsReborn.getInstance().getConfig().getString("worlds.world");
                        if (overworld == null) {
                            overworld = "world";
                            OriginsReborn.getInstance().getConfig().set("worlds.world", "world");
                            OriginsReborn.getInstance().saveConfig();
                        }
                        boolean isInOverworld = player.getWorld() == Bukkit.getWorld(overworld);
                        boolean day = player.getWorld().isDayTime();

                        if (!getConfigOption(OriginsReborn.getInstance(), burnWithHelmet, SettingType.BOOLEAN)) {
                            ItemStack helm = player.getInventory().getHelmet();
                            if (helm != null) {
                                if (!helm.getType().isAir()) return;
                            }
                        }

                        if (height && isInOverworld && day && !player.isInWaterOrRainOrBubbleColumn()) {
                            player.setFireTicks(Math.max(player.getFireTicks(), 60));
                        }
                    });
        }
    }

    private final String burnWithHelmet = "burn_with_helmet";

    @Override
    public void initialize() {
        registerConfigOption(OriginsReborn.getInstance(), burnWithHelmet, List.of("Whether the player should burn even when wearing a helmet"), SettingType.BOOLEAN, true);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        runForAbility(event.getEntity(), player -> {
            if (event.getDamager() instanceof LivingEntity entity) {
                int level = entity.getActiveItem().getEnchantmentLevel(OriginsMobs.getNMSInvoker().getSmiteEnchantment());
                event.setDamage(event.getDamage() + (2.5 * level));
            }
        });
    }
}
