package com.example.customgamma;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class CustomGammaPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        registerCommands();
        getLogger().info("CustomGamma включен.");
    }

    @Override
    public void onDisable() {
        getLogger().info("CustomGamma выключен.");
    }

    private void registerCommands() {
        GammaCommand gammaCommand = new GammaCommand(this);
        @NotNull var command = getCommand("gamma");
        if (command != null) {
            command.setExecutor(gammaCommand);
        } else {
            getLogger().severe("Команда 'gamma' не найдена в plugin.yml!");
        }
    }
}
