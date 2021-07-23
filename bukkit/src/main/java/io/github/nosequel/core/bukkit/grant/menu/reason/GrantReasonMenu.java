package io.github.nosequel.core.bukkit.grant.menu.reason;

import io.github.nosequel.core.bukkit.data.TemporaryPlayerObject;
import io.github.nosequel.core.bukkit.grant.menu.BaseHeaderMenu;
import io.github.nosequel.core.bukkit.grant.menu.duration.GrantDurationMenu;
import io.github.nosequel.core.shared.PacmanAPI;
import io.github.nosequel.core.shared.grants.Grant;
import io.github.nosequel.core.shared.grants.GrantHandler;
import io.github.nosequel.core.shared.rank.Rank;
import io.github.nosequel.menu.Menu;
import io.github.nosequel.menu.buttons.Button;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.UUID;
import java.util.function.Consumer;

public class GrantReasonMenu extends BaseHeaderMenu {

    private String reason;

    private final Rank rank;
    private final long duration;

    private final TemporaryPlayerObject target;

    private final GrantHandler grantHandler = PacmanAPI.getPacmanAPI().getGrantHandler();

    public GrantReasonMenu(Player player, TemporaryPlayerObject target, Rank rank, long duration) {
        super(player, "Select a grant reason", 9*3);

        this.target = target;
        this.rank = rank;
        this.duration = duration;
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

            this.grantHandler.register(new Grant(
                    this.target.getUniqueId(),
                    this.rank.getUniqueId(),
                    UUID.randomUUID(),
                    this.getPlayer().getUniqueId(),
                    this.reason,
                    this.duration
            ));

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
        return new GrantDurationMenu(this.getPlayer(), this.target, rank);
    }
}
