package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AttributeModifierAbility;
import com.starshootercity.abilities.VisibleAbility;
import io.papermc.paper.world.MoonPhase;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FullMoon implements VisibleAbility, AttributeModifierAbility {
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
        return !player.getWorld().isDayTime() && player.getWorld().getMoonPhase() == MoonPhase.FULL_MOON ? 0.07 : 0;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.ADD_NUMBER;
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:full_moon");
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("During a full moon you get Faster, Stronger, and Healthier.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Werewolf-like", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }
}
