package rtb.economy.plugin.vault;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import rtb.economy.plugin.configuration.Config;

import static rtb.economy.plugin.configuration.consts.Constants.ECONOMY_MONEY_TOP_TOP1_TAG;

public class VaultChat {

    private static Chat chat;

    static {
        RegisteredServiceProvider<Chat> chatClass = Bukkit.getServicesManager().getRegistration(Chat.class);

        chat = chatClass.getProvider();
    }

    private VaultChat() {
    }

    public static String getPrefix(String playerName) {
        Player player = Bukkit.getPlayer(playerName);
        if (player == null)
            return "";

        return chat.getPlayerPrefix(player).replace("&", "§");
    }

    public static void setPrefix(String playerName) {
        Player player = Bukkit.getPlayer(playerName);
        if (player == null)
            return;

        chat.setPlayerPrefix(player, Config.getInstance().getMessage(ECONOMY_MONEY_TOP_TOP1_TAG));
    }

    public static void removePrefix(String playerName) {
        Player player = Bukkit.getPlayer(playerName);
        if (player == null)
            return;

        chat.setPlayerPrefix(player, null);
    }
}
