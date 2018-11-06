package com.wasykes.EasyConfig.components;

import com.wasykes.EasyConfig.ConfigComponent;
import com.wasykes.EasyConfig.EasyConfig;
import com.wasykes.EasyConfig.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import java.util.Arrays;

/**
 *
 * Component used to implement live config editing commands.
 *
 * @author Darknuju
 * @version 1.0
 * @since 10/30/2018
 *
 */
public class LiveEditCommandComponent extends ConfigComponent implements CommandExecutor {

    private final String commandLabel;
    private boolean backupComponentExists = false;
    private BackupComponent backupComponent;

    /**
     *
     * Constructs component.
     *
     * @param componentConfig Config component is bound to.
     * @param label Command label.
     *
     */
    public LiveEditCommandComponent(EasyConfig componentConfig, String label) {
        super(componentConfig);
        commandLabel = label;
    }

    /**
     *
     * Constructs component with backup component to allow backup command.
     *
     * @param componentConfig Config component is binded to.
     * @param label Command label.
     * @param backup Backup component to add.
     *
     */
    public LiveEditCommandComponent(EasyConfig componentConfig, String label, BackupComponent backup) {
        super(componentConfig);
        commandLabel = label;
        backupComponentExists = true;
        backupComponent = backup;
    }

    private void sendUsageMessage(CommandSender sender, String command) {
        switch(command) {
            case "":
                if (backupComponentExists)
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Syntax: /" + commandLabel + " <list/set/get/backup>"));
                else
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
                case "backup":
                    return executeBackup(sender);
                default:
                    sendUsageMessage(sender, "");
                    return false;
            }
        }
        return false;
    }

    private boolean executeList(CommandSender sender) {
        sender.sendMessage(Util.buildPathListMessage(getComponentConfig().getPaths()));
        return true;
    }

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

    private boolean executeBackup(CommandSender sender) {
        if (backupComponentExists) {
            if (backupComponent.backup()) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&1Config backed up!"));
                return true;
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Failed to backup config!"));
                return false;
            }
        } else {
            sendUsageMessage(sender, "");
            return false;
        }
    }
}
