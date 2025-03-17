package com.starshootercity.originsmobs.abilities;

import com.starshootercity.abilities.SkinChangingAbility;
import com.starshootercity.originsmobs.OriginsMobs;
import net.kyori.adventure.key.Key;
import org.bukkit.*;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.image.BufferedImage;

public class SlimyColor implements SkinChangingAbility {

    private NamespacedKey key;

    @Override
    public void initialize() {
        key = new NamespacedKey(OriginsMobs.getInstance(), "color");
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:slimy_color");
    }

    @Override
    public void modifySkin(BufferedImage image, Player player) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                image.setRGB(x, y, slimify(image.getRGB(x, y), player));
            }
        }
    }

    public int slimify(int decimalRGB, Player player) {
        DyeColor color = getColor(player);

        int a = decimalRGB >> 24 & 0xFF;
        int r = (decimalRGB >> 16) & 0xFF;
        int g = (decimalRGB >> 8) & 0xFF;
        int b = decimalRGB & 0xFF;
        a = (int) (a * 0.6);

        if (color == null) {
            r = (int) (r * 0.6);
            b = (int) (b * 0.5);
            return a << 24 | (r << 16) | (g << 8) | b;
        }

        else return a << 24 | color.getColor().mixColors(Color.fromRGB(r, g, b)).asRGB();
    }

    public void setColor(Player player, @Nullable DyeColor color) {
        if (color == null) player.getPersistentDataContainer().remove(key);
        else player.getPersistentDataContainer().set(key, PersistentDataType.STRING, color.toString());
    }

    public @Nullable DyeColor getColor(Player player) {
        String color = player.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        if (color == null) return null;
        return DyeColor.valueOf(color.toUpperCase());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (!event.getAction().isRightClick()) return;

        if (event.getClickedBlock() == null ||
                (!event.getClickedBlock().getType().isInteractable() && !event.getClickedBlock().getType().equals(Material.WATER_CAULDRON))) {
            if (event.getItem() == null) return;
            DyeColor color = getColor(event.getItem().getType());
            if (color == null) return;

            runForAbility(event.getPlayer(), p -> Dyeable.dyeable.runForAbility(p, player -> {
                event.getPlayer().swingMainHand();
                event.getItem().setAmount(event.getItem().getAmount() - 1);
                if (getColor(player) == color) return;
                setColor(player, color);
                forceUpdate(player);
            }));
            return;
        }

        if (event.getClickedBlock() == null) return;
        if (event.hasItem()) return;
        if (!event.getAction().isRightClick()) return;
        if (event.getClickedBlock().getType().equals(Material.WATER_CAULDRON)) {
            if (event.getClickedBlock().getBlockData() instanceof Levelled levelled) {
                runForAbility(event.getPlayer(), player -> {
                    player.swingMainHand();
                    if (levelled.getLevel() - 1 < levelled.getMinimumLevel()) {
                        event.getClickedBlock().setType(Material.CAULDRON);
                    } else {
                        levelled.setLevel(levelled.getLevel() - 1);
                        event.getClickedBlock().setBlockData(levelled);
                    }

                    if (getColor(player) == null) return;
                    setColor(player, null);
                    forceUpdate(player);
                });
            }
        }
    }

    public DyeColor getColor(Material material) {
        return switch (material) {
            case BLUE_DYE -> DyeColor.BLUE;
            case BLACK_DYE -> DyeColor.BLACK;
            case BROWN_DYE -> DyeColor.BROWN;
            case CYAN_DYE -> DyeColor.CYAN;
            case GRAY_DYE -> DyeColor.GRAY;
            case GREEN_DYE -> DyeColor.GREEN;
            case LIGHT_GRAY_DYE -> DyeColor.LIGHT_GRAY;
            case LIGHT_BLUE_DYE -> DyeColor.LIGHT_BLUE;
            case LIME_DYE -> DyeColor.LIME;
            case ORANGE_DYE -> DyeColor.ORANGE;
            case MAGENTA_DYE -> DyeColor.MAGENTA;
            case PINK_DYE -> DyeColor.PINK;
            case PURPLE_DYE -> DyeColor.PURPLE;
            case RED_DYE -> DyeColor.RED;
            case WHITE_DYE -> DyeColor.WHITE;
            case YELLOW_DYE -> DyeColor.YELLOW;
            default -> null;
        };
    }
}
