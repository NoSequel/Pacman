package io.github.nosequel.core.bukkit.rank.prompt;

import io.github.nosequel.core.shared.PacmanAPI;
import io.github.nosequel.core.shared.prompt.ChatPrompt;
import io.github.nosequel.core.shared.prompt.ChatPromptResult;
import io.github.nosequel.core.shared.rank.Rank;
import io.github.nosequel.core.shared.rank.RankHandler;

import java.util.UUID;

public abstract class RankPrompt implements ChatPrompt<Rank> {

    private final RankHandler rankHandler = PacmanAPI.getPacmanAPI().getRankHandler();

    /**
     * Handle the input of a {@link ChatPrompt} object
     *
     * @param player  the player ot handle it for
     * @param message the message to handle
     * @param value   the value to handle it for
     * @return the result of the chat prompt
     */
    @Override
    public ChatPromptResult handleInput(UUID player, String message, Rank value) {
        this.rankHandler.updateRank(value);

        return new ChatPromptResult(
                "",
                true
        );
    }
}