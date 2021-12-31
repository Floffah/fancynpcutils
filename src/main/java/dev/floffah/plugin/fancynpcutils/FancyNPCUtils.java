package dev.floffah.plugin.fancynpcutils;

import dev.floffah.plugin.fancynpcutils.config.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class FancyNPCUtils extends JavaPlugin {
    public ConfigManager config;
    public Manager manager;

    @Override
    public void onEnable() {
        this.config = new ConfigManager(this);
        this.manager = new Manager(this);
        this.manager.loadAll();
    }

    @Override
    public void onDisable() {
        this.manager.unloadAll();
    }
}
