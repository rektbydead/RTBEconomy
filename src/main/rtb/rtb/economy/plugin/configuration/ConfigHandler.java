package rtb.economy.plugin.configuration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class ConfigHandler {
    private static final String MESSAGES_FILE_NAME = "messages.yml";
    private static final String DEFAULT_CONFIG_FILE_NAME = "config.yml";
    private static final String COMMANDS_CONFIG_FILE_NAME = "commands.yml";

    protected FileConfiguration defaultConfig;
    protected FileConfiguration messagesConfig;
    protected FileConfiguration commandsConfig;

    private Plugin instance;

    public ConfigHandler(Plugin instance) {
        this.instance = instance;

        this.createConfig();
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

        File commandsConfigFile = new File(instance.getDataFolder(), COMMANDS_CONFIG_FILE_NAME);
        if (!commandsConfigFile.exists()) {
            messagesConfigFile.getParentFile().mkdirs();
            instance.saveResource(COMMANDS_CONFIG_FILE_NAME, false);
        }

        defaultConfig = instance.getConfig();
        messagesConfig = YamlConfiguration.loadConfiguration(messagesConfigFile);
        commandsConfig = YamlConfiguration.loadConfiguration(commandsConfigFile);
    }

    public void reloadConfig() {
        instance.reloadConfig();

        File messagesConfigFile = new File(instance.getDataFolder(), MESSAGES_FILE_NAME);
        messagesConfig = YamlConfiguration.loadConfiguration(messagesConfigFile);

        File defaultConfigFile = new File(instance.getDataFolder(), DEFAULT_CONFIG_FILE_NAME);
        defaultConfig = YamlConfiguration.loadConfiguration(defaultConfigFile);

        File commandsConfigFile = new File(instance.getDataFolder(), COMMANDS_CONFIG_FILE_NAME);
        commandsConfig = YamlConfiguration.loadConfiguration(commandsConfigFile);
    }
}
