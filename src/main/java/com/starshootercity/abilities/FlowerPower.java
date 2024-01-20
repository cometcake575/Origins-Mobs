package com.starshootercity.abilities;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.OriginSwapper;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Tag;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FlowerPower implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("When near multiple flowers, you gain regeneration.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Flower Power", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:flower_power");
    }

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        if (event.getTickNumber() % 40 != 0) return;
        for (Player player : Bukkit.getOnlinePlayers()) {
            AbilityRegister.runForAbility(player, getKey(), () -> {
                List<Integer> points = List.of(-3, -2, -1, 0, 1, 2, 3);
                int num = 0;
                for (int x : points) {
                    for (int y : points) {
                        for (int z : points) {
                            Location loc = player.getLocation().clone().add(new Vector(x, y, z));
                            if (loc.distance(player.getLocation()) > 3) continue;
                            if (Tag.FLOWERS.isTagged(loc.getBlock().getType())) num += 1;
                        }
                    }
                }
                if (num >= 3) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 0, false, true));
                }
            });
        }
    }
}
