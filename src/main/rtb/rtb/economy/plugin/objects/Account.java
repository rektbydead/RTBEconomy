package rtb.economy.plugin.objects;


import org.bukkit.Bukkit;
import rtb.economy.plugin.util.NumberFormatter;
import rtb.economy.plugin.util.UUIDHelper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

public final class Account implements Comparable<Account>{

    private BigDecimal money;

    private UUID playerUUID;
    private String playerName;
    private boolean toggle;
    private boolean formattedNumber;

    //Create new Account
    public Account(UUID playerUUID, int number) {
        this.playerUUID = playerUUID;
        this.playerName = Bukkit.getOfflinePlayer(playerUUID).getName();
        this.money = new BigDecimal(number);
        this.toggle = false;
        formattedNumber = true;
    }

    //Load account from DB
    public Account(UUID playerUUID, String number, boolean toggle, boolean formatted) {
        this.playerUUID = playerUUID;
        this.playerName = Bukkit.getOfflinePlayer(playerUUID).getName();
        this.money = new BigDecimal(number);
        this.toggle = toggle;
        formattedNumber = formatted;
    }

    public boolean getToggle() { return toggle; }

    public boolean getFormatted() { return formattedNumber; }

    public void changeToggle() {
        this.toggle = !this.toggle;
    }

    public void changeFormatted() {
        this.formattedNumber = !this.formattedNumber;
    }

    public String getName() {
        return playerName;
    }

    public UUID getPlayerUUID() { return playerUUID; }

    public String getMoney() { return NumberFormatter.numberToString(money, formattedNumber); }

    public String getMoney(boolean formatted) { return NumberFormatter.numberToString(money, formatted); }

    public String getMoneyPlainText() {
        return money.toPlainString();
    }

    public BigDecimal getMoneyAsBigDecimal() { return money; }

    public void add(BigDecimal add) {
        this.money = money.add(add);
    }

    public void set(BigDecimal set) { this.money = set; }

    public void subtract(BigDecimal subtract) {
        this.money = money.subtract(subtract);
    }

    public boolean hasEnoughMoney(BigDecimal compare) {
        return this.money.compareTo(compare) >= 0;
    }

    public static Account valueOf(ResultSet resultSet) throws SQLException {
        byte[] bytes = resultSet.getBytes("uuid");
        String number = resultSet.getString("numberString");
        boolean toggle = resultSet.getBoolean("toggle");
        boolean formatted = resultSet.getBoolean("formatted");

        return new Account(UUIDHelper.convertStream(bytes), number, toggle, formatted);
    }

    @Override
    public int compareTo(Account account) {
        BigDecimal accountMoney = account.money;

        return this.money.compareTo(accountMoney);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Account)
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
