package io.github.nosequel.core.bukkit;

import io.github.nosequel.core.bukkit.logger.BukkitLogger;
import io.github.nosequel.core.bukkit.prompt.BukkitChatPromptHandler;
import io.github.nosequel.core.bukkit.punishment.BukkitPunishmentActionHandler;
import io.github.nosequel.core.shared.PacmanImpl;
import io.github.nosequel.core.shared.database.DatabaseHandler;
import io.github.nosequel.core.shared.database.SyncHandler;

public class BukkitPacmanImpl extends PacmanImpl {

    public BukkitPacmanImpl(DatabaseHandler databaseHandler, SyncHandler syncHandler) {
        super(new BukkitLogger(), databaseHandler, syncHandler, new BukkitPunishmentActionHandler(), new BukkitChatPromptHandler());
    }
}
