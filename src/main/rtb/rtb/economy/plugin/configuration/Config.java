package rtb.economy.plugin.configuration;

import org.bukkit.plugin.Plugin;

import java.util.List;

public class Config extends ConfigHandler {

    private static Plugin pluginInstance;
    private static Config instance;

    public static void setPluginInstance(Plugin plugin) {
        pluginInstance = plugin;
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config(pluginInstance);
        }

        return instance;
    }

    private Config(Plugin plugin) {
        super(plugin);
    }

    public boolean getConfigBoolean(String value) { return defaultConfig.getBoolean(value); }

    public String getConfigString(String value) { return defaultConfig.getString(value).replace("&", "§"); }

    public int getConfigInt(String value) { return defaultConfig.getInt(value); }

    public String getMessage(String value) {
        return messagesConfig.getString(value).replace("&", "§");
    }
    
    public String getCommandString(String value) {
        return commandsConfig.getString(value).replace("&", "§");
    }

    public List<String> getCommandsList(String path) {
        List<String> list = commandsConfig.getStringList(path);
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i).replace("&", "§"));
        }

        return list;
    }
    
    public List<String> getConfigList(String path) {
        List<String> list = defaultConfig.getStringList(path);
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i).replace("&", "§"));
        }

        return list;
    }
}
