package io.github.nosequel.core.bukkit.rank.menu.editor;

import io.github.nosequel.core.bukkit.rank.prompt.RankPrefixPrompt;
import io.github.nosequel.core.shared.CoreAPI;
import io.github.nosequel.core.shared.prompt.ChatPromptHandler;
import io.github.nosequel.core.shared.rank.Rank;
import io.github.nosequel.menu.Menu;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

@Getter
@RequiredArgsConstructor
public enum RankEditorType {

    PREFIX(ChatColor.GOLD + "Edit Prefix", Material.NAME_TAG) {
        @Override
        public String[] getLore(Rank rank) {
            return new String[]{
                    ChatColor.GOLD + "Current Value: " + ChatColor.RED + rank.getPrefix(),
                    "",
                    ChatColor.GRAY + "Clicking this will close the current menu",
                    ChatColor.GRAY + "to start a new prompt to change the prefix."
            };
        }

        @Override
        public Consumer<InventoryClickEvent> getAction(Rank rank, Menu menu) {
            return event -> {
                final Player player = (Player) event.getWhoClicked();

                this.promptHandler.startPrompt(event.getWhoClicked().getUniqueId(), new RankPrefixPrompt(), rank);

                event.setCancelled(true);
                player.closeInventory();
            };
        }
    };

    private final String displayName;
    private final Material material;

    public final ChatPromptHandler promptHandler = CoreAPI.getCoreAPI().getPromptHandler();

    /**
     * Get the lore to display inside of the button
     *
     * @param rank the rank to get the lore from
     * @return the lore
     */
    public abstract String[] getLore(Rank rank);

    /**
     * Get the action to perform whenever interacting with the button
     *
     * @param rank the rank to perform the action with
     * @param menu the menu which the action was performed from
     * @return the action to execute
     */
    public abstract Consumer<InventoryClickEvent> getAction(Rank rank, Menu menu);

}