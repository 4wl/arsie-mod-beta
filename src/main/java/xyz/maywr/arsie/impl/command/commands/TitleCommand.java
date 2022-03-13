package xyz.maywr.arsie.impl.command.commands;

import org.lwjgl.opengl.Display;
import scala.tools.nsc.backend.icode.Members;
import xyz.maywr.arsie.impl.command.Command;

public class TitleCommand extends Command {
    public TitleCommand() {
        super("title", "title <title>", "chanes the window title");
    }

    @Override
    public boolean onCommand(final String[] args) {
        Display.setTitle(args[0]);
        return true;
    }
}
