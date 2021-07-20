package rtb.economy.plugin.configuration.handlers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class ConfigHandler {
    private static final String MESSAGES_FILE_NAME = "messages.yml";
    private static final String DEFAULT_CONFIG_FILE_NAME = "config.yml";

    private Plugin instance;

    protected FileConfiguration defaultConfig;
    protected FileConfiguration messagesConfig;


    public ConfigHandler(Plugin instance) {
        this.instance = instance;
        createConfig();
    }

    private void createConfig() {
        File configFile = new File(instance.getDataFolder(), DEFAULT_CONFIG_FILE_NAME);
        if (!configFile.exists()) {
            instance.saveDefaultConfig();
        }

        File messagesConfigFile = new File(instance.getDataFolder(), MESSAGES_FILE_NAME);
        if (!messagesConfigFile.exists()) {
            messagesConfigFile.getParentFile().mkdirs();
            instance.saveResource(MESSAGES_FILE_NAME, false);
        }

        defaultConfig = instance.getConfig();
        messagesConfig = YamlConfiguration.loadConfiguration(messagesConfigFile);
    }
}
