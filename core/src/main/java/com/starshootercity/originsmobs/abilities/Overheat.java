package com.starshootercity.originsmobs.abilities;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.events.PlayerSwapOriginEvent;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.Tag;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

public class Overheat implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "You have a temperature bar that slowly begins to fill in hot biomes, and cool in other biomes.";
    }

    @Override
    public String title() {
        return "Overheat";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:overheat");
    }

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        if (event.getTickNumber() % 20 != 0) return;
        for (Player p : Bukkit.getOnlinePlayers()) {
            runForAbility(p, player -> {
                if (player.getLocation().getBlock().getTemperature() < 1 || Tag.ICE.isTagged(player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType())) {
                    Temperature.INSTANCE.setTemperature(player, Temperature.INSTANCE.getTemperature(player) - 1);
                } else {
                    Temperature.INSTANCE.setTemperature(player, Temperature.INSTANCE.getTemperature(player) + 1);
                }
            });
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> Temperature.INSTANCE.setTemperature(event.getPlayer(), 0));
    }

    @EventHandler
    public void onPlayerSwapOrigin(PlayerSwapOriginEvent event) {
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> Temperature.INSTANCE.setTemperature(event.getPlayer(), 0));
    }
}
