package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.types.AttributeModifierAbility;
import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class SmallWeak implements VisibleAbility, Listener, AttributeModifierAbility {
    @Override
    public @NotNull Attribute getAttribute() {
        return OriginsReborn.getNMSInvoker().getAttackDamageAttribute();
    }

    @Override
    public double getAmount(Player player) {
        return player.getHealth() <= 4 ? -0.95 : 0;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.MULTIPLY_SCALAR_1;
    }

    @Override
    public String description() {
        return "When at less than 2 hearts, you deal almost no damage, but your attacks have stronger knockback!";
    }

    @Override
    public String title() {
        return "Small Weakness";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:small_weak");
    }
}
