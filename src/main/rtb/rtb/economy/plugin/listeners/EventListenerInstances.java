package rtb.economy.plugin.listeners;

import org.bukkit.plugin.Plugin;
import rtb.economy.plugin.listeners.events.*;
import rtb.economy.plugin.economy.RTBEconomy;

public final class EventListenerInstances {
    private EventListenerInstances() { }

    public static void activateAllEvents(RTBEconomy economy, Plugin instance) {
        new OnJoinEvents(economy, instance);
        new InventoryClickEvents(economy, instance);
        new MoneyPayListener(economy, instance);
    }
}
