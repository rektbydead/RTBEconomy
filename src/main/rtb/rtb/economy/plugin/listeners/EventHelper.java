package rtb.economy.plugin.listeners;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import rtb.economy.plugin.economy.RTBEconomy;

public class EventHelper implements Listener {
    protected RTBEconomy economy;

    public EventHelper(RTBEconomy economy, Plugin instance) {
        this.economy = economy;

        activateEvent(instance);
    }

    private void activateEvent(Plugin instance) {
        instance.getServer().getPluginManager().registerEvents(this, instance);
    }
}
