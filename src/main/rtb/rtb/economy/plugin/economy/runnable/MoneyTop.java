package rtb.economy.plugin.economy.runnable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import rtb.economy.plugin.configuration.Config;
import rtb.economy.plugin.economy.Account;
import rtb.economy.plugin.economy.RTBEconomy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static rtb.economy.plugin.configuration.consts.ConstVariables.*;

public final class MoneyTop extends BukkitRunnable {

    private RTBEconomy economy;

    public MoneyTop(RTBEconomy economy, Plugin plugin) {
        this.economy = economy;

        long refreshRate = 20L * Config.getInstance().getConfigInt("Economy.money-top.refresh-rate");
        this.runTaskTimerAsynchronously(plugin, 0, refreshRate);
    }

    @Override
    public void run() {
        List<Account> accountList = new ArrayList<>(economy.getAccounts());
        List<Account> backupList = new ArrayList<>(accountList);

        Collections.sort(accountList);
        Collections.reverse(accountList);

        Config config = Config.getInstance();

        economy.setMoneyTop(accountList);
        
        if (economy.getMoneyTop() == null) {
            return;
        }
        
        if (!config.getConfigBoolean(ECONOMY_MONEY_TOP_SHOW_MESSAGE)) {
            return;
        }
        
       Account oldTop = backupList.get(0);
       Account newTop = accountList.get(0);

       //runs if oldTop != newTOP or if broadcast everytime
       if (!config.getConfigBoolean(ECONOMY_MONEY_TOP_SHOW_MESSAGE_IF_TOP1_CHANGE) || oldTop == null || !oldTop.equals(newTop)) {
           for (Player player : Bukkit.getOnlinePlayers()) {
               player.sendMessage(
                       Config.getInstance().getMessage(MESSAGE_MONEY_TOP_REFRESH)
                               .replace(REPLACE_PLAYER_NAME, newTop.getName())
               );
           }
       }
    }
}
