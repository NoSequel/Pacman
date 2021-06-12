package io.github.nosequel.core.bukkit.rank.menu;

import io.github.nosequel.core.bukkit.rank.prompt.RankCreatePrompt;
import io.github.nosequel.core.shared.prompt.ChatPromptHandler;
import io.github.nosequel.core.shared.CoreAPI;
import io.github.nosequel.core.shared.rank.Rank;
import io.github.nosequel.core.shared.rank.RankHandler;
import io.github.nosequel.menu.buttons.Button;
import io.github.nosequel.menu.pagination.PaginatedMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class RankEditorMenu extends PaginatedMenu {

    private final RankHandler rankHandler = CoreAPI.getCoreAPI().getRankHandler();
    private final ChatPromptHandler promptHandler = CoreAPI.getCoreAPI().getPromptHandler();

    public RankEditorMenu(Player player) {
        super(player, "Rank Editor", 18);
    }

    @Override
    public Button[] getNavigationBar() {
        final Button[] buttons = super.getNavigationBar();

        buttons[4] = new Button(Material.NETHER_STAR)
                .setDisplayName(ChatColor.GREEN + "&aCreate Rank")
                .setClickAction(event -> promptHandler.startPrompt(this.getPlayer().getUniqueId(), new RankCreatePrompt(), this.rankHandler));

        return buttons;
    }

    @Override
    public void tick() {
        for (int i = 0; i < this.rankHandler.getRanks().size(); i++) {
            final Rank rank = this.rankHandler.getRanks().get(i);

            this.buttons[i] = new Button(Material.INK_SACK)
                    .setDisplayName(rank.getDisplayName())
                    .setAmount(rank.getWeight());
        }
    }
}