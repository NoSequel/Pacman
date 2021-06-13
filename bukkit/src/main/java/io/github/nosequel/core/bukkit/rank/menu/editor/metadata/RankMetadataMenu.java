package io.github.nosequel.core.bukkit.rank.menu.editor.metadata;

import io.github.nosequel.core.bukkit.rank.menu.editor.RankEditorMenu;
import io.github.nosequel.core.bukkit.util.InventoryActionWrapper;
import io.github.nosequel.core.shared.rank.Rank;
import io.github.nosequel.core.shared.rank.metadata.Metadata;
import io.github.nosequel.menu.Menu;
import io.github.nosequel.menu.buttons.Button;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class RankMetadataMenu extends Menu {

    private final Rank rank;

    public RankMetadataMenu(Player player, Rank rank) {
        super(player, "Edit rank's metadata", 18);
        this.rank = rank;
    }

    @Override
    public void tick() {
        this.buttons[0] = new Button(Material.LEVER)
                .setDisplayName(ChatColor.RED + "Go Back")
                .setClickAction(InventoryActionWrapper.closeInventoryWrapper(event -> new RankEditorMenu(getPlayer(), rank).updateMenu()));

        for (int i = 0; i < Metadata.values().length; i++) {
            final Metadata metadata = Metadata.values()[i];
            final ItemStack itemStack = new ItemStack(Material.getMaterial(metadata.getDisplayItem()));

            final Consumer<InventoryClickEvent> action = event -> {
                event.setCancelled(true);

                if (rank.hasMetadata(metadata)) {
                    rank.getMetadatum().remove(metadata);
                } else {
                    rank.getMetadatum().add(metadata);
                }

                this.updateMenu();
            };

            if (rank.hasMetadata(metadata)) {
                itemStack.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
            }

            this.buttons[i + 9] = new Button(itemStack)
                    .setDisplayName(metadata.getDisplayName())
                    .setClickAction(action);
        }
    }
}