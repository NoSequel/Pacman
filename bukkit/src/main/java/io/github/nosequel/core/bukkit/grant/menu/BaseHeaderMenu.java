package io.github.nosequel.core.bukkit.grant.menu;

import io.github.nosequel.menu.Menu;
import io.github.nosequel.menu.buttons.Button;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public abstract class BaseHeaderMenu extends Menu {

    /**
     * Constructor to make a new menu object
     *
     * @param player the player to create the menu for
     * @param title  the title to display at the top of the inventory
     * @param size   the size of the inventory
     */
    public BaseHeaderMenu(Player player, String title, int size) {
        super(player, title, size);
    }

    @Override
    public void tick() {
        for (int i = 0; i < 9; i++) {
            this.buttons[i] = new Button(this.getFillerType());
        }

        this.buttons[2] = new Button(Material.INK_SACK)
                .setData(DyeColor.GREEN.getDyeData())
                .setDisplayName(ChatColor.GREEN + "Confirm")
                .setClickAction(this.getAcceptAction());

        this.buttons[4] = new Button(Material.LEVER)
                .setDisplayName(ChatColor.GREEN + "Return")
                .setClickAction(event -> {
                    event.setCancelled(true);
                    this.getPreviousMenu().updateMenu();
                });

        this.buttons[6] = new Button(Material.INK_SACK)
                .setData(DyeColor.RED.getDyeData())
                .setDisplayName(this.getSecondDisplay())
                .setClickAction(this.getCancelAction());
    }

    /**
     * Get the action to perform whenever the confirm button is clicked
     *
     * @return the action
     */
    public abstract Consumer<InventoryClickEvent> getAcceptAction();

    /**
     * Get the action to perform whenever the cancel button is clicked
     *
     * @return the action
     */
    public abstract Consumer<InventoryClickEvent> getCancelAction();

    /**
     * Get the display name of the second item
     *
     * @return the message
     */
    public abstract String getSecondDisplay();

    /**
     * Get the action to perform whenever the previous button is clicked
     *
     * @return the action
     */
    public abstract Menu getPreviousMenu();

}
