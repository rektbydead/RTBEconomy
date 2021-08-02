package rtb.economy.plugin.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public final class ActionBarAPI {

    private static Method a;
    private static Object typeMessage;
    private static Constructor<?> chatConstructor;

    static {
        try {
            String version = Bukkit.getVersion();

            Class<?> icbc = ReflectionUtils.getNMSClass("IChatBaseComponent");
            Class<?> ppoc = ReflectionUtils.getNMSClass("PacketPlayOutChat");
            if (icbc.getDeclaredClasses().length > 0) {
                a = icbc.getDeclaredClasses()[0].getMethod("a", String.class);
            } else {
                a = ReflectionUtils.getNMSClass("ChatSerializer").getMethod("a", String.class);
            }

            Class<?> typeMessageClass = null;
            if (!version.contains("v1_1")) {
                typeMessageClass = Byte.TYPE;
                typeMessage = (byte) 2;
            } else {
                typeMessageClass = ReflectionUtils.getNMSClass("ChatMessageType");
                typeMessage = typeMessageClass.getEnumConstants()[2];
            }

            chatConstructor = ppoc.getConstructor(icbc, typeMessageClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ActionBarAPI() {
    }

    public static void sendActionBar(Player player, String message) {
        try {
            Object chatMessage = a.invoke(null, "{\"text\":\"" + message + "\"}");
            Object packet = chatConstructor.newInstance(chatMessage, typeMessage);
            ReflectionUtils.sendPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
