package io.github.nosequel.core.bukkit.rank.prompt.impl;

import io.github.nosequel.core.bukkit.rank.menu.editor.RankEditorMenu;
import io.github.nosequel.core.bukkit.rank.prompt.RankPrompt;
import io.github.nosequel.core.shared.prompt.ChatPrompt;
import io.github.nosequel.core.shared.prompt.ChatPromptResult;
import io.github.nosequel.core.shared.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RankSuffixPrompt extends RankPrompt {
    /**
     * Get the prompt text to send to the player
     * whenever the prompt starts.
     *
     * @param player the player to send it to
     * @param value  the value of the data
     * @return the text
     */
    @Override
    public String getPromptText(UUID player, Rank value) {
        return ChatColor.YELLOW + "Type the prefix to update the rank's suffix value to in the chat.";
    }

    /**
     * Handle the input of a {@link ChatPrompt} object
     *
     * @param uniqueId the player ot handle it for
     * @param message  the message to handle
     * @param value    the value to handle it for
     * @return the result of the chat prompt
     */
    @Override
    public ChatPromptResult handleInput(UUID uniqueId, String message, Rank value) {
        final Player player = Bukkit.getPlayer(uniqueId);

        value.setSuffix(message);

        if (player != null) {
            player.sendMessage(ChatColor.YELLOW + "You updated the suffix of the rank to " + message + ".");
            new RankEditorMenu(player, value).updateMenu();
        }

        return super.handleInput(uniqueId, message, value);
    }
}