package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.AttributeModifierAbility;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsmobs.OriginsMobs;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class TimidCreature implements VisibleAbility, AttributeModifierAbility {
    @Override
    public @NotNull Attribute getAttribute() {
        return OriginsReborn.getNMSInvoker().getMovementSpeedAttribute();
    }

    @Override
    public double getAmount() {
        return 0;
    }

    @Override
    public double getChangedAmount(Player player) {
        double ran = getConfigOption(OriginsMobs.getInstance(), range, ConfigManager.SettingType.DOUBLE);
        List<Entity> entities = player.getNearbyEntities(ran, ran, ran);
        entities.removeIf(entity -> entity.getType() != EntityType.PLAYER);
        return entities.size() >= 3 ? 0.1 : 0;
    }

    private final String range = "range";

    @Override
    public void initialize() {
        registerConfigOption(OriginsMobs.getInstance(), range, Collections.singletonList("Range to check for players in"), ConfigManager.SettingType.DOUBLE, 8d);
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.ADD_NUMBER;
    }

    @Override
    public String description() {
        return "Your speed increases when you are around more than 3 other players.";
    }

    @Override
    public String title() {
        return "Timid Creature";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:timid_creature");
    }
}
