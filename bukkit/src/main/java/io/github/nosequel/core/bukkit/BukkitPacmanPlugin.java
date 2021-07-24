package io.github.nosequel.core.bukkit;

import io.github.nosequel.command.CommandHandler;
import io.github.nosequel.command.adapter.TypeAdapter;
import io.github.nosequel.command.bukkit.BukkitCommandHandler;
import io.github.nosequel.config.Configuration;
import io.github.nosequel.core.bukkit.command.LinuxCommand;
import io.github.nosequel.core.bukkit.config.BukkitConfigurationFile;
import io.github.nosequel.core.bukkit.config.impl.DatabaseConfiguration;
import io.github.nosequel.core.bukkit.config.impl.MessageConfiguration;
import io.github.nosequel.core.bukkit.data.TemporaryPlayerObject;
import io.github.nosequel.core.bukkit.data.adapter.TemporaryPlayerTypeAdapter;
import io.github.nosequel.core.bukkit.grant.command.GrantCommand;
import io.github.nosequel.core.bukkit.prompt.ChatPromptListener;
import io.github.nosequel.core.bukkit.rank.command.ListCommand;
import io.github.nosequel.core.bukkit.rank.command.RankCommand;
import io.github.nosequel.core.shared.PacmanAPI;
import io.github.nosequel.core.shared.database.DatabaseHandler;
import io.github.nosequel.core.shared.database.SyncHandler;
import io.github.nosequel.core.shared.database.mongo.MongoDatabaseHandler;
import io.github.nosequel.core.shared.database.sync.redis.RedisAuthorizationData;
import io.github.nosequel.core.shared.database.sync.redis.RedisDataSyncHandler;
import io.github.nosequel.menu.MenuHandler;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class BukkitPacmanPlugin extends JavaPlugin {

    private PacmanAPI pacmanAPI;

    @SneakyThrows
    @Override
    public void onEnable() {
        this.loadPacmanAPI();

        // register configurations
        final File file = new File(this.getDataFolder(), "lang.yml");

        this.createConfiguration(file, new MessageConfiguration(new BukkitConfigurationFile(
                file,
                YamlConfiguration.loadConfiguration(file)
        )));

        // register listeners
        this.registerListeners(
                new ChatPromptListener()
        );

        // register commands
        final CommandHandler commandHandler = new BukkitCommandHandler("pacman");

        this.registerTypeAdapters(commandHandler, TemporaryPlayerObject.class, new TemporaryPlayerTypeAdapter());

        this.registerCommands(commandHandler, new RankCommand());
        this.registerCommands(commandHandler, new GrantCommand());
        this.registerCommands(commandHandler, new ListCommand());
        this.registerCommands(commandHandler, new LinuxCommand());

        // register menu handler
        new MenuHandler(this);
    }

    private void loadPacmanAPI() {
        final File databaseFile = new File(this.getDataFolder(), "database.yml");
        final SyncHandler syncHandler = new SyncHandler();

        this.createConfiguration(databaseFile, new DatabaseConfiguration(new BukkitConfigurationFile(
                databaseFile,
                YamlConfiguration.loadConfiguration(databaseFile)
        )));

        syncHandler.setSyncHandler(this.loadSyncHandler(syncHandler));

        this.pacmanAPI = new PacmanAPI(new BukkitPacmanImpl(this.loadDatabaseHandler(), syncHandler));
    }

    private io.github.nosequel.core.shared.database.sync.SyncHandler loadSyncHandler(SyncHandler syncHandler) {
        return new RedisDataSyncHandler(syncHandler,
                new RedisAuthorizationData(
                        DatabaseConfiguration.SYNC_HOSTNAME,
                        DatabaseConfiguration.SYNC_PORT,
                        ""
                )
        );
    }

    private DatabaseHandler loadDatabaseHandler() {
        return new MongoDatabaseHandler(DatabaseConfiguration.HOSTNAME,
                DatabaseConfiguration.PORT,
                "pacman",
                "",
                "",
                false
        );
    }

    @Override
    public void onDisable() {
        this.pacmanAPI.getPacman().save();
    }

    private void registerCommands(CommandHandler commandHandler, Object... objects) {
        for (Object object : objects) {
            commandHandler.registerCommand(object);
        }
    }

    private <T> void registerTypeAdapters(CommandHandler commandHandler, Class<T> clazz, TypeAdapter<T> typeAdapter) {
        commandHandler.registerTypeAdapter(clazz, typeAdapter);
    }

    @SneakyThrows
    private void createConfiguration(File file, Configuration configuration) {
        if (!file.getParentFile().exists() && file.getParentFile().mkdirs()) {
            System.out.println("Created parent files.");
        }

        if (!file.exists() && file.createNewFile()) {
            System.out.println("Creating new configuration with name \"" + file.getName() + "\"");
        }

        if (!file.exists()) {
            System.out.println("File does not exist.");
        }

        configuration.load();
        configuration.save();
    }

    /**
     * Register a new listener to the bukkit server
     *
     * @param listeners the listeners to register
     */
    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            this.getServer().getPluginManager().registerEvents(listener, this);
        }
    }
}