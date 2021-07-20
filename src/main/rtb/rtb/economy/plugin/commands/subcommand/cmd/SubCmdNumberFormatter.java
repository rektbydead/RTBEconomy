package rtb.economy.plugin.commands.subcommand.cmd;

import org.bukkit.command.CommandSender;
import rtb.economy.plugin.commands.subcommand.AbstractSubCommand;
import rtb.economy.plugin.configuration.Config;
import rtb.economy.plugin.objects.Account;
import rtb.economy.plugin.objects.RTBEconomy;

import static rtb.economy.plugin.configuration.consts.ConstVariables.*;

public class SubCmdNumberFormatter extends AbstractSubCommand {

    public SubCmdNumberFormatter(Config config, RTBEconomy economy) {
        super(config, economy, config.getStringDirect("command.number-formatter.name"), config.getStringDirect("command.number-formatter.usage"),
                config.getStringDirect("command.number-formatter.permission"), config.getListDirect("command.number-formatter.alias"));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Account account = economy.getAccount(sender.getName());
        account.changeFormatted();

        economy.saveAccount(account);

        String message = account.getFormatted() ? MESSAGE_NUMBER_FORMATTER_ON : MESSAGE_NUMBER_FORMATTER_OFF;
        sender.sendMessage(config.getMessage(message));
    }
}