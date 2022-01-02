package dev.floffah.plugin.fancynpcutils;

import dev.floffah.plugin.fancynpcutils.command.FNPCCommand;
import dev.floffah.plugin.fancynpcutils.config.ConfigManager;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class FancyNPCUtils extends JavaPlugin {
    public ConfigManager config;
    public Manager manager;

    @Override
    public void onEnable() {
        this.config = new ConfigManager(this);
        this.manager = new Manager(this);
        this.manager.loadAll();

        PluginCommand fnpcPluginCommand = this.getCommand("fnpc");
        FNPCCommand fnpcCommand = new FNPCCommand(this);
        fnpcPluginCommand.setExecutor(fnpcCommand);
        fnpcPluginCommand.setTabCompleter(fnpcCommand);
    }

    @Override
    public void onDisable() {
        this.manager.unloadAll();
    }
}
