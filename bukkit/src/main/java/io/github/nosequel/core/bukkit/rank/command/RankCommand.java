package io.github.nosequel.core.bukkit.rank.command;

import io.github.nosequel.command.annotation.Command;
import io.github.nosequel.command.bukkit.executor.BukkitCommandExecutor;
import io.github.nosequel.core.bukkit.rank.menu.GlobalRankMenu;

public class RankCommand {

    @Command(label = "rank", permission = "pacman.ranks")
    public void rank(BukkitCommandExecutor executor) {
        new GlobalRankMenu(executor.getPlayer()).updateMenu();
    }
}
