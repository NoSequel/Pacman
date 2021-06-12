package io.github.nosequel.core.bukkit.rank.menu.editor;

import io.github.nosequel.core.shared.rank.Rank;
import io.github.nosequel.menu.Menu;
import io.github.nosequel.menu.buttons.Button;
import org.bukkit.entity.Player;

public class RankEditorMenu extends Menu {

    private final Rank rank;

    public RankEditorMenu(Player player, Rank rank) {
        super(player, "Editing a rank...", 9);
        this.rank = rank;
    }

    @Override
    public void tick() {
        for (int i = 0; i < RankEditorType.values().length; i++) {
            final RankEditorType type = RankEditorType.values()[i];

            this.buttons[i] = new Button(type.getMaterial())
                    .setDisplayName(type.getDisplayName())
                    .setLore(type.getLore(this.rank))
                    .setClickAction(type.getAction(rank, this));
        }
    }
}