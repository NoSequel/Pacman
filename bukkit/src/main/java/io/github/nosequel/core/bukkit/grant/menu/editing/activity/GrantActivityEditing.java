package io.github.nosequel.core.bukkit.grant.menu.editing.activity;

import io.github.nosequel.core.bukkit.util.InventoryActionWrapper;
import io.github.nosequel.core.shared.grants.Grant;
import io.github.nosequel.menu.Menu;
import io.github.nosequel.menu.MenuType;
import io.github.nosequel.menu.buttons.Button;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class GrantActivityEditing extends Menu {

    private final Grant grant;

    public GrantActivityEditing(Player player, Grant grant) {
        super(player, "Editing rank activity", 3);

        this.grant = grant;
        this.setMenuType(MenuType.BREWING_STAND);
    }

    @Override
    public void tick() {
        this.buttons[1] = new Button(Material.INK_SACK)
                .setData((grant.isActive() ? DyeColor.GREEN : DyeColor.RED).getDyeData())
                .setDisplayName(ChatColor.GOLD + "Change Activity")
                .setLore(new String[]{ChatColor.YELLOW + "Current: " + ChatColor.GOLD + grant.isActive()})
                .setClickAction(InventoryActionWrapper.closeInventoryWrapper(event -> grant.handleActivityToggle()));
    }
}
