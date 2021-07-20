package rtb.economy.plugin.listeners;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import rtb.economy.plugin.objects.RTBEconomy;

public class EventHelper implements Listener {
    protected RTBEconomy economy;
    protected Plugin instance;

    public EventHelper(RTBEconomy economy, Plugin instance) {
        this.economy = economy;
        this.instance = instance;

        activateEvent();
    }

    private void activateEvent() {
        instance.getServer().getPluginManager().registerEvents(this, instance);
    }
}
