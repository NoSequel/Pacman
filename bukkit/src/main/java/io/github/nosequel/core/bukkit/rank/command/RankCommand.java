package io.github.nosequel.core.bukkit.rank.command;

import io.github.nosequel.command.annotation.Command;
import io.github.nosequel.command.annotation.Subcommand;
import io.github.nosequel.command.bukkit.executor.BukkitCommandExecutor;
import io.github.nosequel.core.bukkit.rank.menu.GlobalRankMenu;
import io.github.nosequel.core.shared.PacmanAPI;
import io.github.nosequel.core.shared.rank.RankHandler;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;

public class RankCommand {

    private final RankHandler rankHandler = PacmanAPI.getPacmanAPI().getRankHandler();

    @Command(label = "rank", permission = "pacman.ranks")
    public void rank(BukkitCommandExecutor executor) {
        new GlobalRankMenu(executor.getPlayer()).updateMenu();
    }

    @SneakyThrows
    @Subcommand(label = "export", parentLabel = "rank", permission = "pacman.export")
    public void export(BukkitCommandExecutor executor) {
        this.rankHandler.exportRanks(Files.newBufferedWriter(Paths.get("ranks.json")));
    }

}
