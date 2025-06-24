package xyz.verarr.customweathercycle;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class PermissionsService {
    public static final String PERMISSION_ROOT = CustomWeatherCycle.MOD_ID;

    @ExpectPlatform
    public static boolean hasPermission(@NotNull ServerPlayerEntity player, @NotNull String permission, int defaultPermissionLevel) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean hasPermission(@NotNull CommandSource commandSource, @NotNull String permission, int defaultPermissionLevel) {
        throw new AssertionError();
    }

    public static boolean sourceHasPermission(@NotNull ServerCommandSource source, @NotNull String permission, int defaultPermissionLevel) {
            if (source.getPlayer() != null) {
                return hasPermission(source.getPlayer(), permission, defaultPermissionLevel);
            } else {
                return hasPermission(source, permission, defaultPermissionLevel);
            }
    }
}
