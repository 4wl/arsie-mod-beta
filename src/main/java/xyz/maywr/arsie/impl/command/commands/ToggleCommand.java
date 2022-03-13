package xyz.maywr.arsie.impl.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import xyz.maywr.arsie.Arsie;
import xyz.maywr.arsie.impl.command.Command;
import xyz.maywr.arsie.impl.util.ChatUtil;


public class ToggleCommand extends Command {

    public ToggleCommand() {
        super("toggle", "$toggle (module name)", "toggles a module by name");
    }

    @Override
    public boolean onCommand(String[] args) {
        if (args.length != 1) return false;

        try {
            Arsie.moduleManager.getModule(args[0]).toggle();
            return true;
        } catch (NullPointerException ignored) {}
        return false;
    }
}
