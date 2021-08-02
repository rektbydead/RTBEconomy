package rtb.economy.plugin.commands.subcommand.cmd;

import org.bukkit.command.CommandSender;
import rtb.economy.plugin.commands.subcommand.AbstractSubCommand;
import rtb.economy.plugin.configuration.Config;
import rtb.economy.plugin.economy.Account;
import rtb.economy.plugin.economy.RTBEconomy;

import static rtb.economy.plugin.configuration.consts.ConstVariables.MESSAGE_NUMBER_FORMATTER_OFF;
import static rtb.economy.plugin.configuration.consts.ConstVariables.MESSAGE_NUMBER_FORMATTER_ON;


public class SubCmdNumberFormatter extends AbstractSubCommand {

    public SubCmdNumberFormatter(RTBEconomy economy) {
        super(economy, Config.getInstance().getCommandString("command.number-formatter.name"), Config.getInstance().getCommandString("command.number-formatter.usage"),
                Config.getInstance().getCommandString("command.number-formatter.permission"), Config.getInstance().getCommandsList("command.number-formatter.alias"));
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