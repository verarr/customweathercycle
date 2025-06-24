package xyz.verarr.customweathercycle.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.verarr.customweathercycle.CustomWeatherCycle;
import xyz.verarr.customweathercycle.WeatherCycleManager;

@Mixin(ServerWorld.class)
public class ServerWorldSleepMixin {
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;resetWeather()V"))
    public void shouldResetWeather(ServerWorld instance, Operation<Void> original) {
        WeatherCycleManager manager = WeatherCycleManager.getInstance(instance);

        if (manager.resetWeatherAfterSleeping) {
            original.call(instance);
            return;
        }

        CustomWeatherCycle.LOGGER.info("Not resetting weather");

        if (instance.isThundering() && manager.turnThunderIntoRainAfterSleeping) {
            CustomWeatherCycle.LOGGER.info("Turning thunder into rain");
            var random = instance.getRandom();
            instance.setWeather(0, manager.sampleRainDuration(random), true, false);
        }
    }
}
