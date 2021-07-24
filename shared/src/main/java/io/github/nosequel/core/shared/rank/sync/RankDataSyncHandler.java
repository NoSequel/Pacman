package io.github.nosequel.core.shared.rank.sync;

import io.github.nosequel.core.shared.database.sync.DataSyncHandler;
import io.github.nosequel.core.shared.rank.Rank;
import io.github.nosequel.core.shared.rank.RankHandler;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RankDataSyncHandler extends DataSyncHandler<Rank> {

    private final RankHandler rankHandler;

    /**
     * Handle an incoming {@link Rank} object
     *
     * @param rank the object to handle
     */
    @Override
    public void handleData(Rank rank) {
        final Optional<Rank> optionalRank = this.rankHandler.find(rank.getUniqueId());

        if(optionalRank.isPresent()) {
            rankHandler.replaceRank(optionalRank.get(), rank);
        } else {
            rankHandler.register(rank);
        }
    }

    /**
     * Get the type of the data sync handler
     *
     * @return the type
     */
    @Override
    public Class<Rank> getType() {
        return Rank.class;
    }
}
