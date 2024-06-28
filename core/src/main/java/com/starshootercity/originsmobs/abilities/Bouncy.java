package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsmobs.OriginsMobs;
import net.kyori.adventure.key.Key;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Bouncy implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("All blocks act like slime blocks.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Bouncy", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:bouncy");
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
            if (event.getPlayer().isSneaking()) return;
            OriginsMobs.getNMSInvoker().bounce(event.getPlayer());
        });
    }
}
