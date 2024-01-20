package com.starshootercity.abilities.impossible;

import com.starshootercity.abilities.Ability;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public class StriderShake implements Ability {
    @Override
    public @NotNull Key getKey() {
        return Key.key("originsmobs:strider_shake");
    }
}
