package rtb.economy.plugin.vault;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import rtb.economy.plugin.economy.RTBEconomy;
import rtb.economy.plugin.economy.transaction.TransactionType;
import rtb.economy.plugin.util.NumberFormatterHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VaultEconomy implements Economy {

    private Plugin plugin;
    private RTBEconomy economy;

    public VaultEconomy(Plugin plugin, RTBEconomy economy) {
        this.plugin = plugin;
        this.economy = economy;
    }

    @Override
    public boolean isEnabled() {
        return plugin.isEnabled();
    }

    @Override
    public String getName() {
        return "RTBEconomy";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 4;
    }

    @Override
    public String format(double v) {
        return NumberFormatterHelper.numberFormat(BigDecimal.valueOf(v));
    }

    @Override
    public String currencyNamePlural() {
        return null;
    }

    @Override
    public String currencyNameSingular() {
        return null;
    }

    @Override
    public boolean hasAccount(String s) {
        return economy.existsAccount(s);
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return economy.existsAccount(offlinePlayer.getName());
    }

    @Override
    public boolean hasAccount(String s, String s1) {
        return economy.existsAccount(s);
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return economy.existsAccount(offlinePlayer.getName());
    }

    @Override
    public double getBalance(String s) {
        return economy.getAccount(s).getMoneyAsBigDecimal().doubleValue();
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        return economy.getAccount(offlinePlayer.getName()).getMoneyAsBigDecimal().doubleValue();
    }

    @Override
    public double getBalance(String s, String s1) {
        return economy.getAccount(s).getMoneyAsBigDecimal().doubleValue();
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        return economy.getAccount(offlinePlayer.getName()).getMoneyAsBigDecimal().doubleValue();
    }

    @Override
    public boolean has(String s, double v) {
        return economy.getAccount(s).hasEnoughMoney(BigDecimal.valueOf(v));
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        return economy.getAccount(offlinePlayer.getName()).hasEnoughMoney(BigDecimal.valueOf(v));
    }

    @Override
    public boolean has(String s, String s1, double v) {
        return economy.getAccount(s).hasEnoughMoney(BigDecimal.valueOf(v));
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        return economy.getAccount(offlinePlayer.getName()).hasEnoughMoney(BigDecimal.valueOf(v));
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, double v) {
        boolean succeeded = economy.transferMoney(s, new BigDecimal(Double.toString(v)), TransactionType.REMOVE);
        return new EconomyResponse(v, getBalance(s), succeeded ? EconomyResponse.ResponseType.SUCCESS : EconomyResponse.ResponseType.FAILURE, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        return withdrawPlayer(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double v) {
        return withdrawPlayer(s, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return withdrawPlayer(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse depositPlayer(String s, double v) {
        boolean succeeded = economy.transferMoney(s, new BigDecimal(Double.toString(v)), TransactionType.ADD);
        return new EconomyResponse(v, getBalance(s), succeeded ? EconomyResponse.ResponseType.SUCCESS : EconomyResponse.ResponseType.FAILURE, "");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        return depositPlayer(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse depositPlayer(String s, String s1, double v) {
        return depositPlayer(s, v);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return depositPlayer(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse createBank(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return new ArrayList<>();
    }

    @Override
    public boolean createPlayerAccount(String s) {
        return economy.createNewAccount(Bukkit.getPlayer(s).getUniqueId());
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return economy.createNewAccount(offlinePlayer.getUniqueId());
    }

    @Override
    public boolean createPlayerAccount(String s, String s1) {
        return economy.createNewAccount(Bukkit.getPlayer(s).getUniqueId());
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return economy.createNewAccount(offlinePlayer.getUniqueId());
    }
}
