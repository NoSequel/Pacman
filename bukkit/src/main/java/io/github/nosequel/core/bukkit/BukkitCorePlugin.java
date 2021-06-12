package io.github.nosequel.core.bukkit;

import io.github.nosequel.core.bukkit.logger.BukkitLogger;
import io.github.nosequel.core.bukkit.prompt.BukkitChatPromptHandler;
import io.github.nosequel.core.bukkit.prompt.ChatPromptListener;
import io.github.nosequel.core.bukkit.rank.command.RankCommand;
import io.github.nosequel.core.shared.CoreAPI;
import me.blazingtide.zetsu.Zetsu;
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
        new Zetsu(this).registerCommands(new RankCommand());
    }

    @Override
    public void onDisable() {
        this.coreAPI.disable();
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