package io.github.nosequel.core.shared.grants;

import io.github.nosequel.core.shared.PacmanAPI;
import io.github.nosequel.core.shared.expirable.Expirable;
import io.github.nosequel.core.shared.rank.Rank;
import io.github.nosequel.core.shared.rank.RankHandler;
import lombok.Getter;

import java.util.Optional;
import java.util.UUID;

@Getter
public class Grant extends Expirable {

    private final UUID target;
    private final UUID rankId;

    /**
     * Constructor to make a new grant object
     *
     * @param target   the target who the grant is for
     * @param rankId   the identifier of the rank
     * @param uniqueId the unique identifier of the grant object
     * @param executor the executor of the grant
     * @param reason   the reason why the grant was issued
     */
    public Grant(UUID target, UUID rankId, UUID uniqueId, UUID executor, String reason) {
        this(target, rankId, uniqueId, executor, reason, -1L);
    }

    /**
     * Constructor to make a new grant object
     *
     * @param target   the target who the grant is for
     * @param rankId   the identifier of the rank
     * @param uniqueId the unique identifier of the grant object
     * @param executor the executor of the grant
     * @param reason   the reason why the grant was issued
     * @param duration the duration the grant object lasts
     */
    public Grant(UUID target, UUID rankId, UUID uniqueId, UUID executor, String reason, long duration) {
        super(uniqueId, executor, reason, duration);

        this.target = target;
        this.rankId = rankId;
    }

    /**
     * Get the rank for the grant object
     *
     * @return <p>
     *     the found rank if the rank could be found
     *     if the rank couldn't be found, the first default rank.
     * </p>
     */
    public Rank getRank() {
        final RankHandler rankHandler = PacmanAPI.getPacmanAPI().getRankHandler();
        final Optional<Rank> rank = rankHandler.find(this.rankId);

        return rank.orElseGet(() -> rankHandler.findDefault().get(0));
    }
}