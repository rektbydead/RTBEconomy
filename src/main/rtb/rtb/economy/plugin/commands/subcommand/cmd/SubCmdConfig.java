package rtb.economy.plugin.commands.subcommand.cmd;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rtb.economy.plugin.commands.subcommand.AbstractSubCommand;
import rtb.economy.plugin.configuration.Config;
import rtb.economy.plugin.objects.ItemBuilder;
import rtb.economy.plugin.objects.RTBEconomy;

public class SubCmdConfig extends AbstractSubCommand {

    public SubCmdConfig(Config config, RTBEconomy economy) {
        super(config, economy, config.getStringDirect("command.config.name"), config.getStringDirect("command.config.usage"),
                config.getStringDirect("command.config.permission"), config.getListDirect("command.config.alias"));
    }

    private static Inventory configInventory;

    static {
        configInventory = Bukkit.createInventory(null, 9 * 3, "Nome");

        ItemBuilder itemBuilder = new ItemBuilder(Material.NAME_TAG)
                .setName("§cFormated Number")
                .setLore("&7Click to {EnableOrDisable}.");

        configInventory.setItem(10, itemBuilder.toItemStack());
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        Inventory inv = Bukkit.createInventory(configInventory.getHolder(), configInventory.getSize(), configInventory.getName());
        inv.setContents(configInventory.getContents());

        player.openInventory(inv);
    }
}

