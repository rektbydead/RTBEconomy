package rtb.economy.plugin.commands.subcommand;

import org.bukkit.command.CommandSender;
import rtb.economy.plugin.objects.RTBEconomy;
import rtb.economy.plugin.configuration.Config;

import java.util.List;

public abstract class AbstractSubCommand {

    private static final String EMPTY_STRING = "";

    private final String name;
    private final String usage;
    private final String permission;
    private final List<String> alias;

    protected Config config;
    protected RTBEconomy economy;

    protected AbstractSubCommand(Config config, RTBEconomy economy, String name, String usage, String permission, List<String> alias) {
        this.config = config;
        this.economy = economy;
        this.name = name;
        this.usage = usage.replace("&", "§");
        this.permission = permission;
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public String getUsage() {
        return usage;
    }

    public String getPermission() {
        return permission;
    }

    public boolean hasPermission() { return permission.equals(EMPTY_STRING); }

    public boolean containsAlias(String command) {
        return alias.contains(command);
    }

    public abstract void execute(CommandSender sender, String[] args);
}
