package io.github.nosequel.core.bukkit.grant.menu;

import io.github.nosequel.core.bukkit.data.TemporaryPlayerObject;
import io.github.nosequel.core.bukkit.grant.menu.duration.GrantDurationMenu;
import io.github.nosequel.core.bukkit.util.ColorUtil;
import io.github.nosequel.core.shared.CoreAPI;
import io.github.nosequel.core.shared.rank.Rank;
import io.github.nosequel.core.shared.rank.RankHandler;
import io.github.nosequel.core.shared.rank.metadata.Metadata;
import io.github.nosequel.menu.buttons.Button;
import io.github.nosequel.menu.pagination.PaginatedMenu;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;
import java.util.function.Consumer;

public class GrantMenu extends PaginatedMenu {

    private final TemporaryPlayerObject target;
    private final RankHandler rankHandler = CoreAPI.getCoreAPI().getRankHandler();

    public GrantMenu(Player player, TemporaryPlayerObject target) {
        super(player, "Granting to player...", 18);
        this.target = target;
    }

    @Override
    public void tick() {
        final List<Rank> ranks = rankHandler.getRanks();

        for (int i = 0; i < ranks.size(); i++) {
            final Rank rank = ranks.get(i);
            final ChatColor color = ColorUtil.getColorByRank(rank);

            final Consumer<InventoryClickEvent> action = event -> {
                event.setCancelled(true);

                this.getPlayer().closeInventory();
                new GrantDurationMenu(this.getPlayer(), this.target, rank).updateMenu();
            };

            this.buttons[i] = new Button(Material.WOOL)
                    .setData(ColorUtil.findDyeColor(color).getWoolData())
                    .setDisplayName(rank.getDisplayName())
                    .setClickAction(action)
                    .setLore(this.getRankLore(rank));
        }
    }

    private String[] getRankLore(Rank rank) {
        return new String[]{
                ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + StringUtils.repeat("-", 32),
                ChatColor.YELLOW + "Priority: " + ChatColor.GOLD + rank.getWeight(),
                ChatColor.YELLOW + "Visible: " + ChatColor.GOLD + !rank.hasMetadata(Metadata.HIDDEN),
                ChatColor.YELLOW + "Prefix: " + ChatColor.GOLD + rank.getPrefix(),
                "",
                ChatColor.GREEN + "Left-click to grant the " + rank.getDisplayName() + " rank.",
                ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + StringUtils.repeat("-", 32),
        };
    }

}
