package rtb.economy.plugin.commands.subcommand.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rtb.economy.plugin.commands.subcommand.AbstractSubCommand;
import rtb.economy.plugin.configuration.Config;
import rtb.economy.plugin.objects.Account;
import rtb.economy.plugin.objects.RTBEconomy;
import rtb.economy.plugin.transaction.TransactionState;
import rtb.economy.plugin.util.BigDecimalHelper;
import rtb.economy.plugin.util.NumberFormatter;

import java.math.BigDecimal;

import static rtb.economy.plugin.configuration.consts.ConstVariables.*;

public class SubCmdPay extends AbstractSubCommand {

    public SubCmdPay(Config config, RTBEconomy economy) {
        super(config, economy, config.getStringDirect("command.pay.name"), config.getStringDirect("command.pay.usage"),
                config.getStringDirect("command.pay.permission"), config.getListDirect("command.pay.alias"));
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

        if (sender.getName().equalsIgnoreCase(playerName)) {
            sender.sendMessage(config.getMessage(ERROR_SEND_MONEY_YOURSELF));
            return;
        }

        TransactionState transaction = economy.sendMoneyToPlayer(sender.getName(), playerName, money);
        switch (transaction) {
            case RECEIVER_DOES_NOT_EXISTS -> config.getMessage(ERROR_PLAYER_NOT_FOUND);

            case NOT_ENOUGH_MONEY -> sender.sendMessage(config.getMessage(ERROR_NOT_ENOUGH_MONEY));

            case RECEIVER_HAS_TOGGLE_ON -> sender.sendMessage(
                    config.getMessage(ERROR_TOGGLE_ON)
                    .replace(REPLACE_PLAYER_NAME, playerName)
            );

            case SUCCESSFUL -> this.successfulMessagesSend(sender, playerName, money);
        }
    }

    private void successfulMessagesSend(CommandSender sender, String playerName, BigDecimal money) {
        Account senderAccount = economy.getAccount(sender.getName());

        sender.sendMessage(
                config.getMessage(MESSAGE_TRANSFER_SEND)
                        .replace(REPLACE_MONEY, NumberFormatter.numberToString(money, senderAccount.getFormatted()))
                        .replace(REPLACE_PLAYER_NAME, playerName)
        );

        Player receiver = Bukkit.getPlayer(playerName);
        if (receiver == null)
            return;

        Account receiverAccount = economy.getAccount(playerName);

        receiver.sendMessage(
                config.getMessage(MESSAGE_TRANSFER_RECEIVE)
                        .replace(REPLACE_MONEY, NumberFormatter.numberToString(money, receiverAccount.getFormatted()))
                        .replace(REPLACE_PLAYER_NAME, sender.getName())
       );
    }
}

