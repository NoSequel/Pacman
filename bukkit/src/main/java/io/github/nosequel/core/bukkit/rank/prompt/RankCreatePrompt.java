package io.github.nosequel.core.bukkit.rank.prompt;

import io.github.nosequel.core.bukkit.rank.menu.editor.RankEditorMenu;
import io.github.nosequel.core.shared.prompt.ChatPrompt;
import io.github.nosequel.core.shared.prompt.ChatPromptResult;
import io.github.nosequel.core.shared.rank.Rank;
import io.github.nosequel.core.shared.rank.RankHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RankCreatePrompt implements ChatPrompt<RankHandler> {

    /**
     * Get the prompt text to send to the player
     * whenever the prompt starts.
     *
     * @param player the player to send it to
     * @param value  the value of the data
     * @return the text
     */
    @Override
    public String getPromptText(UUID player, RankHandler value) {
        return ChatColor.YELLOW + "Please type a name for the rank to start with the process.";
    }

    /**
     * Handle the input of a {@link ChatPrompt} object
     *
     * @param uniqueId  the player ot handle it for
     * @param message the message to handle
     * @param value   the value to handle it for
     * @return the result of the chat prompt
     */
    @Override
    public ChatPromptResult handleInput(UUID uniqueId, String message, RankHandler value) {
        final Player player = Bukkit.getPlayer(uniqueId);
        final Rank rank = new Rank(UUID.randomUUID(), message);

        if(player != null) {
            player.sendMessage(ChatColor.YELLOW + "You have created a new rank with the name " + message + ".");
            player.sendMessage(ChatColor.YELLOW + "Opening a menu so you can edit this rank.");

            new RankEditorMenu(player, rank).updateMenu();
        }

        value.register(rank);

        return new ChatPromptResult(
                "",
                true
        );
    }
}