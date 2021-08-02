package rtb.economy.plugin.commands.subcommand.cmd;

import org.bukkit.command.CommandSender;
import rtb.economy.plugin.commands.subcommand.AbstractSubCommand;
import rtb.economy.plugin.configuration.Config;
import rtb.economy.plugin.economy.Account;
import rtb.economy.plugin.economy.RTBEconomy;

import static rtb.economy.plugin.configuration.consts.ConstVariables.*;

public class SubCmdToggle extends AbstractSubCommand {

    public SubCmdToggle(RTBEconomy economy) {
        super(economy, Config.getInstance().getCommandString("command.toggle.name"), Config.getInstance().getConfigString("command.toggle.usage"),
                Config.getInstance().getCommandString("command.toggle.permission"), Config.getInstance().getConfigList("command.toggle.alias"));
        System.out.println("Asdasd");
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