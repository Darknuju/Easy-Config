package com.wasykes.EasyConfig.command;

import com.google.common.collect.Sets;
import com.wasykes.EasyConfig.ConfigComponent;
import com.wasykes.EasyConfig.EasyConfig;
import com.wasykes.EasyConfig.Util;
import org.bukkit.command.*;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * Class used to implement live config editing commands.
 *
 */
public class LiveEditCommand extends ConfigComponent implements CommandExecutor {

    private final String commandLabel;

    public LiveEditCommand(EasyConfig componentConfig, String label) {
        super(componentConfig);
        commandLabel = label;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase(commandLabel)
                && (sender.hasPermission(cmd.getPermission())
                    || sender instanceof  ConsoleCommandSender)) {
            if (args.length == 0) {
                //TODO: Implement usage syntax message
                return true;
            }
            switch(args[0].toLowerCase()) {
                case "list":
                    return executeList(sender);
                case "set":
                    return executeSet(args);
                case "get":
                    //TODO:
                    break;
            }
        }
        return false;
    }

    private boolean executeList(CommandSender sender) {
        sender.sendMessage(Util.buildPathListMessage(getComponentConfig().getPaths()));
        return true;
    }

    private boolean executeSet(String[] args) {
        if (args.length > 3) {
            String path = args[1];
            String value = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

            getComponentConfig().setValue(path, value);
            return true;
        }
        return true;
    }
}
