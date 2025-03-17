package com.starshootercity.originsmobs;

import com.starshootercity.originsmobs.abilities.*;
import com.starshootercity.OriginsAddon;
import com.starshootercity.abilities.Ability;
import com.starshootercity.util.Metrics;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OriginsMobs extends OriginsAddon {

    private static OriginsMobs instance;

    public static OriginsMobs getInstance() {
        return instance;
    }

    @Override
    public @NotNull String getNamespace() {
        return "moborigins";
    }

    @Override
    public @NotNull List<Ability> getAbilities() {
        return List.of(
                new SmallBug(),
                new SmallFox(),
                new LowerTotemChance(),
                new SnowTrail(),
                new StrongerSnowballs(),
                new BeeWings(),
                new Stinger(),
                new BecomesElderGuardian(),
                new WarpedFungusEater(),
                new WaterCombatant(),
                new QueenBee(),
                new Undead(),
                new Sly(),
                new TimidCreature(),
                new PillagerAligned(),
                new Illager(),
                new WitchParticles(),
                new MiningFatigueImmune(),
                new SmallWeak(),
                new SmallWeakKnockback(),
                new RideableCreature(),
                new GuardianAlly(),
                new SurfaceSlowness(),
                new SurfaceWeakness(),
                new GuardianSpikes(),
                new PrismarineSkin(),
                new CarefulGatherer(),
                new FrigidStrength(),
                new BetterBerries(),
                new WolfBody(),
                new AlphaWolf(),
                new ItemCollector(),
                new BetterPotions(),
                new ElderMagic(),
                new ElderSpikes(),
                new WaterVision(),
                new SummonFangs(),
                new FullMoon(),
                new FullMoonHealth(),
                new FullMoonAttack(),
                new WolfPack(),
                new WolfPackAttack(),
                new ZombieHunger(),
                Temperature.INSTANCE,
                new Overheat(),
                new Melting(),
                new MeltingSpeed(),
                new WolfHowl(),
                new TridentExpert(),
                new FlowerPower(),
                new Bouncy(),
                new LavaWalk(),
                new WaterBreathing(),
                new Split(),
                new PotionAction(),
                Dyeable.dyeable,
                new SlimyColor()
        );
    }
    private static MobsNMSInvoker nmsInvoker;

    private static void initializeNMSInvoker() {
        nmsInvoker = switch (Bukkit.getMinecraftVersion()) {
            case "1.19" -> new MobsNMSInvokerV1_19();
            case "1.19.1" -> new MobsNMSInvokerV1_19_1();
            case "1.19.2" -> new MobsNMSInvokerV1_19_2();
            case "1.19.3" -> new MobsNMSInvokerV1_19_3();
            case "1.19.4" -> new MobsNMSInvokerV1_19_4();
            case "1.20" -> new MobsNMSInvokerV1_20();
            case "1.20.1" -> new MobsNMSInvokerV1_20_1();
            case "1.20.2" -> new MobsNMSInvokerV1_20_2();
            case "1.20.3" -> new MobsNMSInvokerV1_20_3();
            case "1.20.4" -> new MobsNMSInvokerV1_20_4();
            case "1.20.5", "1.20.6" -> new MobsNMSInvokerV1_20_6();
            case "1.21" -> new MobsNMSInvokerV1_21();
            case "1.21.1" -> new MobsNMSInvokerV1_21_1();
            case "1.21.2", "1.21.3" -> new MobsNMSInvokerV1_21_3();
            default -> new MobsNMSInvokerV1_21_4();
        };
    }

    public static MobsNMSInvoker getNMSInvoker() {
        return nmsInvoker;
    }

    @Override
    public void onRegister() {
        instance = this;
        initializeNMSInvoker();

        int pluginId = 25122;
        new Metrics(this, pluginId);
    }
}