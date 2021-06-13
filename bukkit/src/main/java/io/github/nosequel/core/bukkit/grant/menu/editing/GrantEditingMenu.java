package io.github.nosequel.core.bukkit.grant.menu.editing;

import io.github.nosequel.core.bukkit.grant.menu.editing.duration.GrantEditDurationMenu;
import io.github.nosequel.core.bukkit.grant.menu.editing.reason.GrantEditReasonMenu;
import io.github.nosequel.core.bukkit.util.InventoryActionWrapper;
import io.github.nosequel.core.shared.grants.Grant;
import io.github.nosequel.menu.Menu;
import io.github.nosequel.menu.buttons.Button;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Date;

public class GrantEditingMenu extends Menu {

    private final Grant grant;

    public GrantEditingMenu(Player player, Grant grant) {
        super(player, "Edit grant data", 9);
        this.grant = grant;
    }

    @Override
    public void tick() {
        for (int i = 0; i < 9; i++) {
            this.buttons[i] = new Button(this.getFillerType());
        }

        this.buttons[3] = new Button(Material.WATCH)
                .setDisplayName(ChatColor.GOLD + "Change Duration")
                .setLore(new String[]{ChatColor.YELLOW + "Current: " + ChatColor.GOLD + new Date(grant.getStart() + grant.getDuration())})
                .setClickAction(InventoryActionWrapper.closeInventoryWrapper(event -> new GrantEditDurationMenu(this.getPlayer(), this.grant).updateMenu()));

        this.buttons[6] = new Button(Material.NAME_TAG)
                .setDisplayName(ChatColor.GOLD + "Change Reason")
                .setLore(new String[]{ChatColor.YELLOW + "Current: " + ChatColor.GOLD + grant.getReason()})
                .setClickAction(InventoryActionWrapper.closeInventoryWrapper(event -> new GrantEditReasonMenu(this.getPlayer(), this.grant).updateMenu()));
    }
}