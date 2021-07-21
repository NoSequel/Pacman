package io.github.nosequel.core.shared;

import io.github.nosequel.core.shared.database.DatabaseHandler;
import io.github.nosequel.core.shared.database.mongo.MongoDatabaseHandler;
import io.github.nosequel.core.shared.grants.GrantHandler;
import io.github.nosequel.core.shared.logger.Logger;
import io.github.nosequel.core.shared.prompt.ChatPromptHandler;
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

    private Logger logger;
    private ChatPromptHandler promptHandler;

    private final DatabaseHandler databaseHandler;
    private RankHandler rankHandler;
    private GrantHandler grantHandler;

    public CoreAPI(DatabaseHandler databaseHandler) {
        coreAPI = this;
        this.databaseHandler = databaseHandler;
    }

    public void enable() {
        if(this.logger == null || this.promptHandler == null) {
            throw new IllegalStateException("Not all required fields are set within the CoreAPI class.");
        }

        this.rankHandler = new RankHandler(new RankRepository(this.databaseHandler));
        this.grantHandler = new GrantHandler(new GrantHandler.GrantRepository(this.databaseHandler));

        // load all data
        this.rankHandler.load();
        this.grantHandler.load();
    }

    public void disable() {
        this.rankHandler.save();
        this.grantHandler.save();
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