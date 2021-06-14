package io.github.nosequel.core.bukkit.config.impl;

import io.github.nosequel.config.Configuration;
import io.github.nosequel.config.ConfigurationFile;
import io.github.nosequel.config.annotation.Configurable;
import lombok.Getter;
import lombok.SneakyThrows;

@Getter
public class MessageConfiguration extends Configuration {

    @Configurable(path = "messages.ranks.creation.started")
    public static String RANK_START_CREATING = "&ePlease type a name for the rank to start with the process.";

    @Configurable(path = "messages.ranks.creation.created")
    public static String RANK_CREATED = "&eYou have &acreated &ethe &f$rank &erank, opening a new menu to edit this rank.";

    @Configurable(path = "messages.rank.color.started")
    public static String RANK_START_SETTING_COLOR = "&eType the prefix to update the rank's color value to in the chat.";

    @Configurable(path = "messages.rank.color.set")
    public static String RANK_SET_COLOR = "&eYou have set the color of the &f$rank rank &eto &f$color";

    @SneakyThrows
    public MessageConfiguration(ConfigurationFile file) {
        super(file);
    }
}