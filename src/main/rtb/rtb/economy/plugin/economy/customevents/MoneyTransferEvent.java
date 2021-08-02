package rtb.economy.plugin.economy.customevents;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import rtb.economy.plugin.economy.Account;
import rtb.economy.plugin.economy.transaction.TransactionType;

import java.math.BigDecimal;

public class MoneyTransferEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled = false;

    private Account player;
    private BigDecimal money;
    private TransactionType transactionType;

    public MoneyTransferEvent(Account player, BigDecimal money, TransactionType transactionType) {
        this.player = player;
        this.money = money;
        this.transactionType = transactionType;
    }

    public BigDecimal getMoney() {
        return this.money;
    }

    public Account getAccount() {
        return this.player;
    }

    public Player getPlayer() { return Bukkit.getPlayer(player.getPlayerUUID()); }

    public OfflinePlayer getOfflinePlayer() { return Bukkit.getOfflinePlayer(player.getPlayerUUID()); }

    public TransactionType getTransactionType() { return this.transactionType; }

    public void setMoney(BigDecimal money) { this.money = money; }

    public void setTransactionType(TransactionType transactionType) { this.transactionType = transactionType; }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        isCancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    /*
        DO NOT DELETE! OTHERWISE IT DOES NOT WORK
     */
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
