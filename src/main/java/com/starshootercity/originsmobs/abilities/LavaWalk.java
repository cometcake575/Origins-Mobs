package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AttributeModifierAbility;
import com.starshootercity.abilities.BreakSpeedModifierAbility;
import com.starshootercity.abilities.FlightAllowingAbility;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LavaWalk implements VisibleAbility, FlightAllowingAbility, AttributeModifierAbility, BreakSpeedModifierAbility {
    @Override
    public boolean canFly(Player player) {
        if (player.isInLava()) {
            if (player.getAllowFlight()) player.setFlying(true);
            if (!player.isSneaking()) {
                double num = player.getLocation().y() - Math.floor(player.getLocation().y());
                if (player.getLocation().getBlock().getRelative(BlockFace.UP).getType() == Material.LAVA || num + 0.1 < 0.65) {
                    player.teleport(player.getLocation().add(0, 0.1, 0));

                } else if (0.65 - num > 0.04) {
                    Location loc = player.getLocation().clone();
                    loc.setY(Math.floor(loc.getY()) + 0.65);
                    player.teleport(loc);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public float getFlightSpeed(Player player) {
        return 0.1f;
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You have the ability to walk on lava source blocks! You are also quicker while walking on lava, and slower on land.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Lava Walker", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:lava_walk");
    }

    @Override
    public @NotNull Attribute getAttribute() {
        return Attribute.GENERIC_MOVEMENT_SPEED;
    }

    @Override
    public double getAmount() {
        return 0;
    }

    @Override
    public double getChangedAmount(Player player) {
        return player.isInLava() ? 0.145 : 0;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.ADD_NUMBER;
    }

    @Override
    public BlockMiningContext provideContextFor(Player player) {
        return new BlockMiningContext(
                player.getInventory().getItemInMainHand(),
                player.getPotionEffect(PotionEffectType.SLOW_DIGGING),
                player.getPotionEffect(PotionEffectType.FAST_DIGGING),
                player.getPotionEffect(PotionEffectType.CONDUIT_POWER),
                false,
                false,
                true
        );
    }

    @Override
    public boolean shouldActivate(Player player) {
        return player.isInLava();
    }
}
