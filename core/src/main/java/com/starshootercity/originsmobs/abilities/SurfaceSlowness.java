package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.types.AttributeModifierAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class SurfaceSlowness implements AttributeModifierAbility, Listener {
    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:surface_slowness");
    }

    @Override
    public @NotNull Attribute getAttribute() {
        return OriginsReborn.getNMSInvoker().getMovementSpeedAttribute();
    }

    @Override
    public double getAmount(Player player) {
        return -0.4;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.MULTIPLY_SCALAR_1;
    }
}
