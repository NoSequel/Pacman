package io.github.nosequel.core.bukkit.grant.menu.editing;

import io.github.nosequel.core.bukkit.data.TemporaryPlayerObject;
import io.github.nosequel.core.bukkit.util.InventoryActionWrapper;
import io.github.nosequel.core.shared.CoreAPI;
import io.github.nosequel.core.shared.expirable.ExpirationData;
import io.github.nosequel.core.shared.grants.Grant;
import io.github.nosequel.core.shared.grants.GrantHandler;
import io.github.nosequel.core.shared.rank.Rank;
import io.github.nosequel.menu.buttons.Button;
import io.github.nosequel.menu.pagination.PaginatedMenu;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class GrantsMenu extends PaginatedMenu {

    private final TemporaryPlayerObject target;
    private final GrantHandler grantHandler = CoreAPI.getCoreAPI().getGrantHandler();

    public GrantsMenu(Player player, TemporaryPlayerObject target) {
        super(player, "Viewing grants", 18);
        this.target = target;
    }

    @Override
    public void tick() {
        final List<Grant> grants = new ArrayList<>(this.grantHandler.findGrants(target.getUniqueId()));

        for (int i = 0; i < grants.size(); i++) {
            final Grant grant = grants.get(i);

            this.buttons[i] = new Button(Material.WOOL)
                    .setLore(this.getLore(grant))
                    .setDisplayName(ChatColor.RED + grant.getUniqueId().toString().substring(0, 8))
                    .setData(grant.isActive() ? DyeColor.GREEN.getWoolData() : DyeColor.RED.getWoolData())
                    .setClickAction(InventoryActionWrapper.closeInventoryWrapper(event -> new GrantEditingMenu(this.getPlayer(), grant).updateMenu()));
        }
    }

    private String[] getLore(Grant grant) {
        final Rank rank = grant.getRank();
        final List<String> strings = new ArrayList<>(Arrays.asList(
                ChatColor.GOLD + ChatColor.STRIKETHROUGH.toString() + StringUtils.repeat("-", 20),
                ChatColor.YELLOW + "Rank: " + ChatColor.GOLD + rank.getDisplayName(),
                ChatColor.YELLOW + "Executor: " + ChatColor.GOLD + this.target.getName(),
                ChatColor.YELLOW + "Reason: " + ChatColor.GOLD + grant.getReason(),
                "",
                ChatColor.YELLOW + "Starting Date: " + ChatColor.GOLD + new Date(grant.getStart()),
                ChatColor.YELLOW + "Expiration Date: " + ChatColor.GOLD + new Date(grant.getDuration() + grant.getStart()),
                ChatColor.GOLD + ChatColor.STRIKETHROUGH.toString() + StringUtils.repeat("-", 20)
        ));

        if (!grant.isActive()) {
            final ExpirationData expirationData = grant.getExpirationData();

            if (expirationData != null) {
                strings.addAll(Arrays.asList(
                        ChatColor.YELLOW + "Expiration Reason: " + ChatColor.GOLD + expirationData.getReason(),
                        ChatColor.YELLOW + "Expiration Date: " + ChatColor.GOLD + new Date(expirationData.getDate()),
                        ChatColor.GOLD + ChatColor.STRIKETHROUGH.toString() + StringUtils.repeat("-", 20)
                ));
            }
        }

        return strings.toArray(new String[0]);
    }
}