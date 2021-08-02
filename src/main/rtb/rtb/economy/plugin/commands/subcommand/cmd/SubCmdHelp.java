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

        StringBuilder text = new StringBuilder(BREAK_LINE);
        Config config = Config.getInstance();

        text.append(config.getConfigString("command.money.help") + BREAK_LINE);

        if (sender.hasPermission(config.getConfigString("command.pay.permission"))) {
            text.append(config.getConfigString("command.pay.help") + BREAK_LINE);
        }

        if (sender.hasPermission(config.getConfigString("command.config.permission"))) {
            text.append(config.getConfigString("command.config.help") + BREAK_LINE);
        }

        if (sender.hasPermission(config.getConfigString("command.money-top.permission"))) {
            text.append(config.getConfigString("command.money-top.help") + BREAK_LINE);
        }

        if (sender.hasPermission(config.getConfigString("command.number-formatter.permission"))) {
            text.append(config.getConfigString("command.number-formatter.help") + BREAK_LINE);
        }

        if (sender.hasPermission(config.getConfigString("command.toggle.permission"))) {
            text.append(config.getConfigString("command.toggle.help") + BREAK_LINE);
        }

        if (sender.hasPermission(config.getConfigString("command.set.permission"))) {
            text.append(config.getConfigString("command.set.help") + BREAK_LINE);
        }

        if (sender.hasPermission(config.getConfigString("command.add.permission"))) {
            text.append(config.getConfigString("command.add.help") + BREAK_LINE);
        }

        if (sender.hasPermission(config.getConfigString("command.remove.permission"))) {
            text.append(config.getConfigString("command.remove.help") + BREAK_LINE);
        }

        if (sender.hasPermission(config.getConfigString("command.reload.permission"))) {
            text.append(config.getConfigString("command.reload.help") + BREAK_LINE);
        }

        String message = text.append(BREAK_LINE).toString();
        sender.sendMessage(message);
    }
}

