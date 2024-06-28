package com.starshootercity.originsmobs;

import com.destroystokyo.paper.entity.ai.Goal;
import org.bukkit.Particle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public abstract class MobsNMSInvoker {
    public abstract void dealThornsDamage(Entity target, int amount, Entity attacker);

    public abstract @NotNull Enchantment getSmiteEnchantment();

    public abstract @NotNull Particle getElderGuardianParticle();

    public abstract @NotNull Particle getWitchParticle();

    public abstract void damageItem(ItemStack item, int amount, Player player);

    public abstract void startAutoSpinAttack(Player player, int duration, float riptideAttackDamage, ItemStack item);

    public abstract void tridentMove(Player player);

    public abstract Goal<Mob> getIronGolemAttackGoal(LivingEntity golem, Predicate<Player> hasAbility);

    public abstract void bounce(Player player);
}