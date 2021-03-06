package io.github.nosequel.core.shared;

import io.github.nosequel.core.shared.database.DatabaseHandler;
import io.github.nosequel.core.shared.database.SyncHandler;
import io.github.nosequel.core.shared.grants.GrantHandler;
import io.github.nosequel.core.shared.logger.Logger;
import io.github.nosequel.core.shared.prompt.ChatPromptHandler;
import io.github.nosequel.core.shared.punishment.PunishmentActionHandler;
import io.github.nosequel.core.shared.punishment.PunishmentHandler;
import io.github.nosequel.core.shared.punishment.PunishmentRepository;
import io.github.nosequel.core.shared.rank.RankHandler;
import io.github.nosequel.core.shared.rank.RankRepository;
import io.github.nosequel.core.shared.rank.sync.RankDataSyncHandler;
import lombok.Getter;

import java.util.Arrays;

@Getter
public abstract class PacmanImpl {

    private final Logger logger;
    private final DatabaseHandler databaseHandler;
    private final SyncHandler syncHandler;

    private final GrantHandler grantHandler;
    private final RankHandler rankHandler;
    private final PunishmentHandler punishmentHandler;
    private final ChatPromptHandler promptHandler;

    public PacmanImpl(Logger logger, DatabaseHandler databaseHandler, SyncHandler syncHandler, PunishmentActionHandler actionHandler, ChatPromptHandler promptHandler) {
        this.logger = logger;
        this.databaseHandler = databaseHandler;
        this.syncHandler = syncHandler;

        this.grantHandler = new GrantHandler(new GrantHandler.GrantRepository(databaseHandler));
        this.rankHandler = new RankHandler(new RankRepository(databaseHandler));
        this.promptHandler = promptHandler;

        this.punishmentHandler = new PunishmentHandler(
                new PunishmentRepository(databaseHandler),
                actionHandler
        );

        this.syncHandler.getSyncHandlers().addAll(Arrays.asList(
                new RankDataSyncHandler(this.rankHandler)
        ));

        this.load();
    }

    public void load() {
        this.grantHandler.load();
        this.rankHandler.load();
        this.punishmentHandler.load();
    }

    public void save() {
        this.grantHandler.save();
        this.rankHandler.save();
        this.punishmentHandler.save();
    }
}