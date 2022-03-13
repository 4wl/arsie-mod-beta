package xyz.maywr.arsie.impl.command.commands;

import xyz.maywr.arsie.Arsie;
import xyz.maywr.arsie.impl.command.Command;
import xyz.maywr.arsie.impl.util.ChatUtil;

public class PrefixCommand extends Command {

    public PrefixCommand() {
        super("prefix", "prefix <char>", "changes the default $ prefix to whatever you want");
    }

    @Override
    public boolean onCommand(String[] args) {
        Arsie.commandManager.setPrefix(args[0]);
        ChatUtil.sendMessage("done! ur prefix " + args[0] + " now", true);
        return true;
    }
}
