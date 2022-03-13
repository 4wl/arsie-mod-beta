package xyz.maywr.arsie.impl.command.commands;

import bsh.EvalError;
import bsh.Interpreter;
import xyz.maywr.arsie.impl.command.Command;
import xyz.maywr.arsie.impl.util.ChatUtil;

public class ExecuteCommand extends Command {

    public ExecuteCommand() {
        super("java", "executes java code", "executes java code (bsh interpritator)");
    }

    @Override
    public boolean onCommand(String[] args) {
        String command = String.join(" ", args);
        Interpreter interpreter = new Interpreter();
        try {
            interpreter.eval(command);
        } catch (EvalError e) {}
        return true;
    }
}
