package io.github.nosequel.core.shared.prompt;


import lombok.Getter;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Getter
public abstract class ChatPromptHandler {

    private final Set<ChatPromptData<?>> prompts = new HashSet<>();

    /**
     * Send a message to a unique identifier
     *
     * @param uuid the unique identfieir to send it to
     * @param text the text to send to the player
     */
    public abstract void sendMessage(UUID uuid, String text);

    /**
     * Start a new {@link ChatPrompt} procedure for a {@link UUID}
     *
     * @param player the player to start it for
     * @param prompt the prompt to start
     * @param value  the value to use to handle the prompt
     * @param <T>    the type of the prompt value
     */
    public <T> void startPrompt(UUID player, ChatPrompt<T> prompt, T value) {
        if(this.findPrompt(player).isPresent()) {
            this.sendMessage(player, "&cYou already have an active prompt.");
            return;
        }

        final ChatPromptData<T> promptData = new ChatPromptData<>(player, prompt, value);

        this.sendMessage(player, prompt.getPromptText(player, value));
        this.prompts.add(promptData);
    }

    /**
     * Find a prompt by a player
     *
     * @param player the player to find the prompt by
     * @return the optional of the prompt
     */
    public Optional<ChatPromptData<?>> findPrompt(UUID player) {
        return this.prompts.stream()
                .filter(prompt -> prompt.getTarget().equals(player))
                .findFirst();
    }
}