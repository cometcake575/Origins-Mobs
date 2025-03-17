package com.starshootercity.originsmobs.abilities;

import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.cooldowns.CooldownAbility;
import com.starshootercity.cooldowns.Cooldowns;
import com.starshootercity.events.PlayerLeftClickEvent;
import com.starshootercity.originsmobs.OriginsMobs;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class SummonFangs implements VisibleAbility, Listener, CooldownAbility {
    @Override
    public String description() {
        return "You have the ability to summon fangs!";
    }

    @Override
    public String title() {
        return "Summon Fangs";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:summon_fangs");
    }

    @EventHandler
    public void onPlayerLeftClick(PlayerLeftClickEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR) return;
        runForAbility(event.getPlayer(), player -> {
            if (hasCooldown(player)) return;
            setCooldown(player);
            Location currentLoc = player.getLocation();
            for (int i = 0; i < getConfigOption(OriginsMobs.getInstance(), fangCount, ConfigManager.SettingType.INTEGER); i++) {
                currentLoc.add(currentLoc.getDirection().setY(0));
                if (!currentLoc.getBlock().getRelative(BlockFace.DOWN).isSolid()) continue;
                currentLoc.getWorld().spawnEntity(currentLoc, EntityType.EVOKER_FANGS).getPersistentDataContainer().set(sentFromPlayerKey, PersistentDataType.STRING, event.getPlayer().getUniqueId().toString());
            }
        });
    }

    private final String fangCount = "fang_count";

    @Override
    public void initialize() {
        registerConfigOption(OriginsMobs.getInstance(), fangCount, Collections.singletonList("The number of fangs to summon"), ConfigManager.SettingType.INTEGER, 16);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (event.getDamager().getPersistentDataContainer().getOrDefault(sentFromPlayerKey, PersistentDataType.STRING, "").equals(player.getUniqueId().toString())) event.setCancelled(true);
        }
    }

    private final NamespacedKey sentFromPlayerKey = new NamespacedKey(OriginsMobs.getInstance(), "sent-from-player");

    @Override
    public Cooldowns.CooldownInfo getCooldownInfo() {
        return new Cooldowns.CooldownInfo(600, "summon_fangs");
    }
}
