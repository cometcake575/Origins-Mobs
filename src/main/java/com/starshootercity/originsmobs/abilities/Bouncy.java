package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private final Map<Player, Vec3> lastVec3Map = new HashMap<>();

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onPlayerMove(PlayerMoveEvent event) {
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
            if (event.getPlayer().isSneaking()) return;
            ServerPlayer player = ((CraftPlayer) event.getPlayer()).getHandle();
            if (event.getPlayer().isOnGround()) {
                if (event.getPlayer().getFallDistance() <= 0) return;
                Vec3 dm = lastVec3Map.get(event.getPlayer());
                if (dm != null) {
                    event.getPlayer().setVelocity(event.getPlayer().getVelocity().add(new Vector(0, -dm.y, 0)));
                }
            }
            lastVec3Map.put(event.getPlayer(), player.getDeltaMovement());
        });
    }
}
