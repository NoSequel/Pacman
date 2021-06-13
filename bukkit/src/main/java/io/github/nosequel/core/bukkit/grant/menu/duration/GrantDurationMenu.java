package io.github.nosequel.core.bukkit.grant.menu.duration;

import io.github.nosequel.core.bukkit.data.TemporaryPlayerObject;
import io.github.nosequel.core.bukkit.grant.menu.BaseHeaderMenu;
import io.github.nosequel.core.bukkit.grant.menu.GrantMenu;
import io.github.nosequel.core.bukkit.grant.menu.reason.GrantReasonMenu;
import io.github.nosequel.core.shared.rank.Rank;
import io.github.nosequel.menu.Menu;
import io.github.nosequel.menu.buttons.Button;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

public class GrantDurationMenu extends BaseHeaderMenu {

    private long currentDuration;

    private final TemporaryPlayerObject target;
    private final Rank rank;

    public GrantDurationMenu(Player player, TemporaryPlayerObject target, Rank rank) {
        super(player, "Grant time duration", 18);

        this.target = target;
        this.rank = rank;
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

            new GrantReasonMenu(this.getPlayer(), this.target, this.rank, this.currentDuration).updateMenu();
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

            new GrantReasonMenu(this.getPlayer(), this.target, this.rank, this.currentDuration).updateMenu();
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
        return new GrantMenu(this.getPlayer(), this.target);
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