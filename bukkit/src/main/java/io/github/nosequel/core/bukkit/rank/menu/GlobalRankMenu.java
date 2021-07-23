package io.github.nosequel.core.bukkit.rank.menu;

import io.github.nosequel.core.bukkit.rank.menu.editor.RankEditorMenu;
import io.github.nosequel.core.bukkit.rank.prompt.RankCreatePrompt;
import io.github.nosequel.core.bukkit.util.ColorUtil;
import io.github.nosequel.core.bukkit.util.InventoryActionWrapper;
import io.github.nosequel.core.shared.prompt.ChatPromptHandler;
import io.github.nosequel.core.shared.PacmanAPI;
import io.github.nosequel.core.shared.rank.Rank;
import io.github.nosequel.core.shared.rank.RankHandler;
import io.github.nosequel.menu.buttons.Button;
import io.github.nosequel.menu.pagination.PaginatedMenu;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class GlobalRankMenu extends PaginatedMenu {

    private final RankHandler rankHandler = PacmanAPI.getPacmanAPI().getRankHandler();
    private final ChatPromptHandler promptHandler = PacmanAPI.getPacmanAPI().getPromptHandler();

    public GlobalRankMenu(Player player) {
        super(player, "Rank Editor", 18);
    }

    @Override
    public Button[] getNavigationBar() {
        final Button[] buttons = super.getNavigationBar();
        final Consumer<InventoryClickEvent> action = InventoryActionWrapper.closeInventoryWrapper(event ->
                promptHandler.startPrompt(this.getPlayer().getUniqueId(), new RankCreatePrompt(), this.rankHandler)
        );

        buttons[4] = new Button(Material.NETHER_STAR)
                .setDisplayName(ChatColor.GREEN + "&aCreate Rank")
                .setClickAction(action);

        return buttons;
    }

    @Override
    public void tick() {
        for (int i = 0; i < this.rankHandler.getRanks().size(); i++) {
            final Rank rank = this.rankHandler.getRanks().get(i);
            final Consumer<InventoryClickEvent> action = InventoryActionWrapper.closeInventoryWrapper(event ->
                    new RankEditorMenu(this.getPlayer(), rank).updateMenu()
            );

            final ChatColor color = ColorUtil.getColorByRank(rank);
            final String[] lore = new String[]{
                    ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + StringUtils.repeat("-", 24),

                    ChatColor.GOLD + "Rank Information",
                    ChatColor.BLUE + "▶ " + ChatColor.YELLOW + "Weight: " + ChatColor.RED + rank.getWeight(),
                    ChatColor.BLUE + "▶ " + ChatColor.YELLOW + "Color: " + color + color.name(),
                    ChatColor.BLUE + "▶ " + ChatColor.YELLOW + "Display Name: " + ChatColor.RED + rank.getDisplayName(),

                    "",

                    ChatColor.GOLD + "Chat Information",
                    ChatColor.BLUE + "▶ " + ChatColor.YELLOW + "Prefix: " + ChatColor.RED + rank.getPrefix(),
                    ChatColor.BLUE + "▶ " + ChatColor.YELLOW + "Suffix: " + ChatColor.RED + rank.getSuffix(),

                    ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + StringUtils.repeat("-", 24)
            };

            this.buttons[i] = new Button(Material.INK_SACK)
                    .setDisplayName(rank.getDisplayName())
                    .setAmount(rank.getWeight())
                    .setLore(lore)
                    .setData(ColorUtil.findDyeColor(color).getDyeData())
                    .setClickAction(action);
        }
    }
}