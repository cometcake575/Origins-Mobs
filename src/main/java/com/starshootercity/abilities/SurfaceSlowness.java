package com.starshootercity.abilities;

import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class SurfaceSlowness implements AttributeModifierAbility, Listener {
    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:surface_slowness");
    }

    @Override
    public @NotNull Attribute getAttribute() {
        return Attribute.GENERIC_MOVEMENT_SPEED;
    }

    @Override
    public double getAmount() {
        return -0.4;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.MULTIPLY_SCALAR_1;
    }
}
