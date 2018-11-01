package com.wasykes.EasyConfig.command;

import com.wasykes.EasyConfig.ConfigComponent;
import com.wasykes.EasyConfig.EasyConfig;
import com.wasykes.EasyConfig.Util;
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
public class LiveEditCommand extends ConfigComponent implements CommandExecutor {

    private final String commandLabel;

    /**
     *
     * Constructs component.
     *
     * @param componentConfig Config component is binded to.
     * @param label Command label.
     *
     */
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

    /**
     *
     * List command to list all paths in memory.
     *
     * @param sender Sender of command.
     * @return Boolean which is passed back to be returned in onCommand in proper use.
     *
     */
    private boolean executeList(CommandSender sender) {
        sender.sendMessage(Util.buildPathListMessage(getComponentConfig().getPaths()));
        return true;
    }

    /**
     *
     * Set command edits configuration in memory.
     *
     * @param args Arguments of command.
     * @return Boolean which is passed back to be returned in onCommand in proper use.
     *
     */
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
