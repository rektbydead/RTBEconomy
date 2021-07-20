package rtb.economy.plugin.commands.subcommand.cmd;

import org.bukkit.command.CommandSender;
import rtb.economy.plugin.commands.subcommand.AbstractSubCommand;
import rtb.economy.plugin.configuration.Config;
import rtb.economy.plugin.objects.Account;
import rtb.economy.plugin.objects.RTBEconomy;

import static rtb.economy.plugin.configuration.consts.ConstVariables.*;

public class SubCmdToggle extends AbstractSubCommand {

    public SubCmdToggle(Config config, RTBEconomy economy) {
        super(config, economy, config.getStringDirect("command.toggle.name"), config.getStringDirect("command.toggle.usage"),
                config.getStringDirect("command.toggle.permission"), config.getListDirect("command.toggle.alias"));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Account account = economy.getAccount(sender.getName());
        account.changeToggle();

        economy.saveAccount(account);

        String message = account.getToggle() ? MESSAGE_TOGGLE_ON : MESSAGE_TOGGLE_OFF;
        sender.sendMessage(config.getMessage(message));
    }
}