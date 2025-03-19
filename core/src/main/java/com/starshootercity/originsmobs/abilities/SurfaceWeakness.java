package com.starshootercity.originsmobs.abilities;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class SurfaceWeakness implements VisibleAbility, Listener {

    @Override
    public String description() {
        return "You are weakened while on land.";
    }

    @Override
    public String title() {
        return "Surface Weakness";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:surface_weakness");
    }

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            runForAbility(p, player -> {
                if (!player.isInWater()) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, -1, 0, true, true));
                } else {
                    PotionEffect effect = player.getPotionEffect(PotionEffectType.WEAKNESS);
                    if (effect != null && effect.getDuration() == -1) player.removePotionEffect(PotionEffectType.WEAKNESS);
                }
            });
        }
    }
}
