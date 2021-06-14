package io.github.nosequel.core.bukkit.rank.prompt;

import io.github.nosequel.core.bukkit.config.impl.MessageConfiguration;
import io.github.nosequel.core.bukkit.rank.menu.editor.RankEditorMenu;
import io.github.nosequel.core.bukkit.util.ColorUtil;
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
        return ColorUtil.translate(MessageConfiguration.RANK_START_CREATING);
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

        value.register(rank);

        if(player != null) {
            player.sendMessage(ColorUtil.translate(MessageConfiguration.RANK_CREATED
                    .replace("$rank", rank.getDisplayName())
            ));

            new RankEditorMenu(player, rank).updateMenu();
        }

        return new ChatPromptResult(
                "",
                true
        );
    }
}