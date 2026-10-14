package com.fortuneshears;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class Config {
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.IntValue WOOL_PER_LEVEL;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        WOOL_PER_LEVEL = builder
                .comment("Bonus wool scale per Fortune level. Bonus wool = this * (level + random 0..level). Set to 0 to disable the bonus.")
                .defineInRange("woolPerLevel", 1, 0, 16);
        SPEC = builder.build();
    }

    private Config() {
    }
}
