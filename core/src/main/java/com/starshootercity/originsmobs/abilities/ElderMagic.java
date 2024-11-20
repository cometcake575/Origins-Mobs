package com.starshootercity.originsmobs.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.cooldowns.CooldownAbility;
import com.starshootercity.cooldowns.Cooldowns;
import com.starshootercity.events.PlayerLeftClickEvent;
import com.starshootercity.originsmobs.OriginsMobs;
import net.kyori.adventure.key.Key;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ElderMagic implements VisibleAbility, Listener, CooldownAbility {
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

    @EventHandler
    public void onPlayerLeftClick(PlayerLeftClickEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR) return;
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
            if (hasCooldown(event.getPlayer())) return;
            setCooldown(event.getPlayer());
            for (Entity entity : event.getPlayer().getNearbyEntities(5, 5, 5)) {
                if (entity instanceof Player player) {
                    if (AbilityRegister.hasAbility(player, getKey())) return;
                    player.addPotionEffect(new PotionEffect(OriginsReborn.getNMSInvoker().getMiningFatigueEffect(), 600, 1, false, true));
                    player.spawnParticle(OriginsMobs.getNMSInvoker().getElderGuardianParticle(), player.getLocation(), 1);
                    player.playSound(player, Sound.ENTITY_ELDER_GUARDIAN_CURSE, SoundCategory.HOSTILE, 1, 1);
                }
            }
        });
    }

    @Override
    public Cooldowns.CooldownInfo getCooldownInfo() {
        return new Cooldowns.CooldownInfo(600, "elder_magic");
    }
}
