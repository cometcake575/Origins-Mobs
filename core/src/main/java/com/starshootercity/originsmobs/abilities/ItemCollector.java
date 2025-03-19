package com.starshootercity.originsmobs.abilities;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.abilities.types.VisibleAbility;
import com.starshootercity.originsmobs.OriginsMobs;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ItemCollector implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "You have a larger item pickup radius.";
    }

    @Override
    public String title() {
        return "Item Collector";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:item_collector");
    }

    private void pickUpItem(Player player, Item item) {
        EntityPickupItemEvent pickupItemEvent = new EntityPickupItemEvent(player, item, 0);
        if (!pickupItemEvent.callEvent()) return;
        Map<Integer, ItemStack> remainingItems = player.getInventory().addItem(item.getItemStack());
        if (remainingItems.isEmpty()) {
            player.playPickupItemAnimation(item);
            item.remove();
        } else {
            for (Integer i : remainingItems.keySet()) {
                int remaining = item.getItemStack().getAmount() - remainingItems.get(i).getAmount();
                player.playPickupItemAnimation(item, remaining);
                item.setItemStack(remainingItems.get(i));
            }
        }
    }

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            runForAbility(p, player -> {
                double rad = getConfigOption(OriginsMobs.getInstance(), radius, ConfigManager.SettingType.DOUBLE);
                List<Entity> entities = player.getNearbyEntities(rad, rad, rad);
                for (Entity entity : entities) {
                    if (entity instanceof Item item) {
                        if (!item.canPlayerPickup()) continue;
                        if (item.getPickupDelay() > 0) continue;
                        pickUpItem(player, item);
                    }
                }
            });
        }
    }

    private final String radius = "radius";

    @Override
    public void initialize(JavaPlugin plugin) {
        registerConfigOption(OriginsMobs.getInstance(), radius, Collections.singletonList("Increased pickup range radius"), ConfigManager.SettingType.DOUBLE, 2.5);
    }
}
