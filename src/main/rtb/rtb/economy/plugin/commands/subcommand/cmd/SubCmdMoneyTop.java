package rtb.economy.plugin.commands.subcommand.cmd;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rtb.economy.plugin.commands.subcommand.AbstractSubCommand;
import rtb.economy.plugin.configuration.Config;
import rtb.economy.plugin.objects.Account;
import rtb.economy.plugin.objects.RTBEconomy;

import java.util.ArrayList;
import java.util.List;

import static rtb.economy.plugin.configuration.consts.ConstVariables.*;

public class SubCmdMoneyTop extends AbstractSubCommand {

    public SubCmdMoneyTop(Config config, RTBEconomy economy) {
        super(config, economy, config.getStringDirect("command.money-top.name"), config.getStringDirect("command.money-top.usage"),
                config.getStringDirect("command.money-top.permission"), config.getListDirect("command.money-top.alias"));
    }

    private static final String NEW_LINE = "\n";
    private static final int MAX_PLAYER_NAME_SIZE = 16;

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return;

        List<Account> moneyTop = new ArrayList<>(economy.getMoneyTop());

        if (moneyTop.isEmpty()) {
            sender.sendMessage(config.getMessage(ERROR_NO_PLAYER_FOUND));
            return;
        }

        Account senderAccount = economy.getAccount(sender.getName());
        boolean formatNumbers = senderAccount.getFormatted();

        String firstLine = config.getMessage(MESSAGE_MONEY_TOP_TITLE) + NEW_LINE;
        StringBuilder stringBuilder = new StringBuilder(firstLine);

        int index = 0;
        for (int i = 0; i < moneyTop.size() && index < 10; i++) {
            Account account = moneyTop.get(i);

            if (account.getName().length() > MAX_PLAYER_NAME_SIZE)
                continue;

            stringBuilder.append(
                    config.getMessage(MESSAGE_MONEY_TOP_TEXT)
                            .replace(REPLACE_PLAYER_NAME, account.getName())
                            .replace(REPLACE_MONEY, account.getMoney(formatNumbers)).replace(REPLACE_POSITION, (++index) + "") + NEW_LINE
            );
        }

        sender.sendMessage(stringBuilder.toString());
    }
}

