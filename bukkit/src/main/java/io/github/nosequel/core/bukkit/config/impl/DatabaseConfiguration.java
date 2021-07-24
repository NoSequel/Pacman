package io.github.nosequel.core.bukkit.config.impl;

import io.github.nosequel.config.Configuration;
import io.github.nosequel.config.ConfigurationFile;
import io.github.nosequel.config.annotation.Configurable;

public class DatabaseConfiguration extends Configuration {

    @Configurable(path = "database.sync.hostname")
    public static String SYNC_HOSTNAME = "127.0.0.1";

    @Configurable(path = "database.sync.port")
    public static Integer SYNC_PORT = 6379;

    @Configurable(path = "database.hostname")
    public static String HOSTNAME = "127.0.0.1";

    @Configurable(path = "database.port")
    public static Integer PORT = 27017;

    public DatabaseConfiguration(ConfigurationFile file) {
        super(file);
    }
}
