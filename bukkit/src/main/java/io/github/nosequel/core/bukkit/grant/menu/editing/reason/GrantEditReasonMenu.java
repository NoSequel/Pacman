package io.github.nosequel.core.bukkit.grant.menu.editing.reason;

import io.github.nosequel.core.bukkit.grant.menu.BaseHeaderMenu;
import io.github.nosequel.core.bukkit.grant.menu.editing.GrantEditingMenu;
import io.github.nosequel.core.bukkit.grant.menu.reason.GrantReasonType;
import io.github.nosequel.core.shared.grants.Grant;
import io.github.nosequel.menu.Menu;
import io.github.nosequel.menu.buttons.Button;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class GrantEditReasonMenu extends BaseHeaderMenu {

    private String reason;
    private final Grant grant;

    public GrantEditReasonMenu(Player player, Grant grant) {
        super(player, "Select a grant reason", 9*3);

        this.reason = grant.getReason();
        this.grant = grant;
    }

    @Override
    public void tick() {
        super.tick();

        for (int i = 0; i < GrantReasonType.values().length; i++) {
            final GrantReasonType reasonType = GrantReasonType.values()[i];

            final Consumer<InventoryClickEvent> action = event -> {
                event.setCancelled(true);

                this.reason = reasonType.getDisplay();
                this.updateMenu();
            };

            this.buttons[i + 9] = new Button(Material.WATCH)
                    .setLore(this.getLore(reasonType))
                    .setDisplayName(ChatColor.GOLD + reasonType.getDisplay())
                    .setClickAction(action);
        }
    }

    /**
     * Get the lore to display inside of the menu
     *
     * @param reasonType the type of the reason
     * @return the lore
     */
    private String[] getLore(GrantReasonType reasonType) {
        return new String[]{
                ChatColor.YELLOW + "Click to set the reason to " + ChatColor.GOLD + reasonType.getDisplay(),
        };
    }

    /**
     * Get the action to perform whenever the confirm button is clicked
     *
     * @return the action
     */
    @Override
    public Consumer<InventoryClickEvent> getAcceptAction() {
        return event -> {
            event.setCancelled(true);

            this.grant.setReason(this.reason);
            this.getPlayer().closeInventory();
        };
    }

    /**
     * Get the action to perform whenever the cancel button is clicked
     *
     * @return the action
     */
    @Override
    public Consumer<InventoryClickEvent> getCancelAction() {
        return event -> {
            event.setCancelled(true);

            this.getPlayer().closeInventory();
        };
    }

    /**
     * Get the display name of the second item
     *
     * @return the message
     */
    @Override
    public String getSecondDisplay() {
        return ChatColor.RED + "Cancel";
    }

    /**
     * Get the action to perform whenever the previous button is clicked
     *
     * @return the action
     */
    @Override
    public Menu getPreviousMenu() {
        return new GrantEditingMenu(this.getPlayer(), this.grant);
    }
}
