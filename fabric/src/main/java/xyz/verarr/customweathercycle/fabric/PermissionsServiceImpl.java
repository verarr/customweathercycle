package xyz.verarr.customweathercycle.fabric;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.command.CommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;
import xyz.verarr.customweathercycle.PermissionsService;

public class PermissionsServiceImpl extends PermissionsService {
    public static boolean hasPermission(@NotNull ServerPlayerEntity player,
                                        @NotNull String permission,
                                        int defaultPermissionLevel) {
        String permissionNode = String.format("%s.%s", PERMISSION_ROOT, permission);
        return Permissions.check(player, permissionNode, defaultPermissionLevel);
    }

    public static boolean hasPermission(@NotNull CommandSource commandSource,
                                        @NotNull String permission,
                                        int defaultPermissionLevel) {
        String permissionNode = String.format("%s.%s", PERMISSION_ROOT, permission);
        return Permissions.check(commandSource, permissionNode, defaultPermissionLevel);
    }
}