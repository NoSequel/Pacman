package io.github.nosequel.core.shared.punishment;

import java.util.UUID;

public interface PunishmentActionHandler {

    /**
     * Attempt to execute a {@link Punishment} using the provided
     * implementation by the current running Pacman instance.
     *
     * @param punishment the punishment to attempt to execute
     * @return whether it successfully went through successfully
     */
    boolean attemptBan(Punishment punishment);

    /**
     * Attempt to register a {@link Punishment} using the provided
     * implementation by the current running Pacman instance.
     *
     * @param punishment the punishment to attempt to execute
     * @return whether it successfully went through successfully
     */
    boolean registerPunishment(Punishment punishment);

    /**
     * Attempt to expire a {@link Punishment} using the provided
     * implementation by the current running Pacman instance.
     *
     * @param punishment the punishment to attempt to execute
     * @return whether it successfully went through successfully
     */
    boolean expirePunishment(Punishment punishment);

}
