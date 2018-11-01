package com.wasykes.EasyConfig;

import org.bukkit.ChatColor;
import java.util.Set;

/**
 *
 * General library utility class. (Subject to removal int he future.)
 *
 * @author Darknuju
 * @version 1.0
 * @since 10/30/2018
 *
 */
public class Util {

    /**
     *
     * Test if arguments are null.
     *
     * @param args Arguments to test if null.
     * @return Boolean whether or not any elements are null.
     *
     */
    public static boolean IsNotNull(Object... args) {
        for (Object o : args) {
            if (o == null) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * Builds list of strings to be send through bukkit messages to player.
     *
     * @param paths Set of path strings to be added to list.
     * @return Returns completed string to be sent.
     *
     */
    public static String buildPathListMessage(Set<String> paths) {
        String returnString = "&6:&3--&1========================&3--&6:\n";
        for(String path : paths) {
            returnString += "&6:&3--&6: &1" + path + "\n";
        }
        returnString += "&6:&3--&1========================&3--&6:";

        return ChatColor.translateAlternateColorCodes('&', returnString);
    }
}
