package xyz.verarr.customweathercycle;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.argument.TimeArgumentType;
import net.minecraft.server.command.ServerCommandSource;

import java.util.function.Predicate;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;
import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

public class WeatherCycleCommand {
    private static final Predicate<ServerCommandSource> ROOT_REQUIREMENT = source -> PermissionsService.sourceHasPermission(source, "command", 2);
    private static final Predicate<ServerCommandSource> MODIFY_REQUIREMENT = source -> PermissionsService.sourceHasPermission(source, "command.modify",2);

    private static final LiteralArgumentBuilder<ServerCommandSource> commandLiteral = literal("weathercycle");
    public static final LiteralArgumentBuilder<ServerCommandSource> command = commandLiteral
            .requires(ROOT_REQUIREMENT)
            .then(ModifyClearCommand.command.requires(MODIFY_REQUIREMENT))
            .then(ModifyRainCommand.command.requires(MODIFY_REQUIREMENT))
            .then(ModifyThunderCommand.command.requires(MODIFY_REQUIREMENT));

    private static class ModifyClearCommand {
        private static final RequiredArgumentBuilder<ServerCommandSource, Integer> maxArgument = argument("max", TimeArgumentType.time(0));
        private static final RequiredArgumentBuilder<ServerCommandSource, Integer> minArgument = argument("min", TimeArgumentType.time(0));
        private static final LiteralArgumentBuilder<ServerCommandSource> literalCommand = literal("clear");
        public static final LiteralArgumentBuilder<ServerCommandSource> command = literalCommand.then(minArgument.then(maxArgument.executes(ModifyClearCommand::execute)));

        private static int execute(CommandContext<ServerCommandSource> ctx) {
            int min = IntegerArgumentType.getInteger(ctx, "min");
            int max = IntegerArgumentType.getInteger(ctx, "min");
            WeatherCycleManager manager = WeatherCycleManager.getInstance(ctx.getSource().getWorld());
            manager.setClearDuration(min, max);
            return 0;
        }
    }

    private static class ModifyRainCommand {
        private static final RequiredArgumentBuilder<ServerCommandSource, Integer> maxArgument = argument("max", TimeArgumentType.time(0));
        private static final RequiredArgumentBuilder<ServerCommandSource, Integer> minArgument = argument("min", TimeArgumentType.time(0));
        private static final LiteralArgumentBuilder<ServerCommandSource> literalCommand = literal("rain");
        public static final LiteralArgumentBuilder<ServerCommandSource> command = literalCommand.then(minArgument.then(maxArgument.executes(ModifyRainCommand::execute)));

        private static int execute(CommandContext<ServerCommandSource> ctx) {
            int min = IntegerArgumentType.getInteger(ctx, "min");
            int max = IntegerArgumentType.getInteger(ctx, "min");
            WeatherCycleManager manager = WeatherCycleManager.getInstance(ctx.getSource().getWorld());
            manager.setRainDuration(min, max);
            return 0;
        }
    }

    private static class ModifyThunderCommand {
        private static final RequiredArgumentBuilder<ServerCommandSource, Integer> maxArgument = argument("max", TimeArgumentType.time(0));
        private static final RequiredArgumentBuilder<ServerCommandSource, Integer> minArgument = argument("min", TimeArgumentType.time(0));
        private static final LiteralArgumentBuilder<ServerCommandSource> literalCommand = literal("thunder");
        public static final LiteralArgumentBuilder<ServerCommandSource> command = literalCommand.then(minArgument.then(maxArgument.executes(ModifyThunderCommand::execute)));

        private static int execute(CommandContext<ServerCommandSource> ctx) {
            int min = IntegerArgumentType.getInteger(ctx, "min");
            int max = IntegerArgumentType.getInteger(ctx, "min");
            WeatherCycleManager manager = WeatherCycleManager.getInstance(ctx.getSource().getWorld());
            manager.setThunderDuration(min, max);
            return 0;
        }
    }
}
