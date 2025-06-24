package xyz.verarr.customweathercycle.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.verarr.customweathercycle.CustomWeatherCycle;
import xyz.verarr.customweathercycle.WeatherCycleManager;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    @WrapOperation(method = "tickWeather", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/intprovider/IntProvider;get(Lnet/minecraft/util/math/random/Random;)I", ordinal = 0))
    public int wrapThunderDuration(IntProvider instance, Random random, Operation<Integer> original) {
        CustomWeatherCycle.LOGGER.info("Redirecting thunder duration...");
        ServerWorld world = (ServerWorld) (Object) this;
        WeatherCycleManager manager = WeatherCycleManager.getInstance(world);

        if (manager.isNormalThunderDuration()) {
            int sample = original.call(instance, random);
            CustomWeatherCycle.LOGGER.info("Nevermind, Using vanilla: {}.", sample);
            return sample;
        };

        int sample = manager.sampleThunderDuration(random);
        CustomWeatherCycle.LOGGER.info("Got new duration {}.", sample);
        return sample;
    }

    @WrapOperation(method = "tickWeather", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/intprovider/IntProvider;get(Lnet/minecraft/util/math/random/Random;)I", ordinal = 1))
    public int wrapClearThunderDuration(IntProvider instance, Random random, Operation<Integer> original) {
        CustomWeatherCycle.LOGGER.info("Redirecting clear (thunder) duration...");
        ServerWorld world = (ServerWorld) (Object) this;
        WeatherCycleManager manager = WeatherCycleManager.getInstance(world);

        if (manager.isNormalClearDuration()) {
            int sample = original.call(instance, random);
            CustomWeatherCycle.LOGGER.info("Nevermind, Using vanilla: {}.", sample);
            return sample;
        };

        int sample = manager.sampleClearDuration(random);
        CustomWeatherCycle.LOGGER.info("Got new duration {}.", sample);
        return sample;
    }

    @WrapOperation(method = "tickWeather", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/intprovider/IntProvider;get(Lnet/minecraft/util/math/random/Random;)I", ordinal = 2))
    public int wrapRainDuration(IntProvider instance, Random random, Operation<Integer> original) {
        CustomWeatherCycle.LOGGER.info("Redirecting rain duration...");
        ServerWorld world = (ServerWorld) (Object) this;
        WeatherCycleManager manager = WeatherCycleManager.getInstance(world);

        if (manager.isNormalRainDuration()) {
            int sample = original.call(instance, random);
            CustomWeatherCycle.LOGGER.info("Nevermind, Using vanilla: {}.", sample);
            return sample;
        };

        int sample = manager.sampleRainDuration(random);
        CustomWeatherCycle.LOGGER.info("Got new duration {}.", sample);
        return sample;
    }

    @WrapOperation(method = "tickWeather", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/intprovider/IntProvider;get(Lnet/minecraft/util/math/random/Random;)I", ordinal = 3))
    public int wrapClearDuration(IntProvider instance, Random random, Operation<Integer> original) {
        CustomWeatherCycle.LOGGER.info("Redirecting clear duration...");
        ServerWorld world = (ServerWorld) (Object) this;
        WeatherCycleManager manager = WeatherCycleManager.getInstance(world);

        if (manager.isNormalClearDuration()) {
            int sample = original.call(instance, random);
            CustomWeatherCycle.LOGGER.info("Nevermind, Using vanilla: {}.", sample);
            return sample;
        };

        int sample = manager.sampleClearDuration(random);
        CustomWeatherCycle.LOGGER.info("Got new duration {}.", sample);
        return sample;
    }
}
