package io.github.nosequel.core.bukkit.grant.command;

import io.github.nosequel.command.annotation.Command;
import io.github.nosequel.command.bukkit.executor.BukkitCommandExecutor;
import io.github.nosequel.core.bukkit.data.TemporaryPlayerObject;
import io.github.nosequel.core.bukkit.grant.menu.GrantMenu;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GrantCommand {

    @Command(label = "grant", permission = "pacman.grant")
    public void grant(BukkitCommandExecutor executor, TemporaryPlayerObject target) {
        if (!(executor.getSender() instanceof Player)) {
            executor.sendMessage(ChatColor.RED + "You must be a player to execute this command.");
            return;
        }

        new GrantMenu(executor.getPlayer(), target).updateMenu();
    }
}