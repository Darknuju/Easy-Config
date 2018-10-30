package com.wasykes.EasyConfig;

import org.bukkit.ChatColor;
import java.util.Set;

/**
 *
 * General library utility class. (Subject to removal int he future)
 *
 * @author Darknuju
 * @version 1.0
 * @since 10/30/2018
 *
 */
public class Util {

    public static boolean IsNotNull(Object... args) {
        for (Object o : args) {
            if (o == null) {
                return false;
            }
        }
        return true;
    }

    public static String buildPathListMessage(Set<String> paths) {
        String returnString = "&6:&3--&1========================&3--&6:\n";
        for(String path : paths) {
            returnString += "&6:&3--&6: &1" + path + "\n";
        }
        returnString += "&6:&3--&1========================&3--&6:";

        return ChatColor.translateAlternateColorCodes('&', returnString);
    }
}
