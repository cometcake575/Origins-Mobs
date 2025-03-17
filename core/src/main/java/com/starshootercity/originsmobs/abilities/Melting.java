package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.AttributeModifierAbility;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Melting implements VisibleAbility, AttributeModifierAbility {
    @Override
    public String description() {
        return "As your temperature bar fills up, you'll slowly begin to melt in hot biomes, losing health and speed.";
    }

    @Override
    public String title() {
        return "Melting";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:melting");
    }

    @Override
    public @NotNull Attribute getAttribute() {
        return OriginsReborn.getNMSInvoker().getMaxHealthAttribute();
    }

    @Override
    public double getAmount() {
        return 0;
    }

    @Override
    public double getChangedAmount(Player player) {
        int temperature = Temperature.INSTANCE.getTemperature(player);
        if (temperature >= 100) return -8;
        else if (temperature >= 50) return -4;
        else return 0;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.ADD_NUMBER;
    }
}
