package rtb.economy.plugin.listeners.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import rtb.economy.plugin.economy.RTBEconomy;
import rtb.economy.plugin.listeners.EventHelper;

public class OnJoinEvents extends EventHelper {

    public OnJoinEvents(RTBEconomy economy, Plugin instance) {
        super(economy, instance);
    }

    @EventHandler(ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (economy.existsAccount(player.getName()))
            return;

        economy.createNewAccount(player.getUniqueId());
    }
}
