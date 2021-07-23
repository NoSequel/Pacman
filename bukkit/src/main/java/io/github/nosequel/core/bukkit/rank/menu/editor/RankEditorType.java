package io.github.nosequel.core.bukkit.rank.menu.editor;

import io.github.nosequel.core.bukkit.config.impl.MessageConfiguration;
import io.github.nosequel.core.bukkit.rank.menu.editor.metadata.RankMetadataMenu;
import io.github.nosequel.core.bukkit.rank.prompt.RankColorPrompt;
import io.github.nosequel.core.bukkit.rank.prompt.RankPrefixPrompt;
import io.github.nosequel.core.bukkit.rank.prompt.RankSuffixPrompt;
import io.github.nosequel.core.bukkit.rank.prompt.RankWeightPrompt;
import io.github.nosequel.core.bukkit.util.ColorUtil;
import io.github.nosequel.core.shared.PacmanAPI;
import io.github.nosequel.core.shared.prompt.ChatPromptHandler;
import io.github.nosequel.core.shared.rank.Rank;
import io.github.nosequel.core.shared.rank.metadata.Metadata;
import io.github.nosequel.menu.Menu;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;
import java.util.stream.Collectors;

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
    },

    SUFFIX(ChatColor.GOLD + "Edit Suffix", Material.NAME_TAG) {
        @Override
        public String[] getLore(Rank rank) {
            return new String[]{
                    ChatColor.GOLD + "Current Value: " + ChatColor.RED + rank.getSuffix(),
                    "",
                    ChatColor.GRAY + "Clicking this will close the current menu",
                    ChatColor.GRAY + "to start a new prompt to change the suffix."
            };
        }

        @Override
        public Consumer<InventoryClickEvent> getAction(Rank rank, Menu menu) {
            return event -> {
                final Player player = (Player) event.getWhoClicked();

                this.promptHandler.startPrompt(event.getWhoClicked().getUniqueId(), new RankSuffixPrompt(), rank);

                event.setCancelled(true);
                player.closeInventory();
            };
        }
    },

    WEIGHT(ChatColor.GOLD + "Edit Weight", Material.ANVIL) {
        @Override
        public String[] getLore(Rank rank) {
            return new String[]{
                    ChatColor.GOLD + "Current Value: " + ChatColor.RED + rank.getWeight(),
                    "",
                    ChatColor.GRAY + "Clicking this will close the current menu",
                    ChatColor.GRAY + "to start a new prompt to change the weight."
            };
        }

        @Override
        public Consumer<InventoryClickEvent> getAction(Rank rank, Menu menu) {
            return event -> {
                final Player player = (Player) event.getWhoClicked();

                this.promptHandler.startPrompt(event.getWhoClicked().getUniqueId(), new RankWeightPrompt(), rank);

                event.setCancelled(true);
                player.closeInventory();
            };
        }
    },

    COLOR(ChatColor.GOLD + "Edit Color", Material.INK_SACK) {
        @Override
        public String[] getLore(Rank rank) {
            final ChatColor color = ColorUtil.getColorByRank(rank);

            return new String[]{
                    ChatColor.GOLD + "Current Value: " + ChatColor.RED + color + color.name(),
                    "",
                    ChatColor.GRAY + "Clicking this will close the current menu",
                    ChatColor.GRAY + "to start a new prompt to change the color."
            };
        }

        @Override
        public Consumer<InventoryClickEvent> getAction(Rank rank, Menu menu) {
            return event -> {
                final Player player = (Player) event.getWhoClicked();

                this.promptHandler.startPrompt(event.getWhoClicked().getUniqueId(), new RankColorPrompt(), rank);

                event.setCancelled(true);
                player.closeInventory();
            };
        }
    },

    METADATA(ChatColor.GOLD + "Edit Metadata", Material.BED) {
        @Override
        public String[] getLore(Rank rank) {
            final String metadatum = rank.getMetadatum().stream()
                    .map(Metadata::getDisplayName)
                    .collect(Collectors.joining(ChatColor.WHITE + ", "));

            return new String[]{
                    ChatColor.GOLD + "Current Value: " + ChatColor.RED + metadatum,
                    "",
                    ChatColor.GRAY + "Clicking this will close the current menu",
                    ChatColor.GRAY + "to open a new menu where you can edit the metadatum."
            };
        }

        @Override
        public Consumer<InventoryClickEvent> getAction(Rank rank, Menu menu) {
            return event -> {
                final Player player = (Player) event.getWhoClicked();

                event.setCancelled(true);
                player.closeInventory();

                new RankMetadataMenu(player, rank).updateMenu();
            };
        }
    },

    DELETE(ChatColor.GOLD + "Delete Rank", Material.REDSTONE) {
        @Override
        public String[] getLore(Rank rank) {
            return new String[]{
                    ChatColor.GRAY + "Click here to delete the current rank."
            };
        }

        @Override
        public Consumer<InventoryClickEvent> getAction(Rank rank, Menu menu) {
            return event -> {
                final Player player = (Player) event.getWhoClicked();


                event.setCancelled(true);
                player.closeInventory();
                player.sendMessage(ColorUtil.translate(MessageConfiguration.RANK_DELETED));

                PacmanAPI.getPacmanAPI().getRankHandler().delete(rank);
            };
        }
    };


    private final String displayName;
    private final Material material;

    public final ChatPromptHandler promptHandler = PacmanAPI.getPacmanAPI().getPromptHandler();

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