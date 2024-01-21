package com.originsmobs.abilities;

import com.starshootercity.abilities.AttributeModifierAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MeltingSpeed implements AttributeModifierAbility {
    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:melting_speed");
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
        int temperature = Temperature.getTemperature(player);
        if (temperature >= 100) return -0.04;
        else if (temperature >= 50) return -0.02;
        else return 0;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.ADD_NUMBER;
    }
}
