package xyz.verarr.customweathercycle.neoforge;

import net.neoforged.fml.common.Mod;

import xyz.verarr.customweathercycle.ExampleMod;

@Mod(ExampleMod.MOD_ID)
public final class ExampleModNeoForge {
    public ExampleModNeoForge() {
        // Run our common setup.
        ExampleMod.init();
    }
}
