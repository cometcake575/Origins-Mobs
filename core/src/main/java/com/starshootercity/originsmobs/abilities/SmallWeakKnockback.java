package com.starshootercity.originsmobs.abilities;

import com.starshootercity.abilities.types.AttributeModifierAbility;
import com.starshootercity.originsmobs.OriginsMobs;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class SmallWeakKnockback implements Listener, AttributeModifierAbility {
    @Override
    public @NotNull Attribute getAttribute() {
        return OriginsMobs.getNMSInvoker().getAttackKnockbackAttribute();
    }

    @Override
    public double getAmount(Player player) {
        return player.getHealth() <= 4 ? 2.5 : 0;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.ADD_NUMBER;
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:small_weak_knockback");
    }
}
