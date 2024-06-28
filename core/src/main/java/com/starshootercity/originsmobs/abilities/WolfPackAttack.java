package com.starshootercity.originsmobs.abilities;

import com.starshootercity.abilities.AttributeModifierAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WolfPackAttack implements AttributeModifierAbility {
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
        return entities.size() >= 4 ? 2 : 0;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.ADD_NUMBER;
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:wolf_pack_attack");
    }
}
