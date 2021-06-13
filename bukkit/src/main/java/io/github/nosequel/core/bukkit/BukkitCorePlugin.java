package io.github.nosequel.core.bukkit;

import io.github.nosequel.command.CommandHandler;
import io.github.nosequel.command.adapter.TypeAdapter;
import io.github.nosequel.command.bukkit.BukkitCommandHandler;
import io.github.nosequel.core.bukkit.data.TemporaryPlayerObject;
import io.github.nosequel.core.bukkit.data.adapter.TemporaryPlayerTypeAdapter;
import io.github.nosequel.core.bukkit.grant.command.GrantCommand;
import io.github.nosequel.core.bukkit.logger.BukkitLogger;
import io.github.nosequel.core.bukkit.prompt.BukkitChatPromptHandler;
import io.github.nosequel.core.bukkit.prompt.ChatPromptListener;
import io.github.nosequel.core.bukkit.rank.command.RankCommand;
import io.github.nosequel.core.shared.CoreAPI;
import io.github.nosequel.menu.MenuHandler;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitCorePlugin extends JavaPlugin {

    private final CoreAPI coreAPI = new CoreAPI();

    @Override
    public void onEnable() {
        this.coreAPI.setPromptHandler(new BukkitChatPromptHandler());
        this.coreAPI.setLogger(new BukkitLogger());

        this.coreAPI.enable();

        // register listeners
        this.registerListeners(
                new ChatPromptListener()
        );

        // register commands
        final CommandHandler commandHandler = new BukkitCommandHandler("pacman");

        this.registerTypeAdapters(commandHandler, TemporaryPlayerObject.class, new TemporaryPlayerTypeAdapter());

        this.registerCommands(commandHandler, new RankCommand());
        this.registerCommands(commandHandler, new GrantCommand());

        // register menu handler
        new MenuHandler(this);
    }

    @Override
    public void onDisable() {
        this.coreAPI.disable();
    }

    private void registerCommands(CommandHandler commandHandler, Object... objects) {
        for (Object object : objects) {
            commandHandler.registerCommand(object);
        }
    }

    private <T> void registerTypeAdapters(CommandHandler commandHandler, Class<T> clazz, TypeAdapter<T> typeAdapter) {
        commandHandler.registerTypeAdapter(clazz, typeAdapter);
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