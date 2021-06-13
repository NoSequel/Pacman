package io.github.nosequel.core.bukkit.grant.menu.editing.duration;

import io.github.nosequel.core.bukkit.grant.menu.BaseHeaderMenu;
import io.github.nosequel.core.bukkit.grant.menu.duration.GrantDurationType;
import io.github.nosequel.core.bukkit.grant.menu.editing.GrantEditingMenu;
import io.github.nosequel.core.shared.grants.Grant;
import io.github.nosequel.menu.Menu;
import io.github.nosequel.menu.buttons.Button;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

public class GrantEditDurationMenu extends BaseHeaderMenu {

    private long currentDuration;
    private final Grant grant;

    public GrantEditDurationMenu(Player player, Grant grant) {
        super(player, "Grant time duration", 18);
        this.grant = grant;
    }

    @Override
    public void tick() {
        super.tick();

        for (int i = 0; i < GrantDurationType.values().length; i++) {
            final GrantDurationType durationType = GrantDurationType.values()[i];

            final Consumer<InventoryClickEvent> action = event -> {
                event.setCancelled(true);

                this.currentDuration += durationType.getDuration();
                this.updateMenu();
            };

            this.buttons[i + 9] = new Button(Material.WATCH)
                    .setLore(this.getLore())
                    .setDisplayName(ChatColor.GOLD + durationType.getDisplay())
                    .setClickAction(action);
        }
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
            event.getWhoClicked().closeInventory();

            this.grant.setStart(System.currentTimeMillis());
            this.grant.setDuration(this.currentDuration);
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
            this.currentDuration = -1L;
        };
    }

    /**
     * Get the display name of the second item
     *
     * @return the message
     */
    @Override
    public String getSecondDisplay() {
        return ChatColor.RED + "Permanent";
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

    /**
     * Get the lore to display inside of the menu
     *
     * @return the lore
     */
    private String[] getLore() {
        final Date date = new Date(System.currentTimeMillis() + this.currentDuration);
        final DateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

        return new String[]{
                ChatColor.GRAY + "( Left | Right )",
                ChatColor.GREEN + ChatColor.BOLD.toString() + "  +1 " + ChatColor.RED + ChatColor.BOLD + " -1 ",
                "",
                ChatColor.YELLOW + "Current duration will last until " + ChatColor.GOLD + format.format(date),
        };
    }
}