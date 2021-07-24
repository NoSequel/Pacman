package io.github.nosequel.core.shared.punishment;

import io.github.nosequel.core.shared.expirable.ExpirationData;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PunishmentHandler {

    private final PunishmentRepository repository;
    private final PunishmentActionHandler actionHandler;
    private final List<Punishment> punishments = new ArrayList<>();

    public void load() {
        this.repository.retrieve().thenAccept(this.punishments::addAll);
    }

    public void save() {
        for (Punishment punishment : this.punishments) {
            this.repository.update(punishment, punishment.getUniqueId().toString());
        }
    }

    /**
     * Find the most relevant punishment for a target
     *
     * @param uniqueId the unique identifier of the target
     * @return the optional of the found most relevant punishment
     */
    public Optional<Punishment> findRelevantPunishment(UUID uniqueId) {
        return this.findPunishments(uniqueId).stream()
                .filter(punishment -> punishment.getTarget().equals(uniqueId))
                .findFirst();
    }

    /**
     * Find the most relevant punishment for a target
     *
     * @param uniqueId the unique identifier of the target
     * @param type     the type to find the punishments by
     * @return the optional of the found most relevant punishment
     */
    public Optional<Punishment> findRelevantPunishment(UUID uniqueId, PunishmentType type) {
        return this.findPunishments(uniqueId, type).stream()
                .filter(punishment -> punishment.getTarget().equals(uniqueId))
                .findFirst();
    }

    /**
     * Find all the punishments for a specific target
     *
     * @param uniqueId the unique identifier of the target to find all the punishments by
     * @return the punishments found by the player's unique identifier.
     */
    public List<Punishment> findPunishments(UUID uniqueId) {
        return this.punishments.stream()
                .filter(punishment -> punishment.getTarget().equals(uniqueId))
                .collect(Collectors.toList());
    }

    /**
     * Find all the punishments for a specific target
     *
     * @param uniqueId the unique identifier of the target to find all the punishments by
     * @param type     the type to find the punishments by
     * @return the punishments found by the player's unique identifier.
     */
    public List<Punishment> findPunishments(UUID uniqueId, PunishmentType type) {
        return this.findPunishments(uniqueId).stream()
                .filter(punishment -> punishment.getPunishmentType().equals(type))
                .collect(Collectors.toList());
    }

    /**
     * Expire a {@link Punishment} object with a set expiration data
     *
     * @param punishment the punishment to expire
     * @param data       the data to expire the punishment with
     */
    public void expirePunishment(Punishment punishment, ExpirationData data) {
        if (!this.punishments.contains(punishment)) {
            this.registerPunishment(punishment);
        }

        if (punishment.isActive()) {
            punishment.setExpirationData(data);
            punishment.setExpired(true);

            this.repository.update(punishment, punishment.getUniqueId().toString());
            this.actionHandler.expirePunishment(punishment);

            // todo: redis synchronization
        }
    }

    /**
     * Register a new punishment to the {@link PunishmentHandler#repository}
     * field, this updates it to the database and the cache.
     *
     * @param punishment the punishment to register
     */
    public void registerPunishment(Punishment punishment) {
        if (this.punishments.stream().noneMatch(target -> target.getUniqueId().equals(punishment.getUniqueId()))) {
            final Optional<Punishment> relevantPunishment = this.findRelevantPunishment(punishment.getTarget(), punishment.getPunishmentType());
            final ExpirationData expirationData = new ExpirationData("Overwrote by other punishment", System.currentTimeMillis());

            relevantPunishment.ifPresent(value -> this.expirePunishment(value, expirationData));
        }

        this.repository.update(punishment, punishment.getUniqueId().toString());
        this.punishments.add(punishment);

        this.actionHandler.registerPunishment(punishment);

        // todo: redis synchronization
    }
}