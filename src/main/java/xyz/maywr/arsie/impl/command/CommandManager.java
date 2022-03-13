package xyz.maywr.arsie.impl.command;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.maywr.arsie.impl.command.Command;
import xyz.maywr.arsie.impl.command.commands.*;
import xyz.maywr.arsie.impl.util.ChatUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static xyz.maywr.arsie.Arsie.mc;


public class CommandManager  {

    private ArrayList<Command> commands = new ArrayList<>();
    private String prefix = "$";

    public CommandManager(){
        MinecraftForge.EVENT_BUS.register(this);
        commands.add(new ToggleCommand());
        commands.add(new TitleCommand());
        commands.add(new ExecuteCommand());
        commands.add(new PrefixCommand());
        commands.add(new HelpCommand());
    }

    public Command getCommand(String name){
        for(Command c : commands){
            if(c.getName().equalsIgnoreCase(name)){
                return c;
            }
        } return null;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix){
        this.prefix = prefix;
    }

    public List<Command> getCommands(){
        return commands;
    }

    @SubscribeEvent
    public void onChatMessage(ClientChatEvent evt){
        String message = evt.getOriginalMessage();
        if(!message.startsWith(prefix))
            return;

        message = message.replace(prefix, "");
        String command = message.split(" ")[0];
        String[] args = message.replace(command + " ", "").split(" ");
        try {
            Command executed = getCommand(command);
            if(!executed.onCommand(args)){
                ChatUtil.sendMessage(String.format("wrong usage! %s - $s", executed.getName(), executed.getUsage()), true);
            }
        } catch (NullPointerException e) {
            ChatUtil.sendMessage(ChatFormatting.RED + String.format("there is no %s command, use %shelp", command, prefix), true); }
        evt.setCanceled(true);
        mc.ingameGUI.getChatGUI().addToSentMessages(evt.getOriginalMessage());
    }
}
