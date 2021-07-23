package io.github.nosequel.core.shared;

import io.github.nosequel.core.shared.database.DatabaseHandler;
import io.github.nosequel.core.shared.grants.GrantHandler;
import io.github.nosequel.core.shared.prompt.ChatPromptHandler;
import io.github.nosequel.core.shared.rank.RankHandler;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacmanAPI {

    @Getter
    private static PacmanAPI pacmanAPI;
    private final PacmanImpl pacman;

    private final DatabaseHandler databaseHandler;
    private final GrantHandler grantHandler;
    private final RankHandler rankHandler;
    private final ChatPromptHandler promptHandler;

    /**
     * Constructor to make a new {@link PacmanAPI}
     * class, this registers all the fields within the PacmanAPI class.
     *
     * @param pacman the pacman implementation
     */
    public PacmanAPI(PacmanImpl pacman) {
        pacmanAPI = this;

        this.pacman = pacman;
        this.databaseHandler = pacman.getDatabaseHandler();
        this.grantHandler = pacman.getGrantHandler();
        this.rankHandler = pacman.getRankHandler();
        this.promptHandler = pacman.getPromptHandler();
    }
}