package rtb.economy.plugin;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import rtb.economy.plugin.commands.Commands;
import rtb.economy.plugin.economy.RTBEconomy;
import rtb.economy.plugin.configuration.Config;

import rtb.economy.plugin.database.Database;
import rtb.economy.plugin.listeners.EventListenerInstances;
import rtb.economy.plugin.economy.runnable.MoneyTop;
import rtb.economy.plugin.vault.VaultEconomy;


public final class Main extends JavaPlugin {

    private Database database;
    private RTBEconomy economy;

    @Override
    public void onEnable() {
        if (!this.activateVault()) {
            Log.info("No Vault found, disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
        }

        Config.setPluginInstance(this);

        database = new Database(this);
        economy = new RTBEconomy(database);

        new MoneyTop(economy, this);

        EventListenerInstances.activateAllEvents(economy, this);

        if (Bukkit.getPluginManager().isPluginEnabled(this)) {
            getCommand("money").setExecutor(new Commands(economy, this));
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

