package com.fortuneshears;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;

@Mod(FortuneShears.MODID)
public class FortuneShears {
    public static final String MODID = "fortuneshears";

    public FortuneShears(IEventBus modBus, ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        NeoForge.EVENT_BUS.addListener(ShearHandler::onEntityInteract);
    }
}
