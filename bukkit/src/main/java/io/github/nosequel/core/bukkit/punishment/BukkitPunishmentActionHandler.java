package io.github.nosequel.core.bukkit.punishment;

import io.github.nosequel.core.shared.punishment.Punishment;
import io.github.nosequel.core.shared.punishment.PunishmentActionHandler;

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
        return false;
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
        return false;
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
