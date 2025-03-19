package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.types.AttributeModifierAbility;
import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PrismarineSkin implements VisibleAbility, AttributeModifierAbility {
    @Override
    public @NotNull Attribute getAttribute() {
        return OriginsReborn.getNMSInvoker().getArmorAttribute();
    }

    @Override
    public double getAmount(Player player) {
        return 2;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.ADD_NUMBER;
    }

    @Override
    public String description() {
        return "Your skin is made of prismarine, and you get natural armor from it.";
    }

    @Override
    public String title() {
        return "Prismarine Skin";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:prismarine_skin");
    }
}
