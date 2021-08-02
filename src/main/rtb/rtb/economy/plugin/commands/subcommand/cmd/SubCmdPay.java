package rtb.economy.plugin.commands.subcommand.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rtb.economy.plugin.commands.subcommand.AbstractSubCommand;
import rtb.economy.plugin.configuration.Config;
import rtb.economy.plugin.economy.Account;
import rtb.economy.plugin.economy.RTBEconomy;
import rtb.economy.plugin.economy.transaction.MoneySendStatus;
import rtb.economy.plugin.util.BigDecimalHelper;
import rtb.economy.plugin.util.NumberFormatter;

import java.math.BigDecimal;

import static rtb.economy.plugin.configuration.consts.ConstVariables.*;

public class SubCmdPay extends AbstractSubCommand {

    public SubCmdPay(RTBEconomy economy) {
        super(economy, Config.getInstance().getCommandString("command.pay.name"), Config.getInstance().getCommandString("command.pay.usage"),
                Config.getInstance().getCommandString("command.pay.permission"), Config.getInstance().getCommandsList("command.pay.alias"));
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

        MoneySendStatus transaction = economy.sendMoneyToPlayer(sender.getName(), playerName, money);
        switch (transaction) {
            case RECEIVER_DOES_NOT_EXISTS -> sender.sendMessage(config.getMessage(ERROR_PLAYER_NOT_FOUND));

            case NOT_ENOUGH_MONEY -> sender.sendMessage(config.getMessage(ERROR_NOT_ENOUGH_MONEY));

            case RECEIVER_HAS_TOGGLE_ON -> sender.sendMessage(
                    config.getMessage(ERROR_TOGGLE_ON)
                    .replace(REPLACE_PLAYER_NAME, playerName)
            );

            case CANCELLED -> sender.sendMessage(config.getMessage(ERROR_CANCELLED));

            case SUCCESSFUL ->  {
                Account senderAccount = economy.getAccount(sender.getName());                
                Account receiverAccount = economy.getAccount(playerName);

                sender.sendMessage(
                        config.getMessage(MESSAGE_TRANSFER_SEND)
                                .replace(REPLACE_MONEY, NumberFormatter.numberToString(money, senderAccount.getFormatted()))
                                .replace(REPLACE_PLAYER_NAME, receiverAccount.getName())
                );
                
                Player receiverPlayer = Bukkit.getPlayer(playerName);
                if (receiverPlayer == null)
                    break;

                receiverPlayer.sendMessage(
                        config.getMessage(MESSAGE_TRANSFER_RECEIVE)
                                .replace(REPLACE_MONEY, NumberFormatter.numberToString(money, receiverAccount.getFormatted()))
                                .replace(REPLACE_PLAYER_NAME, senderAccount.getName())
                );
            }
        }            
    }
}

