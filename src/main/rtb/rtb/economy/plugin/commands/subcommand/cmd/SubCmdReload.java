package rtb.economy.plugin.commands.subcommand.cmd;

import org.bukkit.command.CommandSender;
import rtb.economy.plugin.commands.subcommand.AbstractSubCommand;
import rtb.economy.plugin.configuration.Config;
import rtb.economy.plugin.economy.RTBEconomy;


import static rtb.economy.plugin.configuration.consts.ConstVariables.*;

public class SubCmdReload extends AbstractSubCommand {

    public SubCmdReload(RTBEconomy economy) {
        super(economy, Config.getInstance().getCommandString("command.reload.name"), Config.getInstance().getCommandString("command.reload.usage"),
                Config.getInstance().getCommandString("command.reload.permission"), Config.getInstance().getCommandsList("command.reload.alias"));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Config.getInstance().reloadConfig();
        sender.sendMessage(Config.getInstance().getMessage(MESSAGE_RELOADED));
    }
}

