package rtb.economy.plugin.objects;

import rtb.economy.plugin.configuration.Config;
import rtb.economy.plugin.configuration.Database;
import rtb.economy.plugin.transaction.TransactionState;
import rtb.economy.plugin.transaction.TransactionType;

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
    Config config;

    public RTBEconomy(Database database, Config config) {
        this.database = database;
        this.config = config;
        accounts = new HashMap<>();

        this.loadAll();
    }

    public boolean transferMoney(String player, BigDecimal money, TransactionType transactionType) {
        Account playerAccount = this.accounts.get(player);

        if (playerAccount == null) return false;

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

    public TransactionState sendMoneyToPlayer(String sender, String receiver, BigDecimal money) {
        Account senderAccount = this.accounts.get(sender);
        Account receiverAccount = this.accounts.get(receiver);

        if (receiverAccount == null) return TransactionState.RECEIVER_DOES_NOT_EXISTS;

        if (receiverAccount.getToggle()) return TransactionState.RECEIVER_HAS_TOGGLE_ON;

        if (!senderAccount.hasEnoughMoney(money)) return TransactionState.NOT_ENOUGH_MONEY;

        senderAccount.subtract(money);
        receiverAccount.add(money);

        this.saveAccount(senderAccount);
        this.saveAccount(receiverAccount);

        return TransactionState.SUCCESSFUL;
    }

    public boolean createNewAccount(UUID playerUUID) {
        Account account = new Account(playerUUID, config.getConfigInt(ECONOMY_DEFAULT_MONEY));

        this.createAccount(account);
        this.accounts.put(account.getName(), account);

        return true;
    }

    public Account getAccount(String playerName) {
        return accounts.get(playerName);
    }

    public boolean existsAccount(String playerName) {
        return this.accounts.containsKey(playerName);
    }

    public final Collection<Account> getAccounts() { return accounts.values(); }

    public Collection<Account> getMoneyTop() { return moneyTop; }

    public void setMoneyTop(Collection<Account> moneyTop) { this.moneyTop = moneyTop; }

    public void saveAccount(Account account) {
        try {
            this.database.getUpdateStatement(account.getPlayerUUID(), account.getMoneyPlainText(), account.getToggle(), account.getFormatted()).executeUpdate();
        } catch (Exception ignored) { }
    }

    public void saveAllAccount() {
        for (Account account : accounts.values())
            saveAccount(account);
    }

    private void createAccount(Account account) {
        try {
            this.database.getInsertStatement(account.getPlayerUUID(), account.getMoneyPlainText(), account.getToggle(), account.getFormatted()).executeUpdate();
        } catch (Exception ignored) { ignored.printStackTrace();}
    }

    private void loadAll() {
        try {
            PreparedStatement statement = this.database.getLoadAccountsStatement();
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Account account = Account.valueOf(resultSet);

                this.accounts.put(account.getName(), account);
            }
        } catch (Exception ignored) { }
    }
}
