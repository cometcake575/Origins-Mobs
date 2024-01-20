package com.starshootercity.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.OriginsMobs;
import com.starshootercity.events.PlayerLeftClickEvent;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SummonFangs implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You have the ability to summon fangs!", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Summon Fangs", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:summon_fangs");
    }

    private final Map<Player, Integer> lastUsedMagicTime = new HashMap<>();

    @EventHandler
    public void onPlayerLeftClick(PlayerLeftClickEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR) return;
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
            if (Bukkit.getCurrentTick() - lastUsedMagicTime.getOrDefault(event.getPlayer(), Bukkit.getCurrentTick() - 600) < 600) return;
            lastUsedMagicTime.put(event.getPlayer(), Bukkit.getCurrentTick());
            Location currentLoc = event.getPlayer().getLocation();
            for (int i = 0; i < 16; i++) {
                currentLoc.add(currentLoc.getDirection().setY(0));
                if (!currentLoc.getBlock().getRelative(BlockFace.DOWN).isSolid()) continue;
                currentLoc.getWorld().spawnEntity(currentLoc, EntityType.EVOKER_FANGS).getPersistentDataContainer().set(sentFromPlayerKey, PersistentDataType.STRING, event.getPlayer().getUniqueId().toString());
            }
        });
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (event.getDamager().getPersistentDataContainer().getOrDefault(sentFromPlayerKey, PersistentDataType.STRING, "").equals(player.getUniqueId().toString())) event.setCancelled(true);
        }
    }

    private final NamespacedKey sentFromPlayerKey = new NamespacedKey(OriginsMobs.getInstance(), "sent-from-player");
}
