package rtb.economy.plugin.commands.subcommand.cmd;

import org.bukkit.command.CommandSender;
import rtb.economy.plugin.commands.subcommand.AbstractSubCommand;
import rtb.economy.plugin.configuration.Config;
import rtb.economy.plugin.objects.Account;
import rtb.economy.plugin.objects.RTBEconomy;
import rtb.economy.plugin.transaction.TransactionType;
import rtb.economy.plugin.util.BigDecimalHelper;
import rtb.economy.plugin.util.NumberFormatter;

import java.math.BigDecimal;

import static rtb.economy.plugin.configuration.consts.ConstVariables.*;

public class SubCmdSet extends AbstractSubCommand {

    public SubCmdSet(Config config, RTBEconomy economy) {
        super(config, economy, config.getStringDirect("command.set.name"), config.getStringDirect("command.set.usage"),
                config.getStringDirect("command.set.permission"), config.getListDirect("command.set.alias"));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(getUsage());
            return;
        }

        String playerName = args[1];
        String stringMoney = args[2];

        BigDecimal money = BigDecimalHelper.getDecimal(stringMoney);

        if (NumberFormatter.isFormatted(stringMoney)) {
            money = NumberFormatter.stringToNumber(stringMoney);
        }

        if (money.compareTo(BigDecimal.ONE) < 0) {
            sender.sendMessage(config.getMessage(ERROR_INVALID_NUMBER));
            return;
        }

        if (!economy.existsAccount(playerName)) {
            sender.sendMessage(config.getMessage(ERROR_PLAYER_NOT_FOUND));
            return;
        }

        if (!economy.transferMoney(playerName, money, TransactionType.SET)) {
            sender.sendMessage(config.getMessage(ERROR_UNKNOWN));
            return;
        }

        Account senderAccount = economy.getAccount(sender.getName());
        boolean formatNumber = senderAccount != null && senderAccount.getFormatted();

        sender.sendMessage(
                config.getMessage(MESSAGE_MODIFY_SET)
                        .replace(REPLACE_MONEY, NumberFormatter.numberToString(money, formatNumber))
                        .replace(REPLACE_PLAYER_NAME, playerName)
        );
    }
}

