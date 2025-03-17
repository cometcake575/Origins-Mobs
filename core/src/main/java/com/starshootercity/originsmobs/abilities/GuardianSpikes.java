package com.starshootercity.originsmobs.abilities;

import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsmobs.OriginsMobs;
import net.kyori.adventure.key.Key;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Random;

public class GuardianSpikes implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "Spikes that have a chance to damage attackers!";
    }

    @Override
    public String title() {
        return "Guardian Spikes";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:guardian_spikes");
    }

    private final Random random = new Random();

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        runForAbility(event.getEntity(), player -> {
            if (random.nextDouble() > getConfigOption(OriginsMobs.getInstance(), thornsChance, SettingType.FLOAT)) return;
            OriginsMobs.getNMSInvoker().dealThornsDamage(event.getDamager(), 2, player);
        });
    }

    private final String thornsChance = "thorns_chance";

    @Override
    public void initialize() {
        registerConfigOption(OriginsMobs.getInstance(), thornsChance, Collections.singletonList("Chance for thorns to damage attackers"), SettingType.FLOAT, 0.75f);
    }
}
