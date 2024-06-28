package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AttributeModifierAbility;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WolfPack implements VisibleAbility, AttributeModifierAbility {
    @Override
    public @NotNull Attribute getAttribute() {
        return Attribute.GENERIC_MAX_HEALTH;
    }

    @Override
    public double getAmount() {
        return 0;
    }

    @Override
    public double getChangedAmount(Player player) {
        List<Entity> entities = player.getNearbyEntities(8, 8, 8);
        entities.removeIf(entity -> entity.getType() != EntityType.WOLF);
        return entities.size() >= 4 ? 0.04 : 0;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.ADD_NUMBER;
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:wolf_pack");
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("When you are near at least 4 wolves you gain speed and attack damage.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Wolf Pack", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }
}
