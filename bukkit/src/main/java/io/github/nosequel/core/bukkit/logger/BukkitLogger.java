package io.github.nosequel.core.bukkit.logger;

import io.github.nosequel.core.shared.logger.Logger;
import org.bukkit.Bukkit;

import java.util.logging.Level;

public class BukkitLogger implements Logger {

    /**
     * Print a warning to the console
     *
     * @param string the string to print
     */
    @Override
    public void warning(String string) {
        Bukkit.getLogger().log(Level.WARNING, string);
    }

    /**
     * Print info to the console
     *
     * @param string the string to print
     */
    @Override
    public void info(String string) {
        Bukkit.getLogger().log(Level.INFO, string);
    }

    /**
     * Print an error to the console
     *
     * @param string the string to print
     */
    @Override
    public void error(String string) {
        Bukkit.getLogger().log(Level.SEVERE, string);
    }
}