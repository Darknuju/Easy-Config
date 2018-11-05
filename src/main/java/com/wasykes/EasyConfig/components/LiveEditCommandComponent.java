package com.wasykes.EasyConfig.components;

import com.wasykes.EasyConfig.ConfigComponent;
import com.wasykes.EasyConfig.EasyConfig;
import com.wasykes.EasyConfig.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import java.util.Arrays;

/**
 *
 * Class used to implement live config editing commands.
 *
 * @author Darknuju
 * @version 1.0
 * @since 10/30/2018
 *
 */
public class LiveEditCommandComponent extends ConfigComponent implements CommandExecutor {

    private final String commandLabel;

    /**
     *
     * Constructs component.
     *
     * @param componentConfig Config component is binded to.
     * @param label Command label.
     *
     */
    public LiveEditCommandComponent(EasyConfig componentConfig, String label) {
        super(componentConfig);
        commandLabel = label;
    }

    private void sendUsageMessage(CommandSender sender, String command) {
        switch(command) {
            case "":
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Syntax: /" + commandLabel + " <list/set/get>"));
            case "list":
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Syntax: /" + commandLabel + " list"));
            case "set":
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Syntax: /" + commandLabel + " set path <string/number/decimal> value"));
            case "get":
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Syntax: /" + commandLabel + " get path"));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase(commandLabel)
                && (sender.hasPermission(cmd.getPermission())
                    || sender instanceof  ConsoleCommandSender)) {
            if (args.length == 0) {
                sendUsageMessage(sender, "");
                return true;
            }
            switch(args[0].toLowerCase()) {
                case "list":
                    return executeList(sender);
                case "set":
                    return executeSet(sender, args);
                case "get":
                    return executeGet(sender, args);
                default:
                    sendUsageMessage(sender, "");
                    return false;
            }
        }
        return false;
    }

    /**
     *
     * List components to list all paths in memory.
     *
     * @param sender Sender of components.
     * @return Boolean which is passed back to be returned in onCommand in proper use.
     *
     */
    private boolean executeList(CommandSender sender) {
        sender.sendMessage(Util.buildPathListMessage(getComponentConfig().getPaths()));
        return true;
    }

    /**
     *
     * Set components edits configuration in memory.
     *
     * @param args Arguments of components.
     * @return Boolean which is passed back to be returned in onCommand in proper use.
     *
     */
    private boolean executeSet(CommandSender sender, String[] args) {
        if (args.length > 3) {
            String path = args[1];
            String type = args[2];
            Object value;
            switch(type.toLowerCase()) {
                case "string":
                    value = String.join(" ", Arrays.copyOfRange(args, 3, args.length));
                    break;
                case "number":
                    try {
                        value = Integer.valueOf(args[3]);
                    } catch(Exception e) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Invalid number value!"));
                        return false;
                    }
                    break;
                case "decimal":
                    try {
                        value = Double.valueOf(args[3]);
                    } catch(Exception e) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Invalid decimal value!"));
                        return false;
                    }
                    break;
                default:
                    sendUsageMessage(sender, "set");
                    return false;
            }

            getComponentConfig().setValue(path, value);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&1Set value!"));
        }
        return true;
    }

    private boolean executeGet(CommandSender sender, String[] args) {
        if (args.length > 1) {
            String path = args[1];

            if (!getComponentConfig().isLoadedToMemory(path)) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&4No such value loaded into memory! (Try using /" + commandLabel + " load <path> if you need to load this config value)"));
                return false;
            }

            String value = getComponentConfig().getValue(path).toString();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&1" + path + "&6: " + value));
            return true;
        }
        sendUsageMessage(sender, "get");
        return false;
    }
}
