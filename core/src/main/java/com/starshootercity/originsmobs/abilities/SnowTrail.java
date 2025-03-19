package com.starshootercity.originsmobs.abilities;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

public class SnowTrail implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "You leave a trail of snow.";
    }

    @Override
    public String title() {
        return "Snow Trail";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:snow_trail");
    }

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            runForAbility(p, player -> {
                if (player.getLocation().getBlock().getType() == Material.AIR) {
                    if (!player.getLocation().getBlock().canPlace(Material.SNOW.createBlockData())) return;
                    BlockPlaceEvent blockPlaceEvent = new BlockPlaceEvent(player.getLocation().getBlock(), player.getLocation().getBlock().getState(), player.getLocation().getBlock(), player.getInventory().getItemInMainHand(), player, true, EquipmentSlot.HAND);
                    if (!blockPlaceEvent.callEvent()) return;
                    player.getLocation().getBlock().setType(Material.SNOW);
                }
            });
        }
    }
}
