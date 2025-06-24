package xyz.verarr.customweathercycle;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.PersistentState;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class WeatherCycleManager extends PersistentState {
    private static final Range DEFAULT_CLEAR_DURATION = new Range(12000, 180000);
    private static final Range DEFAULT_RAIN_DURATION = new Range(12000, 24000);
    private static final Range DEFAULT_THUNDER_DURATION = new Range(3600, 15600);
    public static Type<WeatherCycleManager> type = new Type<>(WeatherCycleManager::new, WeatherCycleManager::createFromNbt, null);

    private Range clearDuration = DEFAULT_CLEAR_DURATION;
    private IntProvider clearDurationProvider = UniformIntProvider.create(clearDuration.min, clearDuration.max);
    private Range rainDuration = DEFAULT_RAIN_DURATION;
    private IntProvider rainDurationProvider = UniformIntProvider.create(rainDuration.min, rainDuration.max);
    private Range thunderDuration = DEFAULT_THUNDER_DURATION;
    private IntProvider thunderDurationProvider = UniformIntProvider.create(thunderDuration.min, thunderDuration.max);

    public boolean resetWeatherAfterSleeping = true;
    public boolean turnThunderIntoRainAfterSleeping = true;

    public static @NotNull WeatherCycleManager createFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        WeatherCycleManager manager = new WeatherCycleManager();
        NbtCompound clear = tag.getCompound("clear");
        manager.setClearDuration(clear.getInt("min"), clear.getInt("max"));
        NbtCompound rain = tag.getCompound("rain");
        manager.setRainDuration(rain.getInt("min"), rain.getInt("max"));
        NbtCompound thunder = tag.getCompound("thunder");
        manager.setThunderDuration(thunder.getInt("min"), thunder.getInt("max"));
        NbtCompound options = tag.getCompound("options");
        manager.resetWeatherAfterSleeping = options.getBoolean("resetWeatherAfterSleeping");
        manager.turnThunderIntoRainAfterSleeping = options.getBoolean("turnThunderIntoRainAfterSleeping");
        return manager;
    }

    public static @NotNull WeatherCycleManager getInstance(@NotNull ServerWorld world) {
        WeatherCycleManager manager = world.getPersistentStateManager().getOrCreate(type, CustomWeatherCycle.MOD_ID);
        manager.markDirty();
        return manager;
    }

    public void setClearDuration(Integer min, Integer max) {
        clearDuration = clearDuration.update(min, max);
        clearDurationProvider = UniformIntProvider.create(clearDuration.min, clearDuration.max);
    }

    public void setRainDuration(Integer min, Integer max) {
        rainDuration = rainDuration.update(min, max);
        rainDurationProvider = UniformIntProvider.create(rainDuration.min, rainDuration.max);
    }

    public void setThunderDuration(Integer min, Integer max) {
        thunderDuration = thunderDuration.update(min, max);
        thunderDurationProvider = UniformIntProvider.create(thunderDuration.min, thunderDuration.max);
    }

    public int sampleClearDuration(Random random) {
        return clearDurationProvider.get(random);
    }

    public int sampleRainDuration(Random random) {
        return rainDurationProvider.get(random);
    }

    public int sampleThunderDuration(Random random) {
        return thunderDurationProvider.get(random);
    }

    public boolean isNormalWeatherCycle() {
        return isNormalClearDuration() && isNormalRainDuration() && isNormalThunderDuration();
    }

    public boolean isNormalClearDuration() {
        return DEFAULT_CLEAR_DURATION.equals(clearDuration);
    }

    public boolean isNormalRainDuration() {
        return DEFAULT_RAIN_DURATION.equals(rainDuration);
    }

    public boolean isNormalThunderDuration() {
        return DEFAULT_THUNDER_DURATION.equals(thunderDuration);
    }

    @Override
    public NbtCompound writeNbt(@NotNull NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        NbtCompound clear = new NbtCompound();
        clear.putInt("min", clearDuration.min);
        clear.putInt("max", clearDuration.max);

        NbtCompound rain = new NbtCompound();
        rain.putInt("min", rainDuration.min);
        rain.putInt("max", rainDuration.max);

        NbtCompound thunder = new NbtCompound();
        thunder.putInt("min", thunderDuration.min);
        thunder.putInt("max", thunderDuration.max);

        NbtCompound options = new NbtCompound();
        options.putBoolean("resetWeatherAfterSleeping", resetWeatherAfterSleeping);
        options.putBoolean("turnThunderIntoRainAfterSleeping", turnThunderIntoRainAfterSleeping);

        nbt.put("clear", clear);
        nbt.put("rain", rain);
        nbt.put("thunder", thunder);
        nbt.put("options", options);
        return nbt;
    }

    private record Range(Integer min, Integer max) {
        public Range update(Integer min, Integer max) {
            return new Range(Objects.requireNonNullElse(min, this.min), Objects.requireNonNullElse(max, this.max));
        }
    }
}
