package rtb.economy.plugin.configuration;

import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;
import rtb.economy.plugin.configuration.consts.ConstVariables;
import java.util.HashMap;
import java.util.Map;

public class Cache<T> {

    //using Generics cuz it looks cool ... oooh yeeeaaaah baby
    private Map<String, T> cacheValues;

    public Cache(FileConfiguration config) {
        cacheValues = new HashMap<>();
        fillCache(config);
    }

    private void fillCache(FileConfiguration config) {
        for (Field field : ConstVariables.class.getDeclaredFields()) {
            try {
                String fieldValue = (String) field.get(this);

                if (config.contains(fieldValue))
                    cacheValues.put(fieldValue, (T) config.get(fieldValue));

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public T get(String value) {
        return cacheValues.get(value);
    }
}
