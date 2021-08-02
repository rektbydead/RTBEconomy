package rtb.economy.plugin.commands.subcommand.cmd;

import org.bukkit.command.CommandSender;
import rtb.economy.plugin.commands.subcommand.AbstractSubCommand;
import rtb.economy.plugin.configuration.Config;
import rtb.economy.plugin.economy.RTBEconomy;

public class SubCmdHelp extends AbstractSubCommand {

    private static final String BREAK_LINE = "\n";
    
    public SubCmdHelp(RTBEconomy economy) {
        super(economy, Config.getInstance().getCommandString("command.help.name"), Config.getInstance().getCommandString("command.help.usage"),
                Config.getInstance().getCommandString("command.help.permission"), Config.getInstance().getCommandsList("command.help.alias"));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Config config = Config.getInstance();

        String text = BREAK_LINE;

        text += config.getConfigString("command.money.help") + BREAK_LINE;

        if (sender.hasPermission(config.getConfigString("command.pay.permission"))) {
            text += config.getConfigString("command.pay.help") + BREAK_LINE;
        }

        if (sender.hasPermission(config.getConfigString("command.config.permission"))) {
            text += config.getConfigString("command.config.help") + BREAK_LINE;
        }

        if (sender.hasPermission(config.getConfigString("command.money-top.permission"))) {
            text += config.getConfigString("command.money-top.help") + BREAK_LINE;
        }

        if (sender.hasPermission(config.getConfigString("command.number-formatter.permission"))) {
            text += config.getConfigString("command.number-formatter.help") + BREAK_LINE;
        }

        if (sender.hasPermission(config.getConfigString("command.toggle.permission"))) {
            text += config.getConfigString("command.toggle.help") + BREAK_LINE;
        }

        if (sender.hasPermission(config.getConfigString("command.set.permission"))) {
            text += config.getConfigString("command.set.help") + BREAK_LINE;
        }

        if (sender.hasPermission(config.getConfigString("command.add.permission"))) {
            text += config.getConfigString("command.add.help") + BREAK_LINE;
        }

        if (sender.hasPermission(config.getConfigString("command.remove.permission"))) {
            text += config.getConfigString("command.remove.help") + BREAK_LINE;
        }

        if (sender.hasPermission(config.getConfigString("command.reload.permission"))) {
            text += config.getConfigString("command.reload.help") + BREAK_LINE;
        }

        text += BREAK_LINE;
        sender.sendMessage(text);
    }
}

