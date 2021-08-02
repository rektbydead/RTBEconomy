package rtb.economy.plugin.economy.customevents;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import rtb.economy.plugin.economy.Account;

import java.math.BigDecimal;

public class PlayerSendMoneyEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled = false;

    private Account sender;
    private Account receiver;
    private BigDecimal money;

    public PlayerSendMoneyEvent(Account sender, Account receiver, BigDecimal money) {
        this.sender = sender;
        this.receiver = receiver;
        this.money = money;
    }

    /*
        DO NOT DELETE! OTHERWISE IT DOES NOT WORK
    */
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public BigDecimal getMoney() {
        return this.money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Account getSender() {
        return this.sender;
    }

    public Account getReceiver() {
        return this.receiver;
    }

    public Player getSenderPlayer() {
        return Bukkit.getPlayer(this.sender.getPlayerUUID());
    }

    public OfflinePlayer getSenderOfflinePlayer() {
        return Bukkit.getOfflinePlayer(this.sender.getPlayerUUID());
    }

    public Player getReceiverPlayer() {
        return Bukkit.getPlayer(this.receiver.getPlayerUUID());
    }

    public OfflinePlayer getReceiverOfflinePlayer() {
        return Bukkit.getOfflinePlayer(this.receiver.getPlayerUUID());
    }

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
}
