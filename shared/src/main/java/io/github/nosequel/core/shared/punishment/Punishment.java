package io.github.nosequel.core.shared.punishment;

import io.github.nosequel.core.shared.expirable.Expirable;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Punishment extends Expirable {

    private final PunishmentType punishmentType;

    /**
     * Constructor to make a new expirable object
     * with a set duration (-1L) for permanent objects
     *
     * @param uniqueId the unique identifier of the expirable object
     * @param executor the issuer of the object
     * @param reason   the reason why the object was issued
     */
    public Punishment(UUID uniqueId, UUID executor, String reason, PunishmentType punishmentType) {
        super(uniqueId, executor, reason);
        this.punishmentType = punishmentType;
    }

    /**
     * Constructor to make a new expirable object with a
     * provided duration argument.
     *
     * @param uniqueId the unique identifier of the expirable object
     * @param executor the issuer of the object
     * @param reason   the reason why the object was issued
     * @param duration the duration of the object
     */
    public Punishment(UUID uniqueId, UUID executor, String reason, long duration, PunishmentType punishmentType) {
        super(uniqueId, executor, reason, duration);
        this.punishmentType = punishmentType;
    }
}