package com.starshootercity.originsmobs.abilities;

import com.starshootercity.abilities.AttributeModifierAbility;
import io.papermc.paper.world.MoonPhase;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FullMoonAttack implements AttributeModifierAbility {
    @Override
    public @NotNull Attribute getAttribute() {
        return Attribute.GENERIC_ATTACK_DAMAGE;
    }

    @Override
    public double getAmount() {
        return 0;
    }

    @Override
    public double getChangedAmount(Player player) {
        return !player.getWorld().isDayTime() && player.getWorld().getMoonPhase() == MoonPhase.FULL_MOON ? 2 : 0;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.ADD_NUMBER;
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:full_moon_attack");
    }
}
