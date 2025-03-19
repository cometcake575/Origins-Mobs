package com.starshootercity.originsmobs.abilities;

import com.starshootercity.abilities.types.VisibleAbility;
import com.starshootercity.originsmobs.OriginsMobs;
import net.kyori.adventure.key.Key;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;

public class Bouncy implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "All blocks act like slime blocks.";
    }

    @Override
    public String title() {
        return "Bouncy";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:bouncy");
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        runForAbility(event.getPlayer(), player -> {
            if (player.isSneaking()) return;
            OriginsMobs.getNMSInvoker().bounce(event.getPlayer());
        });
    }
}
