package rtb.economy.plugin.commands.subcommand.cmd;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rtb.economy.plugin.commands.subcommand.AbstractSubCommand;
import rtb.economy.plugin.configuration.Config;
import rtb.economy.plugin.economy.Account;
import rtb.economy.plugin.economy.RTBEconomy;

import java.util.ArrayList;
import java.util.List;

import static rtb.economy.plugin.configuration.consts.ConstVariables.*;

public class SubCmdMoneyTop extends AbstractSubCommand {

    public SubCmdMoneyTop(RTBEconomy economy) {
        super(economy, Config.getInstance().getCommandString("command.money-top.name"), Config.getInstance().getCommandString("command.money-top.usage"),
                Config.getInstance().getCommandString("command.money-top.permission"), Config.getInstance().getCommandsList("command.money-top.alias"));
    }

    private static final String BREAK_LINE = "\n";
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

        String firstLine = config.getMessage(MESSAGE_MONEY_TOP_TITLE) + BREAK_LINE;
        StringBuilder stringBuilder = new StringBuilder(firstLine);

        boolean showPrefix = Config.getInstance().getConfigBoolean(ECONOMY_MONEY_TOP_SHOW_PREFIX);
        
        int index = 0;
        for (int i = 0; i < moneyTop.size() && index < config.getConfigInt(ECONOMY_MONEY_TOP_SIZE); i++) {
            Account account = moneyTop.get(i);

            if (account.getName().length() > MAX_PLAYER_NAME_SIZE)
                continue;

            String name = "";
            if (i == 0 && config.getConfigBoolean(ECONOMY_MONEY_TOP_SHOW_TOP1_TAG))
                 name = config.getMessage(ECONOMY_MONEY_TOP_TOP1_TAG);
            
            name += showPrefix ? account.getNameWithPrefix() : account.getName();
            
            stringBuilder.append(
                    config.getMessage(MESSAGE_MONEY_TOP_TEXT)
                            .replace(REPLACE_PLAYER_NAME, name)
                            .replace(REPLACE_MONEY, account.getMoney(formatNumbers)).replace(REPLACE_POSITION, (++index) + "") + BREAK_LINE
            );
        }

        sender.sendMessage(stringBuilder.toString());
    }
}

