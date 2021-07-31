package io.github.nosequel.core.bukkit.config.impl;

import io.github.nosequel.config.Configuration;
import io.github.nosequel.config.ConfigurationFile;
import io.github.nosequel.config.annotation.Configurable;
import lombok.Getter;
import lombok.SneakyThrows;

@Getter
public class MessageConfiguration extends Configuration {

    @Configurable(path = "punishments.kick_message")
    public static String PUNISHMENT_KICK_MESSAGE = "&eYou are currently banned for violating our terms of service," +
            "\n&eReason: &d$reason" +
            "\n" +
            "\n" +
            "&7You can appeal your punishment, or purchase an unban.";

    @Configurable(path = "punishments.issue.permanent")
    public static String PUNISHMENT_ISSUE_MESSAGE = "&f$target &ahas been $fancy_punishment_type by &f$executor";

    @Configurable(path = "punishments.issue.temporary")
    public static String PUNISHMENT_ISSUE_TEMPORARY_MESSAGE = "&f$target &ahas been &etemporarily &a$fancy_punishment_type by &f$executor";

    @Configurable(path = "punishments.silent_prefix")
    public static String PUNISHMENT_SILENT_PREFIX = "&7(Silent) ";

    @Configurable(path = "messages.ranks.creation.started")
    public static String RANK_START_CREATING = "&ePlease type a name for the rank to start with the process.";

    @Configurable(path = "messages.ranks.creation.created")
    public static String RANK_CREATED = "&eYou have &acreated &ethe &f$rank &erank, opening a new menu to edit this rank.";

    @Configurable(path = "messages.ranks.deleted")
    public static String RANK_DELETED = "&eYou have &cdeleted &ethe &f$rank &erank.";

    @Configurable(path = "messages.ranks.color.started")
    public static String RANK_START_SETTING_COLOR = "&eType the prefix to update the rank's color value to in the chat.";

    @Configurable(path = "messages.ranks.color.set")
    public static String RANK_SET_COLOR = "&eYou have set the color of the &f$rank rank &eto &f$color";

    @SneakyThrows
    public MessageConfiguration(ConfigurationFile file) {
        super(file);
    }
}