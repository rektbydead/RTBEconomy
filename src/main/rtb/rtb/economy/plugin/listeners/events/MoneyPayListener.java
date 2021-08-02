package rtb.economy.plugin.listeners.events;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;
import rtb.economy.plugin.configuration.Config;
import rtb.economy.plugin.economy.Account;
import rtb.economy.plugin.economy.RTBEconomy;
import rtb.economy.plugin.economy.customevents.PlayerSendMoneyEvent;
import rtb.economy.plugin.listeners.EventHelper;
import rtb.economy.plugin.util.ActionBarAPI;

import java.math.BigDecimal;

import static rtb.economy.plugin.configuration.consts.Constants.*;


public class MoneyPayListener extends EventHelper {

    public MoneyPayListener(RTBEconomy economy, Plugin instance) {
        super(economy, instance);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void soundSender(PlayerSendMoneyEvent e) {
        Config config = Config.getInstance();

        if (!config.getConfigBoolean(ECONOMY_MONEY_PAY_SOUND_ACTIVE))
            return;

        this.playSound(e.getSender());
        this.playSound(e.getReceiver());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void actionBarSender(PlayerSendMoneyEvent e) {
        Config config = Config.getInstance();

        if (!config.getConfigBoolean(ECONOMY_MONEY_PAY_ACTIONBAR_ACTIVE))
            return;

        this.sendActionBarMessage(e.getSender(), e.getReceiver().getName(), e.getMoney(), ECONOMY_MONEY_PAY_ACTIONBAR_TEXT_PAY);
        this.sendActionBarMessage(e.getReceiver(), e.getSender().getName(), e.getMoney(), ECONOMY_MONEY_PAY_ACTIONBAR_TEXT_RECEIVE);
    }

    private void sendActionBarMessage(Account account, String otherPlayerName, BigDecimal money, String type) {
        Config config = Config.getInstance();
        Player player = account.getPlayer();

        if (player == null)
            return;

        ActionBarAPI.sendActionBar(player,
                config.getConfigString(type)
                        .replace(REPLACE_PLAYER_NAME, otherPlayerName)
                        .replace(REPLACE_MONEY, account.getMoneyFormatted(money))
        );
    }

    private void playSound(Account account) {
        Config config = Config.getInstance();
        Player player = account.getPlayer();

        if (player == null)
            return;

        player.playSound(player.getLocation(),
                Sound.valueOf(config.getConfigString(ECONOMY_MONEY_PAY_SOUND_NAME)),
                config.getConfigInt(ECONOMY_MONEY_PAY_SOUND_VOLUME),
                config.getConfigInt(ECONOMY_MONEY_PAY_SOUND_PITCH));
    }
}
