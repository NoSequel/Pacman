package io.github.nosequel.core.bukkit.rank.prompt;

import io.github.nosequel.core.shared.prompt.ChatPrompt;
import io.github.nosequel.core.shared.prompt.ChatPromptResult;
import io.github.nosequel.core.shared.rank.Rank;
import io.github.nosequel.core.shared.rank.RankHandler;
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
     * @param player  the player ot handle it for
     * @param message the message to handle
     * @param value   the value to handle it for
     * @return the result of the chat prompt
     */
    @Override
    public ChatPromptResult handleInput(UUID player, String message, RankHandler value) {
        value.register(new Rank(UUID.randomUUID(), message));

        return new ChatPromptResult(
                "",
                true
        );
    }
}