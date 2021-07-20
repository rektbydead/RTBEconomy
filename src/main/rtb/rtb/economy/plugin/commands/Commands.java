package rtb.economy.plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rtb.economy.plugin.commands.subcommand.cmd.*;
import rtb.economy.plugin.objects.RTBEconomy;
import rtb.economy.plugin.configuration.Config;
import rtb.economy.plugin.commands.subcommand.AbstractSubCommand;

import java.util.ArrayList;
import java.util.List;

import static rtb.economy.plugin.configuration.consts.ConstVariables.*;

public class Commands implements CommandExecutor {
    private List<AbstractSubCommand> subcommands;

    private Config config;
    private RTBEconomy economy;

    public Commands(Config config, RTBEconomy economy) {
        this.subcommands = new ArrayList<>();

        this.config = config;
        this.economy = economy;

        this.subcommands.add(new SubCmdAdd(this.config, this.economy));
        this.subcommands.add(new SubCmdSet(this.config, this.economy));
        this.subcommands.add(new SubCmdRemove(this.config, this.economy));
        this.subcommands.add(new SubCmdPay(this.config, this.economy));
        this.subcommands.add(new SubCmdToggle(this.config, this.economy));
        this.subcommands.add(new SubCmdMoneyTop(this.config, this.economy));
        this.subcommands.add(new SubCmdConfig(this.config, this.economy));
        this.subcommands.add(new SubCmdNumberFormatter(this.config, this.economy));
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) return false;

        if (args.length == 0) {
            commandSender.sendMessage(
                    config.getMessage(MESSAGE_MONEY_YOUR)
                            .replace(REPLACE_MONEY, economy.getAccount(commandSender.getName()).getMoney())
            );
            return false;
        }

        String name = args[0];
        for (AbstractSubCommand subCommand : subcommands) {
            if (!name.equalsIgnoreCase(subCommand.getName()) && !subCommand.containsAlias(name)) {
                continue;
            }

            if (subCommand.hasPermission() && !commandSender.hasPermission(subCommand.getPermission())) {
                commandSender.sendMessage(config.getMessage(ERROR_NO_PERMISSION_ERROR));
                break;
            }

            subCommand.execute(commandSender, args);
            return true;
        }

        if (economy.existsAccount(name)) {
            commandSender.sendMessage(
                    config.getMessage(MESSAGE_MONEY_OTHER)
                            .replace(REPLACE_PLAYER_NAME, name)
                            .replace(REPLACE_MONEY, economy.getAccount(name).getMoney())
            );

            return true;
        }

        commandSender.sendMessage(config.getMessage(ERROR_PLAYER_NOT_FOUND));
        return false;
    }
}
