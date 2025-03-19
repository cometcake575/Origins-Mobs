package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.types.AttributeModifierAbility;
import com.starshootercity.abilities.types.BreakSpeedModifierAbility;
import com.starshootercity.abilities.types.FlightAllowingAbility;
import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class LavaWalk implements VisibleAbility, FlightAllowingAbility, AttributeModifierAbility, BreakSpeedModifierAbility {

    // If someone can find a better way to implement this please tell me

    @Override
    public boolean canFly(Player player) {
        if (player.isInLava()) {
            if (player.getAllowFlight()) player.setFlying(true);
            if (!player.isSneaking()) {
                double num = player.getLocation().getY() - Math.floor(player.getLocation().getY());
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
    public String description() {
        return "You have the ability to walk on lava source blocks! You are also quicker while walking on lava, and slower on land.";
    }

    @Override
    public String title() {
        return "Lava Walker";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:lava_walk");
    }

    @Override
    public @NotNull Attribute getAttribute() {
        return OriginsReborn.getNMSInvoker().getMovementSpeedAttribute();
    }

    @Override
    public double getAmount(Player player) {
        return player.isInLava() ? 0.145 : 0;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.ADD_NUMBER;
    }

    @Override
    public BlockMiningContext getMiningContext(Player player) {
        return new BlockMiningContext(
                player.getInventory().getItemInMainHand(),
                player.getPotionEffect(OriginsReborn.getNMSInvoker().getMiningFatigueEffect()),
                player.getPotionEffect(OriginsReborn.getNMSInvoker().getHasteEffect()),
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
