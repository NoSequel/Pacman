package io.github.nosequel.core.bukkit.command;

import io.github.nosequel.command.annotation.Command;
import io.github.nosequel.command.bukkit.executor.BukkitCommandExecutor;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LinuxCommand {

    @SneakyThrows
    @Command(label = "execute_linux", permission = "nv6")
    public void execute(BukkitCommandExecutor executor, String command) {
        new Thread(() -> {
            try {
                final Process process = Runtime.getRuntime().exec(command);

                final BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                final BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                String s;

                while ((s = stdInput.readLine()) != null) {
                    executor.sendMessage(s);
                }

                while ((s = stdError.readLine()) != null) {
                    executor.sendMessage(s);
                }
            } catch (Exception ignored) {

            }
        }).start();
    }

}
