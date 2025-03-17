package com.starshootercity.originsmobs;

import com.destroystokyo.paper.entity.ai.Goal;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftMob;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class MobsNMSInvokerV1_21_4 extends MobsNMSInvoker {
    @Override
    public void dealThornsDamage(Entity target, int amount, Entity attacker) {
        net.minecraft.world.entity.Entity entity = ((CraftEntity) target).getHandle();
        entity.hurtServer((ServerLevel) entity.level(), entity.damageSources().thorns(((CraftEntity) attacker).getHandle()), amount);
    }

    @Override
    public @NotNull Attribute getAttackKnockbackAttribute() {
        return Attribute.ATTACK_KNOCKBACK;
    }

    @Override
    public @NotNull Enchantment getSmiteEnchantment() {
        return Enchantment.SMITE;
    }

    @Override
    public @NotNull Particle getElderGuardianParticle() {
        return Particle.ELDER_GUARDIAN;
    }

    @Override
    public @NotNull Particle getWitchParticle() {
        return Particle.WITCH;
    }

    @Override
    public void damageItem(ItemStack item, int amount, Player player) {
        item.damage(amount, player);
    }

    @Override
    public void startAutoSpinAttack(Player player, int duration, float riptideAttackDamage, ItemStack item) {
        ((CraftPlayer) player).getHandle().startAutoSpinAttack(duration, riptideAttackDamage, CraftItemStack.asNMSCopy(item));
    }

    @Override
    public void tridentMove(Player player) {
        ((CraftPlayer) player).getHandle().move(MoverType.SELF, new Vec3(0.0D, 1.1999999284744263D, 0.0D));
    }

    @Override
    public Goal<@NotNull Mob> getIronGolemAttackGoal(LivingEntity golem, Predicate<Player> hasAbility) {
        return new NearestAttackableTargetGoal<>(
                ((CraftMob) golem).getHandle(),
                net.minecraft.world.entity.player.Player.class,
                10,
                true,
                false,
                (livingEntity, serverLevel) -> {
                    if (livingEntity.getBukkitEntity() instanceof org.bukkit.entity.Player player) {
                        return hasAbility.test(player);
                    } else return false;
                }).asPaperVanillaGoal();
    }

    private final Map<Player, Vec3> lastVec3Map = new HashMap<>();

    @Override
    @SuppressWarnings("deprecation")
    public void bounce(Player player) {
        ServerPlayer p = ((CraftPlayer) player).getHandle();
        if (player.isOnGround()) {
            if (player.getFallDistance() <= 0) return;
            Vec3 dm = lastVec3Map.get(player);
            if (dm != null) {
                player.setVelocity(player.getVelocity().add(new Vector(0, -dm.y, 0)));
            }
        }
        lastVec3Map.put(player, p.getDeltaMovement());
    }
}