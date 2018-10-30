package com.wasykes.EasyConfig.command;

import com.wasykes.EasyConfig.ConfigComponent;
import com.wasykes.EasyConfig.EasyConfig;
import com.wasykes.EasyConfig.Util;
import org.bukkit.command.*;

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
        if (label.equalsIgnoreCase(commandLabel)) {
            if (args.length == 0) {
                //TODO: Implement usage syntax message
            }
            switch(args[0].toLowerCase()) {
                case "list":
                    return dispatchList(sender);
                case "set":
                    //TODO: Set command
                    break;
                case "get":
                    //TODO:
                    break;
            }
        }
        return false;
    }

    private boolean dispatchList(CommandSender sender) {
        sender.sendMessage(Util.buildPathListMessage(getComponentConfig().getPaths()));
        return true;
    }
}
