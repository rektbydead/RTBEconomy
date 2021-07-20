package rtb.economy.plugin;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import rtb.economy.plugin.commands.Commands;
import rtb.economy.plugin.objects.RTBEconomy;
import rtb.economy.plugin.configuration.Config;

import rtb.economy.plugin.configuration.Database;
import rtb.economy.plugin.listeners.EventListenerInstances;
import rtb.economy.plugin.runnable.MoneyTop;
import rtb.economy.plugin.vault.VaultEconomy;


public final class Main extends JavaPlugin {

    private RTBEconomy economy;
    private Database database;

    @Override
    public void onEnable() {
        if (!this.activateVault()) {
            Log.info("No Vault found, disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
        }

        Config config = new Config(this);

        database = new Database(this, config);
        economy = new RTBEconomy(database, config);

        new MoneyTop(economy).runTaskTimerAsynchronously(this, 0, 20 * (long) 300);

        EventListenerInstances.activateAllEvents(economy, this);

        if (Bukkit.getPluginManager().isPluginEnabled(this)) {
            getCommand("money").setExecutor(new Commands(config, economy));
        }
    }

    @Override
    public void onDisable() {
        economy.saveAllAccount();
        database.closeDatabaseConnection();
    }

    private boolean activateVault() {
        Plugin vault = Bukkit.getPluginManager().getPlugin("Vault");
        if (vault == null)
            return false;

        new VaultEconomy(this, economy);

        return true;
    }
}

