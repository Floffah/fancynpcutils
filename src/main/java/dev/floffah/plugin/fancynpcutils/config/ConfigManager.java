package dev.floffah.plugin.fancynpcutils.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import dev.floffah.plugin.fancynpcutils.FancyNPCUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;

public class ConfigManager {
    public Config config;
    private final FancyNPCUtils plugin;
    private File configFile;

    private ObjectMapper om;

    public ConfigManager(FancyNPCUtils plugin) {
        this.plugin = plugin;

        try {
            this.initConfig();
        } catch (IOException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Could not initialise FancyNPCUtils", e);
            this.plugin.getServer().getPluginManager().disablePlugin(this.plugin);
            return;
        }
    }

    public void initConfig() throws IOException {
        this.configFile = new File(Path.of(this.plugin.getDataFolder().getPath(), "config.yml").toUri());
        this.om = new ObjectMapper(new YAMLFactory());

        if (!this.plugin.getDataFolder().exists()) this.plugin.getDataFolder().mkdirs();
        if (!this.configFile.exists()) {
            this.config = new Config();
            this.writeConfig();
        } else {
            this.readConfig();
        }
    }

    public void readConfig() throws IOException {
        this.config = this.om.readValue(this.configFile, Config.class);
    }

    public void writeConfig() throws IOException {
        this.om.writeValue(this.configFile, this.config);
    }
}
