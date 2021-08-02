package rtb.economy.plugin.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class ActionBarAPI {

    private ActionBarAPI() { }

    private static Method a;
    private static Object typeMessage;
    private static Constructor<?> chatConstructor;

    public static void sendActionBar(Player player, String message) {
        try {
            Object chatMessage = a.invoke(null, "{\"text\":\"" + message + "\"}");
            Object packet = chatConstructor.newInstance(chatMessage, typeMessage);
            ReflectionUtils.sendPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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

            Class<?> typeMessageClass;
            if (!version.contains("v1_12") && !version.contains("v1_13")) {
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
}
