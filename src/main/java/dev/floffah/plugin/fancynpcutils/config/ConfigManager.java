package dev.floffah.plugin.fancynpcutils.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import dev.floffah.plugin.fancynpcutils.FancyNPCUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;

public class ConfigManager {
    private final FancyNPCUtils plugin;
    public Config config;
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

        YAMLFactory yf = new YAMLFactory();
//        yf.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);

        this.om = new ObjectMapper(yf);

//        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
//                .allowIfSubType("java.util.ArrayList")
//                .allowIfSubType("dev.floffah.plugin.fancynpcutils.config")
//                .build();
//        this.om.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL);

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
