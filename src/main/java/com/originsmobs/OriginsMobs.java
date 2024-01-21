package com.originsmobs;

import com.originsmobs.abilities.*;
import com.originsmobs.abilities.WaterBreathing;
import com.starshootercity.OriginsAddon;
import com.starshootercity.abilities.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OriginsMobs extends OriginsAddon {
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
                new SummonFangs(),
                new FullMoon(),
                new FullMoonHealth(),
                new FullMoonAttack(),
                new WolfPack(),
                new WolfPackAttack(),
                new ZombieHunger(),
                new Temperature(),
                new Overheat(),
                new Melting(),
                new MeltingSpeed(),
                new WolfHowl(),
                new TridentExpert(),
                new FlowerPower(),
                new Bouncy(),
                new LavaWalk(),
                new WaterBreathing(),
                new Split()
        );
    }
}