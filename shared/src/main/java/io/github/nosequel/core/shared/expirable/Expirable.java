package io.github.nosequel.core.shared.expirable;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Expirable {

    @SerializedName("_id")
    private final UUID uniqueId;
    private final UUID executor;

    private final String reason;

    private final long start = System.currentTimeMillis();
    private final long duration;

    private boolean expired = false;

    private ExpirationData expirationData;

    /**
     * Constructor to make a new expirable object
     * with a set duration (-1L) for permanent objects
     *
     * @param uniqueId the unique identifier of the expirable object
     * @param executor the issuer of the object
     * @param reason   the reason why the object was issued
     */
    public Expirable(UUID uniqueId, UUID executor, String reason) {
        this(uniqueId, executor, reason, -1L);
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
    public Expirable(UUID uniqueId, UUID executor, String reason, long duration) {
        this.uniqueId = uniqueId;
        this.executor = executor;
        this.reason = reason;
        this.duration = duration;
    }

    /**
     * Check if the expirable object is
     * still active or not.
     *
     * @return whether it's active or not
     */
    public boolean isActive() {
        if (this.duration != -1L && this.duration - System.currentTimeMillis() <= 0) {
            this.expired = true;
            this.expirationData = new ExpirationData("Expired", System.currentTimeMillis());
        }

        return !this.expired;
    }
}