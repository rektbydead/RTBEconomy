package rtb.economy.plugin.runnable;

import org.bukkit.scheduler.BukkitRunnable;
import rtb.economy.plugin.objects.Account;
import rtb.economy.plugin.objects.RTBEconomy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MoneyTop extends BukkitRunnable {

    private RTBEconomy economy;

    public MoneyTop(RTBEconomy economy) {
        this.economy = economy;
    }

    @Override
    public void run() {
        List<Account> accountList = new ArrayList<>(economy.getAccounts());

        Collections.sort(accountList);
        Collections.reverse(accountList);

        economy.setMoneyTop(accountList);
    }
}
