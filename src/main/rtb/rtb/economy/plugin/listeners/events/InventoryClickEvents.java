package rtb.economy.plugin.listeners.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import rtb.economy.plugin.configuration.Config;
import rtb.economy.plugin.economy.Account;
import rtb.economy.plugin.economy.RTBEconomy;
import rtb.economy.plugin.listeners.EventHelper;
import rtb.economy.plugin.util.ItemBuilder;

import static rtb.economy.plugin.configuration.consts.Constants.*;

public class InventoryClickEvents extends EventHelper {

    private Config config;

    public InventoryClickEvents(RTBEconomy economy, Plugin instance) {
        super(economy, instance);

        this.config = Config.getInstance();
    }

    @EventHandler(ignoreCancelled = true)
    public void inventoryClick(InventoryClickEvent e) {
        Inventory inv = e.getClickedInventory();

        if (inv == null) {
            return;
        }

        if (!inv.getTitle().equalsIgnoreCase(config.getConfigString(INVENTORY_TITLE))) {
            return;
        }

        e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();
        Account account = economy.getAccount(player.getName());

        int slot = e.getSlot();
        ItemBuilder itemBuilder = new ItemBuilder(inv.getItem(slot));

        if (slot == config.getConfigInt(INVENTORY_TOGGLE_POSITION)) {
            this.reactOnClick(itemBuilder, TYPE.TOGGLE, account);
            return;
        }

        if (slot == config.getConfigInt(INVENTORY_NUMBER_FORMAT_POSITION)) {
            this.reactOnClick(itemBuilder, TYPE.NUMBER_FORMAT, account);
        }
    }

    private void reactOnClick(ItemBuilder itemBuilder, TYPE type, Account account) {
        itemBuilder.setLore(config.getConfigList(type.value));
        itemBuilder.replaceInLore(REPLACE_ENABLE_DISABLE, config.getMessage(type.getFunction(account) ? MESSAGE_ON : MESSAGE_OFF));

        type.callFunction(account);
    }

    private enum TYPE {
        TOGGLE(INVENTORY_TOGGLE_LORE),
        NUMBER_FORMAT(INVENTORY_NUMBER_FORMAT_LORE);

        private final String value;

        TYPE(String value) {
            this.value = value;
        }

        private void callFunction(Account account) {
            switch (this) {
                case TOGGLE -> account.changeToggle();
                case NUMBER_FORMAT -> account.changeFormatted();
            }
        }

        private boolean getFunction(Account account) {
            switch (this) {
                case TOGGLE: {
                    return account.getToggle();
                }
                case NUMBER_FORMAT: {
                    return account.getFormatted();
                }
                default: {
                    return false;
                }
            }
        }
    }
}
