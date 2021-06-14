package io.github.nosequel.core.bukkit.config;

import io.github.nosequel.config.ConfigurationFile;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

@RequiredArgsConstructor
public class BukkitConfigurationFile implements ConfigurationFile {

    private final File file;
    private final YamlConfiguration configuration;

    @SneakyThrows
    @Override
    public void load() {
        if(!this.file.exists()) {
            this.file.mkdir();
        }

        this.configuration.load(this.file);
    }

    @SneakyThrows
    @Override
    public void save() {
        if(!this.file.exists()) {
            this.file.mkdir();
        }

        this.configuration.save(this.file);
    }

    /**
     * Set an object in the configuration
     *
     * @param path   the path to set the value to
     * @param object the value to set it to
     */
    @Override
    public <T> void set(String path, T object) {
        this.configuration.set(path, object);
    }

    /**
     * Get the string from a path
     *
     * @param path the path to get the string from
     * @return the string, or null
     */
    @Override
    public String get(String path) {
        return this.configuration.getString(path);
    }
}