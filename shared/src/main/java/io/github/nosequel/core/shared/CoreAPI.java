package io.github.nosequel.core.shared;

import io.github.nosequel.core.shared.database.DatabaseHandler;
import io.github.nosequel.core.shared.database.mongo.MongoDatabaseHandler;
import io.github.nosequel.core.shared.grants.GrantHandler;
import io.github.nosequel.core.shared.rank.Rank;
import io.github.nosequel.core.shared.rank.RankHandler;
import io.github.nosequel.core.shared.rank.RankRepository;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CoreAPI {

    @Getter
    private static CoreAPI coreAPI;

    private final DatabaseHandler databaseHandler;
    private final RankHandler rankHandler;
    private final GrantHandler grantHandler;

    public CoreAPI() {
        coreAPI = this;

        this.databaseHandler = new MongoDatabaseHandler("127.0.0.1", 27017, "pacman", "", "", false);
        this.rankHandler = new RankHandler(new RankRepository(this.databaseHandler));
        this.grantHandler = new GrantHandler(new GrantHandler.GrantRepository(this.databaseHandler));
    }

    /**
     * Get the primary default rank
     *
     * @return the primary default rank
     */
    public Rank getPrimaryDefaultRank() {
        return this.rankHandler.findDefault().get(0);
    }

    /**
     * Get all of the default ranks
     *
     * @return the default ranks
     */
    public List<Rank> getDefaultRanks() {
        return this.rankHandler.findDefault();
    }

}