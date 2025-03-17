package com.starshootercity.originsmobs.abilities;

import com.starshootercity.abilities.Ability;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public class Dyeable implements Ability {

    public static Dyeable dyeable = new Dyeable();

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:dyeable");
    }
}
