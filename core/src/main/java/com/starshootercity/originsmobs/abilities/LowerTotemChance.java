package com.starshootercity.originsmobs.abilities;

import com.starshootercity.abilities.types.VisibleAbility;
import com.starshootercity.originsmobs.OriginsMobs;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Random;

public class LowerTotemChance implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "Totems have a %s% chance not to break on use.";
    }

    @Override
    public String title() {
        return "Arcane Totems";
    }

    @Override
    public String modifyDescription(String description) {
        int am = (int) (getConfigOption(OriginsMobs.getInstance(), noBreakChance, ConfigManager.SettingType.FLOAT) * 100);
        return description.replace("%s", String.valueOf(am));
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:lower_totem_chance");
    }

    @EventHandler
    public void onEntityResurrect(EntityResurrectEvent event) {
        if (event.getEntity() instanceof Player p) {
            runForAbility(p, player -> {
                if (event.getEntity().getEquipment() == null) return;
                if (random.nextDouble() > getConfigOption(OriginsMobs.getInstance(), noBreakChance, ConfigManager.SettingType.FLOAT)) return;
                if (event.getEntity().getEquipment().getItemInMainHand().getType() == Material.TOTEM_OF_UNDYING) {
                    event.getEntity().getEquipment().setItemInMainHand(new ItemStack(Material.TOTEM_OF_UNDYING));
                } else {
                    event.getEntity().getEquipment().setItemInOffHand(new ItemStack(Material.TOTEM_OF_UNDYING));
                }
            });
        }
    }

    private final Random random = new Random();

    private final String noBreakChance = "no_break_chance";

    @Override
    public void initialize(JavaPlugin plugin) {
        registerConfigOption(OriginsMobs.getInstance(), noBreakChance, Collections.singletonList("The chance a totem won't break (between 0 and 1)"), ConfigManager.SettingType.FLOAT, 0.1f);
    }
}
