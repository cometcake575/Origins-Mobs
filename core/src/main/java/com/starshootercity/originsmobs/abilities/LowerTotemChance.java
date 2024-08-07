package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class LowerTotemChance implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("Totems have a 10% chance not to break on use.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Arcane Totems", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:lower_totem_chance");
    }

    @EventHandler
    public void onEntityResurrect(EntityResurrectEvent event) {
        if (event.getEntity() instanceof Player player) {
            AbilityRegister.runForAbility(player, getKey(), () -> {
                if (event.getEntity().getEquipment() == null) return;
                if (random.nextDouble() < 0.9) return;
                if (event.getEntity().getEquipment().getItemInMainHand().getType() == Material.TOTEM_OF_UNDYING) {
                    event.getEntity().getEquipment().setItemInMainHand(new ItemStack(Material.TOTEM_OF_UNDYING));
                } else {
                    event.getEntity().getEquipment().setItemInOffHand(new ItemStack(Material.TOTEM_OF_UNDYING));
                }
            });
        }
    }

    private final Random random = new Random();
}
