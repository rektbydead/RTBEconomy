package rtb.economy.plugin.economy;

import org.bukkit.Bukkit;
import rtb.economy.plugin.configuration.Config;
import rtb.economy.plugin.database.Database;
import rtb.economy.plugin.economy.customevents.PlayerSendMoneyEvent;
import rtb.economy.plugin.economy.customevents.MoneyTransferEvent;
import rtb.economy.plugin.economy.transaction.MoneySendStatus;
import rtb.economy.plugin.economy.transaction.TransactionType;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static rtb.economy.plugin.configuration.consts.ConstVariables.ECONOMY_DEFAULT_MONEY;

public class RTBEconomy {

    private Map<String, Account> accounts;
    private Collection<Account> moneyTop;
    private Database database;
    private Config config;

    public RTBEconomy(Database database) {
        this.accounts = new HashMap<>();
        this.database = database;
        this.config = Config.getInstance();

        this.loadAll();
    }

    public boolean transferMoney(String player, BigDecimal money, TransactionType transactionType) {
        Account playerAccount = this.accounts.get(player);

        if (playerAccount == null) return false;

        MoneyTransferEvent event = new MoneyTransferEvent(playerAccount, money, transactionType);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) return false;

        switch (transactionType) {
            case REMOVE_ALLOW_BELLOW_ZERO -> playerAccount.subtract(money);
            case REMOVE -> {
                //Avoids people having less than 0 of "money"
                if (!playerAccount.hasEnoughMoney(money))
                    money = new BigDecimal(playerAccount.getMoneyPlainText());

                playerAccount.subtract(money);
            }
            case ADD -> playerAccount.add(money);
            case SET -> playerAccount.set(money);
        }

        this.saveAccount(playerAccount);

        return true;
    }

    public MoneySendStatus sendMoneyToPlayer(String sender, String receiver, BigDecimal money) {
        Account senderAccount = this.accounts.get(sender);
        Account receiverAccount = this.accounts.get(receiver);

        if (receiverAccount == null) return MoneySendStatus.RECEIVER_DOES_NOT_EXISTS;

        if (receiverAccount.getToggle()) return MoneySendStatus.RECEIVER_HAS_TOGGLE_ON;

        if (!senderAccount.hasEnoughMoney(money)) return MoneySendStatus.NOT_ENOUGH_MONEY;

        PlayerSendMoneyEvent event = new PlayerSendMoneyEvent(senderAccount, receiverAccount, money);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return MoneySendStatus.CANCELLED;
        }

        senderAccount.subtract(event.getMoney());
        receiverAccount.add(event.getMoney());

        this.saveAccount(senderAccount);
        this.saveAccount(receiverAccount);

        return MoneySendStatus.SUCCESSFUL;
    }

    public boolean createNewAccount(UUID playerUUID) {
        Account account = new Account(playerUUID, config.getConfigString(ECONOMY_DEFAULT_MONEY));

        this.createAccount(account);
        this.accounts.put(account.getName(), account);

        return true;
    }
    
    public Account getRichestPlayer() {
        if (moneyTop == null)
            return null;
        
        return moneyTop.iterator().next();
    }

    public Account getAccount(String playerName) {
        return this.accounts.get(playerName);
    }

    public boolean existsAccount(String playerName) {
        return this.accounts.containsKey(playerName);
    }

    public final Collection<Account> getAccounts() { return this.accounts.values(); }

    public Collection<Account> getMoneyTop() { return this.moneyTop; }

    public void setMoneyTop(Collection<Account> moneyTop) { this.moneyTop = moneyTop; }

    public void saveAccount(Account account) {
        this.database.updateAccount(account);
    }

    public void saveAllAccount() {
        for (Account account : accounts.values())
            this.saveAccount(account);
    }

    private void createAccount(Account account) {
        this.database.insertAccount(account);
    }

    private void loadAll() {
        try {
            PreparedStatement statement = this.database.getLoadAccountsStatement();
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Account account = Account.valueOf(resultSet);

                this.accounts.put(account.getName(), account);
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
