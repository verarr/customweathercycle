package xyz.verarr.customweathercycle.neoforge;

import net.neoforged.fml.common.Mod;

import xyz.verarr.customweathercycle.CustomWeatherCycle;

@Mod(CustomWeatherCycle.MOD_ID)
public final class CustomWeatherCycleNeoForge {
    public CustomWeatherCycleNeoForge() {
        // Run our common setup.
        CustomWeatherCycle.init();
    }
}
