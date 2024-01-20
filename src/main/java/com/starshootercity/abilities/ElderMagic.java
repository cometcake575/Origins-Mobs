package com.starshootercity.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.events.PlayerLeftClickEvent;
import net.kyori.adventure.key.Key;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElderMagic implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You can cast a spell on nearby players to slow down their mining speed.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Elder Magic", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:elder_magic");
    }

    private final Map<Player, Integer> lastUsedMagicTime = new HashMap<>();

    @EventHandler
    public void onPlayerLeftClick(PlayerLeftClickEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR) return;
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
            if (Bukkit.getCurrentTick() - lastUsedMagicTime.getOrDefault(event.getPlayer(), Bukkit.getCurrentTick() - 600) < 600) return;
            lastUsedMagicTime.put(event.getPlayer(), Bukkit.getCurrentTick());
            for (Entity entity : event.getPlayer().getNearbyEntities(5, 5, 5)) {
                if (entity instanceof Player player) {
                    if (AbilityRegister.hasAbility(player, getKey())) return;
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 600, 1, false, true));
                    player.spawnParticle(Particle.MOB_APPEARANCE, player.getLocation(), 1);
                    player.playSound(player, Sound.ENTITY_ELDER_GUARDIAN_CURSE, SoundCategory.HOSTILE, 1, 1);
                }
            }
        });
    }
}
