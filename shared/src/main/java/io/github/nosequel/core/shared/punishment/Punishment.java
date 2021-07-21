package io.github.nosequel.core.shared.punishment;

import io.github.nosequel.core.shared.expirable.Expirable;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Punishment extends Expirable {

    private final UUID target;
    private final PunishmentType punishmentType;

    /**
     * Constructor to make a new expirable object with a
     * provided duration argument.
     *
     * @param uniqueId the unique identifier of the expirable object
     * @param executor the issuer of the object
     * @param target   the person who the punishment is targeted to
     * @param reason   the reason why the object was issued
     */
    public Punishment(UUID uniqueId, UUID executor, UUID target, String reason, PunishmentType punishmentType) {
        super(uniqueId, executor, reason);
        this.target = target;
        this.punishmentType = punishmentType;
    }

    /**
     * Constructor to make a new expirable object with a
     * provided duration argument.
     *
     * @param uniqueId the unique identifier of the expirable object
     * @param executor the issuer of the object
     * @param target   the person who the punishment is targeted to
     * @param reason   the reason why the object was issued
     * @param duration the duration of the object
     */
    public Punishment(UUID uniqueId, UUID executor, UUID target, String reason, long duration, PunishmentType punishmentType) {
        super(uniqueId, executor, reason, duration);
        this.target = target;
        this.punishmentType = punishmentType;
    }
}