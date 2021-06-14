package io.github.nosequel.core.bukkit.rank.prompt;

import io.github.nosequel.core.bukkit.config.impl.MessageConfiguration;
import io.github.nosequel.core.bukkit.rank.menu.editor.RankEditorMenu;
import io.github.nosequel.core.bukkit.util.ColorUtil;
import io.github.nosequel.core.shared.prompt.ChatPrompt;
import io.github.nosequel.core.shared.prompt.ChatPromptResult;
import io.github.nosequel.core.shared.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RankColorPrompt implements ChatPrompt<Rank> {

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
        return ColorUtil.translate(MessageConfiguration.RANK_START_SETTING_COLOR);
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

        value.setColor(message);

        if (player != null) {
            player.sendMessage(ColorUtil.translate(MessageConfiguration.RANK_SET_COLOR
                    .replace("$rank", value.getDisplayName())
                    .replace("$color", ColorUtil.getColorByRank(value) + ColorUtil.getColorByRank(value).name())
            ));

            new RankEditorMenu(player, value).updateMenu();
        }

        return new ChatPromptResult(
                "",
                true
        );
    }
}