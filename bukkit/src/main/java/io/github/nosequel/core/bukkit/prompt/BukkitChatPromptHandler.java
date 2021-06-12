package io.github.nosequel.core.bukkit.prompt;

import io.github.nosequel.core.shared.prompt.ChatPromptHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.UUID;

public class BukkitChatPromptHandler extends ChatPromptHandler {

    /**
     * Send a message to a unique identifier
     *
     * @param uuid the unique identfieir to send it to
     * @param text the text to send to the player
     */
    @Override
    public void sendMessage(UUID uuid, String text) {
        Bukkit.getPlayer(uuid).sendMessage(ChatColor.translateAlternateColorCodes('&', text));
    }
}
