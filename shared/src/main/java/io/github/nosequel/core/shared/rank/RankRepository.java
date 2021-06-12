package io.github.nosequel.core.shared.rank;

import io.github.nosequel.core.shared.database.DatabaseHandler;
import io.github.nosequel.core.shared.repository.BaseRepository;

public class RankRepository extends BaseRepository<Rank> {

    /**
     * Constructor to make a new rank repository object
     *
     * @param handler the database handler
     */
    public RankRepository(DatabaseHandler handler) {
        super("ranks", handler);
    }

    @Override
    public Class<Rank> getType() {
        return Rank.class;
    }
}
