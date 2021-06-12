package io.github.nosequel.core.bukkit.prompt;

import io.github.nosequel.core.shared.prompt.ChatPromptData;
import io.github.nosequel.core.shared.prompt.ChatPromptHandler;
import io.github.nosequel.core.shared.prompt.ChatPromptResult;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Optional;

@RequiredArgsConstructor
public class ChatPromptListener implements Listener {

    private final ChatPromptHandler promptHandler;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final Optional<ChatPromptData<?>> promptData = this.promptHandler.findPrompt(player.getUniqueId());

        if (promptData.isPresent()) {
            final ChatPromptResult result = promptData.get().getPrompt()
                    .handleInputCasted(player.getUniqueId(), event.getMessage(), promptData.get().getValue());

            event.setCancelled(result.isCancelled());
            event.setFormat(result.getFormat());

            this.promptHandler.getPrompts().remove(promptData.get());
        }
    }
}