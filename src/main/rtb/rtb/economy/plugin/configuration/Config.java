package rtb.economy.plugin.configuration;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import rtb.economy.plugin.configuration.handlers.ConfigHandler;

import java.util.List;

public class Config extends ConfigHandler {

    Cache<String> messages;
    Cache<Object> objectCache;

    public Config(Plugin instance) {
        super(instance);
        messages = new Cache<>(messagesConfig);
        objectCache = new Cache<>(defaultConfig);
    }

    public boolean getConfigBoolean(String value) { return (boolean) objectCache.get(value); }

    public String getConfigString(String value) { return ((String) objectCache.get(value)).replace("&", "§"); }

    public int getConfigInt(String value) { return (int) objectCache.get(value); }

    public String getMessage(String value) {
        return messages.get(value).replace("&", "§");
    }

    public String getStringDirect(String path) {
        return defaultConfig.getString(path);
    }

    public ConfigurationSection getConfigurationSection(String path) {
        return defaultConfig.getConfigurationSection(path);
    }

    public List<String> getListDirect(String path) {
        return defaultConfig.getStringList(path);
    }
}
