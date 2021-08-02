package rtb.economy.plugin.commands;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import rtb.economy.plugin.economy.Account;
import rtb.economy.plugin.economy.RTBEconomy;
import rtb.economy.plugin.configuration.Config;
import rtb.economy.plugin.commands.subcommand.AbstractSubCommand;

import java.util.ArrayList;
import java.util.List;

import static rtb.economy.plugin.configuration.consts.ConstVariables.*;

public class Commands implements CommandExecutor {
    private List<AbstractSubCommand> subcommands;

    private RTBEconomy economy;

    public Commands(RTBEconomy economy, Plugin instance) {
        this.subcommands = new ArrayList<>();

        this.economy = economy;

        try {
            ImmutableSet<ClassPath.ClassInfo> classList = ClassPath.from(instance.getClass().getClassLoader()).
                    getTopLevelClassesRecursive(PACKAGE_COMMANDS); //package where all commands are

            /*
                Go through all classes (commands) and instance one of each then add it to a List.
             */
            classList.forEach(classInfo -> {
                Class<?> classType = classInfo.load();

                try {
                    Object classe = classType.getConstructor(RTBEconomy.class).newInstance(economy);
                    AbstractSubCommand command = (AbstractSubCommand) classe;

                    subcommands.add(command);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        Config config = Config.getInstance();
        
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

            if (!(subCommand.hasPermission() && !commandSender.hasPermission(subCommand.getPermission()))) {
                commandSender.sendMessage(config.getMessage(ERROR_NO_PERMISSION_ERROR));
                return false;
            }

            subCommand.execute(commandSender, args);
            return true;
        }

        if (economy.existsAccount(name)) {
            Account account = economy.getAccount(name);

            commandSender.sendMessage(
                    config.getMessage(MESSAGE_MONEY_OTHER)
                            .replace(REPLACE_PLAYER_NAME, account.getName())
                            .replace(REPLACE_MONEY, account.getMoney())
            );

            return true;
        }

        commandSender.sendMessage(config.getMessage(ERROR_PLAYER_NOT_FOUND));
        return false;
    }
}
