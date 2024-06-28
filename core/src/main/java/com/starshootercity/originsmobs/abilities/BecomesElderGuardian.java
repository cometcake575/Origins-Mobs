package com.starshootercity.originsmobs.abilities;

import com.starshootercity.AddonLoader;
import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.Ability;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.events.PlayerSwapOriginEvent;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

public class BecomesElderGuardian implements Ability, Listener {
    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:becomes_elder_guardian");
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getType() != EntityType.ELDER_GUARDIAN) return;
        Player player = event.getEntity().getKiller();
        if (player == null) return;
        AbilityRegister.runForAbility(player, getKey(), () -> {
            OriginSwapper.setOrigin(player, AddonLoader.originNameMap.get("elder guardian"), PlayerSwapOriginEvent.SwapReason.PLUGIN, false);
            player.sendMessage(Component.text("You have grown into an Elder Guardian!")
                    .color(NamedTextColor.YELLOW));
        });
    }
}
