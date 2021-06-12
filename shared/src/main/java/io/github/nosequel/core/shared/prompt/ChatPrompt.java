package io.github.nosequel.core.shared.prompt;

import java.util.UUID;

public interface ChatPrompt<T> {

    /**
     * Get the prompt text to send to the player
     * whenever the prompt starts.
     *
     * @param player the player to send it to
     * @param value  the value of the data
     * @return the text
     */
    String getPromptText(UUID player, T value);

    /**
     * Handle the input of a {@link ChatPrompt} object
     *
     * @param player  the player ot handle it for
     * @param message the message to handle
     * @param value   the value to handle it for
     * @return the result of the chat prompt
     */
    ChatPromptResult handleInput(UUID player, String message, T value);

    /**
     * Handle the input of a {@link ChatPrompt} object
     *
     * @param player  the player ot handle it for
     * @param message the message to handle
     * @param value   the value to handle it for
     * @return the result of the chat prompt
     */
    default ChatPromptResult handleInputCasted(UUID player, String message, Object value) {
        return this.handleInput(player, message, (T) value);
    }
}