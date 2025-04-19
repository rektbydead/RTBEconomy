package rtb.economy.plugin.economy;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rtb.economy.plugin.util.NumberFormatterHelper;
import rtb.economy.plugin.util.UUIDHelper;
import rtb.economy.plugin.vault.VaultChat;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

public final class Account implements Comparable<Account> {

    private BigDecimal money;

    private UUID playerUUID;
    private String playerName;
    private boolean toggle;
    private boolean formattedNumber;

    //Create new Account    
    public Account(UUID playerUUID, String number) {
        this.playerUUID = playerUUID;
        this.playerName = Bukkit.getOfflinePlayer(playerUUID).getName();
        this.money = new BigDecimal(number);
        this.toggle = false;
        this.formattedNumber = true;
    }

    //Load account from DB
    public Account(UUID playerUUID, String number, boolean toggle, boolean formatted) {
        this(playerUUID, number);
        this.toggle = toggle;
        this.formattedNumber = formatted;
    }

    public static Account valueOf(ResultSet resultSet) throws SQLException {
        byte[] bytes = resultSet.getBytes("uuid");
        String number = resultSet.getString("numberString");
        boolean toggle = resultSet.getBoolean("toggle");
        boolean formatted = resultSet.getBoolean("formatted");

        return new Account(UUIDHelper.convertStream(bytes), number, toggle, formatted);
    }

    public boolean getToggle() {
        return this.toggle;
    }

    public boolean getFormatted() {
        return this.formattedNumber;
    }

    public void changeToggle() {
        this.toggle = !this.toggle;
    }

    public void changeFormatted() {
        this.formattedNumber = !this.formattedNumber;
    }

    public String getNameWithPrefix() {
        return VaultChat.getPrefix(this.playerName) + this.playerName;
    }

    public String getName() {
        return this.playerName;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.playerUUID);
    }

    public UUID getPlayerUUID() {
        return this.playerUUID;
    }

    public String getMoney() {
        return NumberFormatterHelper.numberToString(this.money, this.formattedNumber);
    }

    public String getMoneyFormatted(BigDecimal moneyBigDecimal) {
        return NumberFormatterHelper.numberToString(moneyBigDecimal, this.formattedNumber);
    }

    public String getMoney(boolean formatted) {
        return NumberFormatterHelper.numberToString(this.money, formatted);
    }

    public String getMoneyPlainText() {
        return this.money.toPlainString();
    }

    public BigDecimal getMoneyAsBigDecimal() {
        return this.money;
    }

    public void add(BigDecimal add) {
        this.money = this.money.add(add);
    }

    public void set(BigDecimal set) {
        this.money = set;
    }

    public void subtract(BigDecimal subtract) {
        this.money = this.money.subtract(subtract);
    }

    public boolean hasEnoughMoney(BigDecimal compare) {
        return this.money.compareTo(compare) >= 0;
    }

    @Override
    public int compareTo(Account account) {
        BigDecimal accountMoney = account.money;

        return this.money.compareTo(accountMoney);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Account))
            return false;

        if (this == obj)
            return true;

        Account account = (Account) obj;

        return account.playerUUID.equals(this.playerUUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(money, playerUUID, playerName, toggle, formattedNumber);
    }
}
