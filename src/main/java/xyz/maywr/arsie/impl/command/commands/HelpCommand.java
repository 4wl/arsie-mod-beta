package xyz.maywr.arsie.impl.command.commands;

import xyz.maywr.arsie.Arsie;
import xyz.maywr.arsie.impl.command.Command;
import xyz.maywr.arsie.impl.util.ChatUtil;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("help", "help", "shows a list of commands and what they do");
    }

    @Override
    public boolean onCommand(String[] args) {
        for(Command c : Arsie.commandManager.getCommands()){
            ChatUtil.sendMessage(String.format("%s - %s", c.getName(), c.getDesc()), true);
        }
        return true;
    }
}
