package mocks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class MockCommand extends Command {

    public MockCommand() {
        super("MockCommand");
    }

    @Override
    public String getPermission() {
        return "MockPermission";
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        return false;
    }
}
