package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarpedFungusEater implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You can eat warped fungus to recover some hunger, along with a small speed boost.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Fungus Hunger", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:warped_fungus_eater");
    }

    private final Map<Player, Integer> lastInteractedTicks = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
            if (lastInteractedTicks.getOrDefault(event.getPlayer(), -1) == Bukkit.getCurrentTick()) return;
            lastInteractedTicks.put(event.getPlayer(), Bukkit.getCurrentTick());
            if (event.getAction().isRightClick()) {
                if (event.getItem() == null) return;
                if (event.getItem().getType() == Material.WARPED_FUNGUS) {
                    if (event.getHand() != null) {
                        event.getPlayer().swingHand(event.getHand());
                    }
                    event.getItem().setAmount(event.getItem().getAmount() - 1);
                    event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 0, false, true));
                    event.getPlayer().setFoodLevel(Math.min(event.getPlayer().getFoodLevel() + 1, 20));
                    event.getPlayer().setSaturation(Math.min(event.getPlayer().getSaturation() + 1, event.getPlayer().getFoodLevel()));
                }
            }
        });
    }
}
