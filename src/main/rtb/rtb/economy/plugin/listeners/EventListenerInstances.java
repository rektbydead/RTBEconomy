package rtb.economy.plugin.listeners;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import rtb.economy.plugin.economy.RTBEconomy;

import static rtb.economy.plugin.configuration.consts.Constants.PACKAGE_EVENTS;

public final class EventListenerInstances {
    private EventListenerInstances() {
    }

    public static void activateAllEvents(RTBEconomy economy, Plugin instance) {
        try {
            ImmutableSet<ClassPath.ClassInfo> classList = ClassPath.from(instance.getClass().getClassLoader()).
                    getTopLevelClassesRecursive(PACKAGE_EVENTS);

            classList.forEach(classInfo -> {
                try {
                    Class<?> classLoaded = classInfo.load();

                    Class<?> classe = (Class<?>) classLoaded.getConstructor(RTBEconomy.class, Plugin.class).newInstance(economy, instance);
                    if (Listener.class.isAssignableFrom(classe))
                        return;

                    classe.getConstructor(Plugin.class).newInstance(instance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
