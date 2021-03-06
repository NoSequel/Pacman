package io.github.nosequel.core.bukkit.util;

import io.github.nosequel.core.shared.rank.Rank;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@UtilityClass
public class ColorUtil {

    private final Map<ChatColor, DyeColor> colorMap = new HashMap<ChatColor, DyeColor>() {{
        put(ChatColor.BLUE, DyeColor.BLUE);
        put(ChatColor.AQUA, DyeColor.CYAN);
        put(ChatColor.BLACK, DyeColor.BLACK);
        put(ChatColor.DARK_AQUA, DyeColor.CYAN);
        put(ChatColor.DARK_BLUE, DyeColor.BLUE);
        put(ChatColor.DARK_GREEN, DyeColor.GREEN);
        put(ChatColor.GREEN, DyeColor.LIME);
        put(ChatColor.DARK_PURPLE, DyeColor.PURPLE);
        put(ChatColor.LIGHT_PURPLE, DyeColor.PINK);
        put(ChatColor.RED, DyeColor.RED);
        put(ChatColor.DARK_RED, DyeColor.RED);
        put(ChatColor.YELLOW, DyeColor.YELLOW);
        put(ChatColor.WHITE, DyeColor.WHITE);
        put(ChatColor.GOLD, DyeColor.ORANGE);
        put(ChatColor.GRAY, DyeColor.GRAY);
        put(ChatColor.DARK_GRAY, DyeColor.GRAY);
    }};

    public String getColoredName(UUID target) {
        return JavaUtil.ifPresentOrElse(Optional.ofNullable(Bukkit.getOfflinePlayer(target)), OfflinePlayer::getName, player -> ChatColor.DARK_RED + "SERVER");
    }

    /**
     * Translate a string's colors to bukkit's '&' char
     *
     * @param string the string to translate
     * @return the translated string
     */
    public String translate(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    /**
     * Get a {@link ChatColor} object by a {@link Rank}
     *
     * @param rank the rank to get the color by
     * @return the color
     */
    public ChatColor getColorByRank(Rank rank) {
        return ChatColor.getByChar(rank.getColor().toCharArray()[1]);
    }

    /**
     * Find a dye color in the color map
     *
     * @param color the color to get the dye color
     * @return the dye color
     */
    public DyeColor findDyeColor(ChatColor color) {
        return colorMap.getOrDefault(color, DyeColor.WHITE);
    }
}