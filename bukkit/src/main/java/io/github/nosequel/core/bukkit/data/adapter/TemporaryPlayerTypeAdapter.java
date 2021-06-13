package io.github.nosequel.core.bukkit.data.adapter;

import io.github.nosequel.command.adapter.TypeAdapter;
import io.github.nosequel.command.executor.CommandExecutor;
import io.github.nosequel.core.bukkit.data.TemporaryPlayerObject;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class TemporaryPlayerTypeAdapter implements TypeAdapter<TemporaryPlayerObject> {

    @Override
    public TemporaryPlayerObject convert(CommandExecutor executor, String source) throws Exception {
        final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(source);

        return new TemporaryPlayerObject(
                offlinePlayer.getName(),
                offlinePlayer.getUniqueId()
        );
    }
}