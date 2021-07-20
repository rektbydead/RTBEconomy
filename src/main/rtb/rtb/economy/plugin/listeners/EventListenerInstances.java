package rtb.economy.plugin.listeners;

import org.bukkit.plugin.Plugin;
import rtb.economy.plugin.listeners.events.OnJoinEvents;
import rtb.economy.plugin.objects.RTBEconomy;

public final class EventListenerInstances {
    private EventListenerInstances() { }

    public static void activateAllEvents(RTBEconomy economy, Plugin instance) {
        new OnJoinEvents(economy, instance);
    }
}
