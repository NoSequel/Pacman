package io.github.nosequel.core.bukkit.rank.command;

import io.github.nosequel.core.bukkit.rank.menu.GlobalRankMenu;
import me.blazingtide.zetsu.permissible.impl.permissible.Permissible;
import me.blazingtide.zetsu.schema.annotations.Command;
import org.bukkit.entity.Player;

public class RankCommand {

    @Command(labels = "rank", description = "Open the rank editor menu")
    @Permissible("rank.editor")
    public void rank(Player player) {
        new GlobalRankMenu(player).updateMenu();
    }

}
