package com.wasykes.EasyConfig.components;

import com.wasykes.EasyConfig.ConfigComponent;
import com.wasykes.EasyConfig.EasyConfig;
import com.wasykes.EasyConfig.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
    private ArrayList<ConfigCommand> commands = new ArrayList<>();
    private Map<String, ConfigComponent> components = new HashMap<>();

    public void addComponent(ConfigComponent component) {
        components.put(component.componentLabel, component);
    }

    public void addCommand(ConfigCommand command) {
        commands.add(command);
    }

    public enum ConfigCommand {
        SET("set"),
        GET("get"),
        LIST("list"),
        BACKUP("backup"),
        LOAD("load"),
        UNLOAD("unload"),
        REVERT("revert");

        public final String label;

        ConfigCommand(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    /**
     *
     * Constructs component.
     *
     * @param componentConfig Config component is bound to.
     * @param label Command label.
     *
     */
    public LiveEditCommandComponent(EasyConfig componentConfig, String label) {
        super(componentConfig, "command");
        commandLabel = label;
    }

    private void sendUsageMessage(CommandSender sender, String usedCommand) {
        switch(usedCommand) {
            case "":
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Usage: /" + commandLabel + " <" +
                        String.join("/", commands.stream().map(ConfigCommand::toString).collect(Collectors.toList()))
                        + ">"));
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
                    if (commands.contains(ConfigCommand.LIST))
                        return executeList(sender);
                case "set":
                    if (commands.contains(ConfigCommand.SET))
                        return executeSet(sender, args);
                case "get":
                    if (commands.contains(ConfigCommand.GET))
                        return executeGet(sender, args);
                case "backup":
                    if (commands.contains(ConfigCommand.BACKUP))
                        return executeBackup(sender);
                case "load":
                    if (commands.contains(ConfigCommand.LOAD))
                        return executeLoad(sender, args);
                case "unload":
                    if (commands.contains(ConfigCommand.UNLOAD))
                        return executeUnload(sender, args);
                case "revert":
                    if (commands.contains(ConfigCommand.REVERT))
                        return executeRevert(sender);
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
        if (components.containsKey("backup")) {
            if (((BackupComponent)components.get("backup")).backup()) {
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

    private boolean executeLoad(CommandSender sender, String[] args) {
        if (!componentConfig.isLoadedToMemory(args[1])) {
            boolean result = componentConfig.loadConfigurationIntoMemory(args[1]);
            if (result) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&1" + args[1] + " loaded into memory!"));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Unable to load value!"));
            }
            return result;
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Value already in memory!"));
            return false;
        }
    }

    private boolean executeUnload(CommandSender sender, String[] args) {
        if (componentConfig.isLoadedToMemory(args[1])) {
            boolean result = componentConfig.unloadConfigurationFromMemory(args[1]);
            if (result) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&1" + args[1] + " unloaded from memory!"));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Unable to unload value!"));
            }
            return result;
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Value not in memory!"));
            return false;
        }
    }

    private boolean executeRevert(CommandSender sender) {
        File backupFile = new File(componentConfig.getRawConfigFile().getPath().replace(".yml", " - backup.yml"));
        if (backupFile.exists()) {
            try {
                componentConfig.getRawConfigFile().delete();
                Files.copy(backupFile.toPath(), componentConfig.getRawConfigFile().toPath());
            } catch(IOException e) {
                e.printStackTrace();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Unable to revert to old backup!"));
                return false;
            }
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&1Successfully reverted! No current memory values will be changed."));
            return true;
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4No backup file to revert to!"));
            return false;
        }
    }
}
