package io.github.nosequel.core.bukkit.util;

import io.github.nosequel.menu.Menu;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

@UtilityClass
public class InventoryActionWrapper {

    /**
     * Create a new wrapped method to close an inventory automatically
     * after clicking on the button.
     *
     * @param consumer the consumer to accept
     * @return the new action
     */
    public Consumer<InventoryClickEvent> closeInventoryWrapper(Consumer<InventoryClickEvent> consumer) {
        return event -> {
            event.setCancelled(true);
            event.getWhoClicked().closeInventory();

            consumer.accept(event);
        };
    }


    public Consumer<InventoryClickEvent> updateInventoryWrapper(Consumer<InventoryClickEvent> consumer, Menu menu) {
        return event -> {
            event.setCancelled(true);

            menu.updateMenu();
            consumer.accept(event);
        };
    }
}