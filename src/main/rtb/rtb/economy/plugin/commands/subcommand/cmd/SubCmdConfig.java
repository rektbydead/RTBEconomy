package rtb.economy.plugin.commands.subcommand.cmd;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rtb.economy.plugin.commands.subcommand.AbstractSubCommand;
import rtb.economy.plugin.configuration.Config;
import rtb.economy.plugin.economy.Account;
import rtb.economy.plugin.util.ItemBuilder;
import rtb.economy.plugin.economy.RTBEconomy;

import static rtb.economy.plugin.configuration.consts.ConstVariables.*;

public class SubCmdConfig extends AbstractSubCommand {

    public SubCmdConfig(RTBEconomy economy) {
        super(economy, Config.getInstance().getCommandString("command.config.name"), Config.getInstance().getCommandString("command.config.usage"),
                Config.getInstance().getCommandString("command.config.permission"), Config.getInstance().getCommandsList("command.config.alias"));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Account account = economy.getAccount(sender.getName());

        Inventory inv = createInventory(account);
        player.openInventory(inv);
    }

    private Inventory createInventory(Account account) {
        Inventory inv = Bukkit.createInventory(null, config.getConfigInt(INVENTORY_ROWS) * 9, config.getConfigString(INVENTORY_TITLE));

        int toggleIndex = config.getConfigInt(INVENTORY_TOGGLE_POSITION);
        int numberFormatIndex = config.getConfigInt(INVENTORY_NUMBER_FORMAT_POSITION);

        ItemBuilder numberFormat = new ItemBuilder(Material.getMaterial(config.getConfigString(INVENTORY_NUMBER_FORMAT_MATERIAL)))
                .setName(config.getConfigString(INVENTORY_NUMBER_FORMAT_NAME))
                .setLore(config.getConfigList(INVENTORY_NUMBER_FORMAT_LORE))
                .replaceInLore(REPLACE_ENABLE_DISABLE, config.getMessage(!account.getFormatted() ? MESSAGE_ON : MESSAGE_OFF));

        ItemBuilder toggle = new ItemBuilder(Material.getMaterial(config.getConfigString(INVENTORY_TOGGLE_MATERIAL)))
                .setName(config.getConfigString(INVENTORY_TOGGLE_NAME))
                .setLore(config.getConfigList(INVENTORY_TOGGLE_LORE))
                .replaceInLore(REPLACE_ENABLE_DISABLE, config.getMessage(!account.getToggle() ? MESSAGE_ON : MESSAGE_OFF));

        inv.setItem(numberFormatIndex, numberFormat.toItemStack());
        inv.setItem(toggleIndex, toggle.toItemStack());

        return inv;
    }
}

