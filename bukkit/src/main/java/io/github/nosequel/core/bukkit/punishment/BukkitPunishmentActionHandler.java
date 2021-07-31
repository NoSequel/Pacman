package io.github.nosequel.core.bukkit.punishment;

import io.github.nosequel.core.bukkit.config.impl.MessageConfiguration;
import io.github.nosequel.core.bukkit.util.ColorUtil;
import io.github.nosequel.core.shared.punishment.Punishment;
import io.github.nosequel.core.shared.punishment.PunishmentActionHandler;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class BukkitPunishmentActionHandler implements PunishmentActionHandler {

    /**
     * Attempt to execute a {@link Punishment} using the provided
     * implementation by the current running Pacman instance.
     *
     * @param punishment the punishment to attempt to execute
     * @return whether it successfully went through successfully
     */
    @Override
    public boolean attemptBan(Punishment punishment) {
        Optional.of(Bukkit.getPlayer(punishment.getTarget())).ifPresent(player -> player.kickPlayer(
                ColorUtil.translate(MessageConfiguration.PUNISHMENT_KICK_MESSAGE)
                        .replace("$reason", punishment.getReason())
        ));

        return true;
    }

    /**
     * Attempt to register a {@link Punishment} using the provided
     * implementation by the current running Pacman instance.
     *
     * @param punishment the punishment to attempt to execute
     * @return whether it successfully went through successfully
     */
    @Override
    public boolean registerPunishment(Punishment punishment) {
        final CompletableFuture<String[]> nameSetFuture = CompletableFuture.supplyAsync(() -> {
            final String targetName = ColorUtil.getColoredName(punishment.getTarget());
            final String executorName = ColorUtil.getColoredName(punishment.getExecutor());

            return new String[]{
                    targetName,
                    executorName
            };
        });

        nameSetFuture.thenAccept(nameSet -> {
            final boolean permanent = punishment.getDuration() == -1L;
            final boolean silent = punishment.getReason().contains("-s");

            final StringBuilder builder = new StringBuilder(silent ? MessageConfiguration.PUNISHMENT_SILENT_PREFIX : "");

            if (permanent) {
                builder.append(MessageConfiguration.PUNISHMENT_ISSUE_MESSAGE);
            } else {
                builder.append(MessageConfiguration.PUNISHMENT_ISSUE_TEMPORARY_MESSAGE);
            }

            Bukkit.broadcastMessage(builder.toString()
                    .replace("$target", nameSet[0])
                    .replace("$executor", nameSet[1])
                    .replace("$fancy_punishment_type", punishment.getPunishmentType().getFancyName())
            );
        });

        return true;
    }

    /**
     * Attempt to expire a {@link Punishment} using the provided
     * implementation by the current running Pacman instance.
     *
     * @param punishment the punishment to attempt to execute
     * @return whether it successfully went through successfully
     */
    @Override
    public boolean expirePunishment(Punishment punishment) {
        return false;
    }
}
